package de.sasnex.sasnexskyblock.Filemanager;


import de.sasnex.sasnexskyblock.Interfaces.IFileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager implements IFileManager {
    File path = new File("plugins/SasnexSkyblock");
    File schematics = new File(path, "Schematics");

    File islandFile = new File(path+ "/island.yml");
    File playerDataFile = new File(path+"/players.yml");

    YamlConfiguration islandCFG = YamlConfiguration.loadConfiguration(islandFile);
    YamlConfiguration playerDataCFG = YamlConfiguration.loadConfiguration(playerDataFile);

    public FileManager (){}

    @Override
    public void createFolder() {
        if(!path.exists()){
            path.mkdir();
        }
    }

    @Override
    public void createSchemFolder() {
        if(!schematics.exists()){
            schematics.mkdir();
        }
    }

    @Override
    public void setIslandData(String path, Object value) {
        try{
            loadIslandCFG();
            islandCFG.set(path,value);
            saveislandCFG();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§cDatei §5island.yml §cDaten können nicht gesetzt werden\n Grund: §4"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPlayersData(String path, Object value) {
        try{
            loadPlayersCFG();
            playerDataCFG.set(path,value);
            savePlayersCFG();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§cDatei §5players.yml §cDaten können nicht gesetzt werden\n Grund: §4"+e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public void loadIslandCFG() {
        try{
            islandCFG.load(islandFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("§cDatei §5island.yml §ckonnte nicht geladen werden \n Grund: §4"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadPlayersCFG() {
        try{
            playerDataCFG.load(playerDataFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("§cDatei §5players.yml §ckonnte nicht geladen werden \n Grund: §4"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public YamlConfiguration getIslandCFG() {
        return islandCFG;
    }

    @Override
    public YamlConfiguration getPlayersDataCFG() {return playerDataCFG;}

    @Override
    public String getServerPrefix() {
        return "";
    }

    @Override
    public void saveislandCFG() {
        try{
            islandCFG.save(islandFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§cDatei §5island.yml §ckonnte nicht gespeichert werden \n Grund: §4"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePlayersCFG(){
        try{
            playerDataCFG.save(playerDataFile);
        } catch (IOException e){
            Bukkit.getConsoleSender().sendMessage("§cDatei §5players.yml §ckonnte nicht gespeichert werden \n Grund: §4"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getServerFolder() {
        return path;
    }

    @Override
    public File getSchematicsFolder(){return schematics;}
}
