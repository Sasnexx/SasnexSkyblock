package de.sasnex.sasnexskyblock.Commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.sasnex.sasnexskyblock.Filemanager.FileManager;
import de.sasnex.sasnexskyblock.GUIS.GUI;
import de.sasnex.sasnexskyblock.Interfaces.ICommands;
import de.sasnex.sasnexskyblock.Listeners.MiningListener;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import de.sasnex.sasnexskyblock.Utils.VaultSystem;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class IslandCreate implements CommandExecutor, ICommands {
    private static final Map<UUID, UUID> WORKER_VILLAGER = new HashMap<>();
    private static final Map<UUID, Integer> WORKER_TASK = new HashMap<>();

    FileManager fm = new FileManager();
    GUI gui = new GUI();
    VaultSystem vm = new VaultSystem();

    public IslandCreate() {
        Objects.requireNonNull(SasnexSkyblock.getInstance().getCommand("is")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NonNull @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            if (SasnexSkyblock.getFileManager().getIslandCFG().contains("islands." + player.getUniqueId())) {
                double x = SasnexSkyblock.getFileManager().getIslandCFG().getDouble("islands." + player.getUniqueId() + ".x");
                double z = SasnexSkyblock.getFileManager().getIslandCFG().getDouble("islands." + player.getUniqueId() + ".z");
                World islandWorld = Bukkit.getWorld("SasnexSkyblock");
                if (islandWorld == null) {
                    player.sendMessage(SasnexSkyblock.color("&cFehler: Welt SasnexSkyblock konnte nicht geladen werden."));
                    return true;
                }
                player.teleport(new Location(islandWorld, x, 100, z).add(0.5, 2, 0.5));
                player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&7Willkommen zurueck auf deiner Insel!"));
            } else {
                player.sendMessage(SasnexSkyblock.color("&cDu hast noch keine Insel. Nutze /is create."));
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (SasnexSkyblock.getFileManager().getIslandCFG().contains("islands." + player.getUniqueId())) {
                player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&cDu hast bereits eine Insel."));
                return true;
            }
            createIsland(player);
            SasnexSkyblock.getFileManager().setPlayersData("island." + player.getUniqueId(), true);
            return true;
        }

        if (args[0].equalsIgnoreCase("money")) {
            int money = SasnexSkyblock.getVaultSystem().getBalance(player);
            player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&7Du hast &a" + money + "&7€."));
            return true;
        }

        if (args[0].equalsIgnoreCase("delete")) {
            SasnexSkyblock.getFileManager().setPlayersData("island." + player.getUniqueId(), false);
            return true;
        }

        if (args[0].equalsIgnoreCase("shop")) {
            gui.openMainShop(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("arbeiter") || args[0].equalsIgnoreCase("worker")) {
            if (args.length >= 2 && (args[1].equalsIgnoreCase("abbruch")
                    || args[1].equalsIgnoreCase("stop")
                    || args[1].equalsIgnoreCase("remove"))) {
                if (stopWorker(player.getUniqueId())) {
                    player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&7Worker wurde gestoppt."));
                } else {
                    player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&7Du hast aktuell keinen Worker."));
                }
                return true;
            }

            spawnWorker(player);
            return true;
        }

        return false;
    }

    private void spawnWorker(Player player) {
        stopWorker(player.getUniqueId());

        Villager villager = player.getWorld().spawn(player.getLocation(), Villager.class);
        villager.setCustomName(SasnexSkyblock.color("&aRaven"));
        villager.setCustomNameVisible(true);
        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());

        WORKER_VILLAGER.put(player.getUniqueId(), villager.getUniqueId());
        player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&7Raven gespawnt. Nutze /is arbeiter abbruch zum Stoppen."));
        startWorkerTask(villager, player);
    }

    private void startWorkerTask(Villager villager, Player owner) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(SasnexSkyblock.getInstance(), () -> {
            if (!villager.isValid() || !owner.isOnline()) {
                stopWorker(owner.getUniqueId());
                return;
            }

            if (!"SasnexSkyblock".equals(owner.getWorld().getName())) {
                return;
            }

            Location eye = villager.getEyeLocation();
            RayTraceResult hit = owner.getWorld().rayTraceBlocks(
                    eye,
                    eye.getDirection(),
                    6.0,
                    FluidCollisionMode.NEVER,
                    true
            );

            if (hit == null || hit.getHitBlock() == null) {
                return;
            }

            Block target = hit.getHitBlock();
            if (target.getType().isAir()) {
                return;
            }

            Material minedType = target.getType();
            if (target.breakNaturally()) {
                MiningListener.handleMine(owner, minedType);
            }
        }, 20L, 20L);

        WORKER_TASK.put(owner.getUniqueId(), task.getTaskId());
    }

    public static boolean stopWorker(UUID ownerUuid) {
        boolean removed = false;

        Integer taskId = WORKER_TASK.remove(ownerUuid);
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            removed = true;
        }

        UUID villagerUuid = WORKER_VILLAGER.remove(ownerUuid);
        if (villagerUuid != null) {
            Entity entity = findEntity(villagerUuid);
            if (entity instanceof Villager villager && villager.isValid()) {
                villager.remove();
                removed = true;
            }
        }

        return removed;
    }

    public static void stopAllWorkers() {
        for (UUID ownerUuid : WORKER_TASK.keySet().toArray(new UUID[0])) {
            stopWorker(ownerUuid);
        }
        for (UUID ownerUuid : WORKER_VILLAGER.keySet().toArray(new UUID[0])) {
            stopWorker(ownerUuid);
        }
    }

    public static boolean stopWorkerByVillager(UUID villagerUuid) {
        for (Map.Entry<UUID, UUID> entry : WORKER_VILLAGER.entrySet()) {
            if (entry.getValue().equals(villagerUuid)) {
                return stopWorker(entry.getKey());
            }
        }
        return false;
    }

    private static Entity findEntity(UUID entityUuid) {
        for (World world : Bukkit.getWorlds()) {
            Entity entity = world.getEntity(entityUuid);
            if (entity != null) return entity;
        }
        return null;
    }

    @Deprecated
    private void createIsland(Player player) {
        World islandWorld = Bukkit.getWorld("SasnexSkyblock");
        if (islandWorld == null) {
            player.sendMessage(SasnexSkyblock.color("&cFehler: Welt SasnexSkyblock konnte nicht geladen werden."));
            return;
        }

        if (SasnexSkyblock.getFileManager().getIslandCFG().contains("islands." + player.getUniqueId())) {
            double x = SasnexSkyblock.getFileManager().getIslandCFG().getDouble("islands." + player.getUniqueId() + ".x");
            double z = SasnexSkyblock.getFileManager().getIslandCFG().getDouble("islands." + player.getUniqueId() + ".z");

            player.teleport(new Location(islandWorld, x, 100, z).add(0.5, 2, 0.5));
            player.sendMessage(SasnexSkyblock.color("&aWillkommen zurueck auf deiner Insel!"));
            return;
        }

        int index = SasnexSkyblock.getFileManager().getIslandCFG().getInt("island-index", 0);
        double newX = index * 1000.0;
        double newZ = 0.0;
        Location spawnLoc = new Location(islandWorld, newX, 100, newZ);

        File schematicFile = new File(SasnexSkyblock.getFileManager().getSchematicsFolder(), "island.schem");
        if (!schematicFile.exists()) {
            player.sendMessage(SasnexSkyblock.color("&cFehler: island.schem wurde nicht im Ordner gefunden!"));
            return;
        }

        Location centerLoc;

        try {
            centerLoc = createSchem(schematicFile, spawnLoc);
        } catch (RuntimeException e) {
            player.sendMessage(SasnexSkyblock.color("&cFehler beim Einfuegen der Insel: " + e.getMessage()));
            return;
        }

        SasnexSkyblock.getFileManager().getIslandCFG().set("islands." + player.getUniqueId() + ".x", centerLoc.getX());
        SasnexSkyblock.getFileManager().getIslandCFG().set("islands." + player.getUniqueId() + ".z", centerLoc.getZ());
        SasnexSkyblock.getFileManager().getIslandCFG().set("island-index", index + 1);
        SasnexSkyblock.getFileManager().saveislandCFG();

        Bukkit.getScheduler().runTaskLater(
                SasnexSkyblock.getInstance(),
                () -> {
                    player.teleport(centerLoc.clone().add(0.5, 2, 0.5));
                    player.sendMessage(SasnexSkyblock.color("&bDeine Insel wurde erfolgreich erstellt!"));
                },
                1L
        );
    }

    Location createSchem(File schematicFile, Location loc) throws RuntimeException {
        ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);

        if (format == null) {
            throw new RuntimeException("Unbekanntes Schematic-Format.");
        }
        try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
            Clipboard clipboard = reader.read();
            BlockVector3 min = clipboard.getRegion().getMinimumPoint();
            BlockVector3 max = clipboard.getRegion().getMaximumPoint();
            BlockVector3 origin = clipboard.getOrigin();

            double centerX = (min.getX() + max.getX()) / 2.0;
            double centerZ = (min.getZ() + max.getZ()) / 2.0;
            double worldCenterX = loc.getBlockX() + (centerX - origin.getX());
            double worldCenterZ = loc.getBlockZ() + (centerZ - origin.getZ());
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(loc.getWorld()))) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))
                        .ignoreAirBlocks(true)
                        .build();

                Operations.complete(operation);
            }
            return new Location(loc.getWorld(), worldCenterX, loc.getY(), worldCenterZ);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
