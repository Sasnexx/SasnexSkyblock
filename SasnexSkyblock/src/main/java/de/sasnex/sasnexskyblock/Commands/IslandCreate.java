package de.sasnex.sasnexskyblock.Commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.sasnex.sasnexskyblock.Filemanager.FileManager;
import de.sasnex.sasnexskyblock.GUIS.GUI;
import de.sasnex.sasnexskyblock.Interfaces.ICommands;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import com.sk89q.worldedit.function.operation.Operation;
import de.sasnex.sasnexskyblock.Utils.VaultSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class IslandCreate implements CommandExecutor, ICommands {

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
                    player.sendMessage("§cFehler: Welt SasnexSkyblock konnte nicht geladen werden.");
                    return true;
                }
                player.teleport(new Location(islandWorld, x, 100, z).add(0.5, 2, 0.5));
                player.sendMessage("§8[§bSasnexSkyblock§8] §7Willkommen zurück auf deiner Insel!");
            } else {
                player.sendMessage("§cDu hast noch keine Insel. Nutze /is create.");
            }
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("create")) {
            if (SasnexSkyblock.getFileManager().getIslandCFG().contains("islands." + player.getUniqueId())) {
                player.sendMessage("§8[§bSasnexSkyblock§8] §cDu hast bereits eine Insel.");

                return true;
            }
            createIsland(player);
            SasnexSkyblock.getFileManager().setPlayersData("island." + player.getUniqueId(), true);
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("money")) {
            int money = SasnexSkyblock.getVaultSystem().getBalance(player);
            player.sendMessage("§8[§bSasnexSkyblock§8] §7Du hast §a"+money+"§7€.");
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("delete")) {
            SasnexSkyblock.getFileManager().setPlayersData("island." + player.getUniqueId(), false);
            // hier muss die methode hin wo das gespawnte schematic löscht
            return true;
        }

        if(args.length > 0 && args[0].equalsIgnoreCase("shop")){
            gui.IslandShopGUI(player);
        }

        return false;
    }

    @Deprecated
    private void createIsland(Player player) {
        World islandWorld = Bukkit.getWorld("SasnexSkyblock");
        if (islandWorld == null) {
            player.sendMessage("§cFehler: Welt SasnexSkyblock konnte nicht geladen werden.");
            return;
        }

        //Prüfen ob der Spiener ne Insel hat (Was bei neuen Spieler nicht der Fall ist lol)
        if (SasnexSkyblock.getFileManager().getIslandCFG().contains("islands." + player.getUniqueId())) {
            double x = SasnexSkyblock.getFileManager().getIslandCFG().getDouble("islands." + player.getUniqueId() + ".x");
            double z = SasnexSkyblock.getFileManager().getIslandCFG().getDouble("islands." + player.getUniqueId() + ".z");

            player.teleport(new Location(islandWorld, x, 100, z).add(0.5, 2, 0.5));
            player.sendMessage("§aWillkommen zurück auf deiner Insel!");
            return;
        }

        int index = SasnexSkyblock.getFileManager().getIslandCFG().getInt("island-index", 0);
        double newX = index * 1000.0;
        double newZ = 0.0;
        Location spawnLoc = new Location(islandWorld, newX, 100, newZ);

        File schematicFile = new File(SasnexSkyblock.getFileManager().getSchematicsFolder(), "island.schem");
        if (!schematicFile.exists()) {
            player.sendMessage("§cFehler: island.schem wurde nicht im Ordner gefunden!");
            return;
        }

        Location centerLoc;


        try {
            centerLoc = createSchem(schematicFile, spawnLoc);
        } catch (RuntimeException e) {
            player.sendMessage("§cFehler beim Einfügen der Insel: " + e.getMessage());
            return;
        }

        SasnexSkyblock.getFileManager().getIslandCFG().set("islands." + player.getUniqueId() + ".x", centerLoc.getX());
        SasnexSkyblock.getFileManager().getIslandCFG().set("islands." + player.getUniqueId() + ".z", centerLoc.getZ());
        SasnexSkyblock.getFileManager().getIslandCFG().set("island-index", index + 1);
        SasnexSkyblock.getFileManager().saveislandCFG();
        // 6. Finaler Teleport (1 Tick Verzögerung, damit der Paste sicher fertig ist)
        Bukkit.getScheduler().runTaskLater(
                SasnexSkyblock.getInstance(),
                () -> {
                    player.teleport(centerLoc.clone().add(0.5, 2, 0.5));
                    player.sendMessage("§bDeine Insel wurde erfolgreich erstellt!");
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

                // 3. Die Operation vorbereiten
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))
                        // Falls deine Schematic Luft enthält, die nichts überschreiben soll:
                        .ignoreAirBlocks(true)
                        .build();

                // 4. Die Operation ausführen (Dank FAWE passiert das extrem schnell)
                Operations.complete(operation);
            }
            return new Location(loc.getWorld(), worldCenterX, loc.getY(), worldCenterZ);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




