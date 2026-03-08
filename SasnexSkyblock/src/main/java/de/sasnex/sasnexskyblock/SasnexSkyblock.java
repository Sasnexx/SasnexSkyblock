package de.sasnex.sasnexskyblock;

import de.sasnex.sasnexskyblock.Commands.IslandCreate;
import de.sasnex.sasnexskyblock.Filemanager.FileManager;
import de.sasnex.sasnexskyblock.Listeners.CobblestoneGenerator;
import de.sasnex.sasnexskyblock.Listeners.GuiListener;
import de.sasnex.sasnexskyblock.Listeners.MiningListener;
import de.sasnex.sasnexskyblock.Listeners.NormalListener;
import de.sasnex.sasnexskyblock.Listeners.ShopSignListener;
import de.sasnex.sasnexskyblock.Listeners.VoidFallTeleport;
import de.sasnex.sasnexskyblock.Listeners.WorkerListener;
import de.sasnex.sasnexskyblock.Utils.VaultSystem;
import de.sasnex.sasnexskyblock.VoidGenerator.VoidGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SasnexSkyblock extends JavaPlugin {

    public static final String PREFIX = "&9[&7SasnexSkyblock&9] ";

    private static SasnexSkyblock instance;

    PluginManager pluginManager = getServer().getPluginManager();
    static FileManager fm = new FileManager();
    static VaultSystem vm = new VaultSystem();

    @Override
    public void onEnable() {
        instance = this;

        File dataFolder = getDataFolder();
        if (!fm.getServerFolder().exists()) {
            fm.createFolder();
        }
        File schemFolder = new File(dataFolder, "Schematics");
        if (!schemFolder.exists()) {
            schemFolder.mkdirs();
        }
        if (!new File(dataFolder, "players.yml").exists()) {
            saveResource("players.yml", false);
        }
        if (!new File(dataFolder, "island.yml").exists()) {
            saveResource("island.yml", false);
        }
        if (!new File(dataFolder, "Schematics/island.schem").exists()) {
            saveResource("Schematics/island.schem", false);
        }
        if (!new File(dataFolder, "shop.yml").exists()) {
            saveResource("shop.yml", false);
        }

        ensureSkyblockWorld();
        commands();

        pluginManager.registerEvents(new CobblestoneGenerator(), this);
        pluginManager.registerEvents(new VoidFallTeleport(), this);
        pluginManager.registerEvents(new GuiListener(), this);
        pluginManager.registerEvents(new MiningListener(), this);
        pluginManager.registerEvents(new NormalListener(), this);
        pluginManager.registerEvents(new WorkerListener(), this);
        pluginManager.registerEvents(new ShopSignListener(), this);

        Bukkit.getConsoleSender().sendMessage(color("&8[&bSasnexSkyblock&8] &7Aktiviert"));
    }

    void commands() {
        new IslandCreate();
    }

    @Override
    public void onDisable() {
        IslandCreate.stopAllWorkers();
        Bukkit.getConsoleSender().sendMessage(color("&8[&bSasnexSkyblock&8] &7Deaktiviert!"));
    }

    public static SasnexSkyblock getInstance() {
        return instance;
    }

    private void ensureSkyblockWorld() {
        if (Bukkit.getWorld("SasnexSkyblock") != null) return;

        WorldCreator wc = new WorldCreator("SasnexSkyblock");
        wc.generator(new VoidGenerator());
        var world = wc.createWorld();
        if (world != null) {
            preloadSpawnChunks(world, 3);
        }
    }

    private void preloadSpawnChunks(org.bukkit.World world, int radius) {
        int total = (radius * 2 + 1) * (radius * 2 + 1);
        final int[] loaded = {0};

        Bukkit.getScheduler().runTaskTimer(this, task -> {
            int perTick = 2;
            for (int i = 0; i < perTick; i++) {
                int index = loaded[0];
                if (index >= total) {
                    task.cancel();
                    return;
                }
                int dx = (index % (radius * 2 + 1)) - radius;
                int dz = (index / (radius * 2 + 1)) - radius;
                world.getChunkAt(dx, dz).load(true);
                loaded[0]++;
            }
        }, 1L, 1L);
    }

    public static FileManager getFileManager() {
        return fm;
    }

    public static VaultSystem getVaultSystem() {
        return vm;
    }

    public static String color(String text) {
        if (text == null) return null;
        String normalized = text
                .replace("\u00C2\u00A7", "&")
                .replaceAll("\uFFFD([0-9A-FK-ORa-fk-or])", "&$1")
                .replace('\u00A7', '&');
        return ChatColor.translateAlternateColorCodes('&', normalized);
    }
}
