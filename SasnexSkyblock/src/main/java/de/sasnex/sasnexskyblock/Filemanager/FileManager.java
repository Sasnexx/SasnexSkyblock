package de.sasnex.sasnexskyblock.Filemanager;

import de.sasnex.sasnexskyblock.Interfaces.IFileManager;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager implements IFileManager {
    File path = new File("plugins/SasnexSkyblock");
    File schematics = new File(path, "Schematics");

    File islandFile = new File(path + "/island.yml");
    File playerFile = new File(path + "/players.yml");
    File shopFile = new File(path + "/shop.yml");

    YamlConfiguration islandCFG = YamlConfiguration.loadConfiguration(islandFile);
    YamlConfiguration playerCFG = YamlConfiguration.loadConfiguration(playerFile);
    YamlConfiguration shopCFG = YamlConfiguration.loadConfiguration(shopFile);

    public FileManager() {}

    @Override
    public void createFolder() {
        if (!path.exists()) {
            path.mkdir();
        }
    }

    @Override
    public void createSchemFolder() {
        if (!schematics.exists()) {
            schematics.mkdir();
        }
    }

    @Override
    public void setIslandData(String path, Object value) {
        try {
            loadIslandCFG();
            islandCFG.set(path, value);
            saveislandCFG();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(SasnexSkyblock.color("&cDatei &5island.yml &cDaten koennen nicht gesetzt werden\n&4Grund: " + e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPlayersData(String path, Object value) {
        try {
            loadPlayersCFG();
            playerCFG.set(path, value);
            savePlayersCFG();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(SasnexSkyblock.color("&cDatei &5players.yml &cDaten koennen nicht gesetzt werden\n&4Grund: " + e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setShopData(String path, Object value) {
        try {
            loadShopCFG();
            shopCFG.set(path, value);
            saveShopCFG();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(SasnexSkyblock.color("&cDatei &5shop.yml &cDaten koennen nicht gesetzt werden\n&4Grund: " + e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadIslandCFG() {
        try {
            islandCFG.load(islandFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage(SasnexSkyblock.color("&cDatei &5island.yml &ckonnte nicht geladen werden\n&4Grund: " + e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadPlayersCFG() {
        try {
            playerCFG.load(playerFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage(SasnexSkyblock.color("&cDatei &5players.yml &ckonnte nicht geladen werden\n&4Grund: " + e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadShopCFG() {
        try {
            shopCFG.load(shopFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage(SasnexSkyblock.color("&cDatei &5shop.yml &ckonnte nicht geladen werden\n&4Grund: " + e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public YamlConfiguration getIslandCFG() {
        return islandCFG;
    }

    @Override
    public YamlConfiguration getPlayersDataCFG() {
        return playerCFG;
    }

    @Override
    public YamlConfiguration getShopCFG() {
        return shopCFG;
    }

    @Override
    public String getServerPrefix() {
        return SasnexSkyblock.PREFIX;
    }

    @Override
    public void saveislandCFG() {
        try {
            islandCFG.save(islandFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(SasnexSkyblock.color("&cDatei &5island.yml &ckonnte nicht gespeichert werden\n&4Grund: " + e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePlayersCFG() {
        try {
            playerCFG.save(playerFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(SasnexSkyblock.color("&cDatei &5players.yml &ckonnte nicht gespeichert werden\n&4Grund: " + e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveShopCFG() {
        try {
            shopCFG.save(shopFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(SasnexSkyblock.color("&cDatei &5shop.yml &ckonnte nicht gespeichert werden\n&4Grund: " + e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getServerFolder() {
        return path;
    }

    @Override
    public File getSchematicsFolder() {
        return schematics;
    }
}
