package de.sasnex.sasnexskyblock.Interfaces;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public interface IFileManager {
    //Create Folder
    void createFolder();

    void createSchemFolder();

    //set Data's
    void setIslandData(String path, Object value);

    void setPlayersData(String path, Object value);

    //load YamlConfiguration file
    void loadIslandCFG();

    void loadPlayersCFG();

    //getting YamlConfiguration file
    YamlConfiguration getIslandCFG();

    YamlConfiguration getPlayersDataCFG();

    //getServerPrefix
    String getServerPrefix();

    //save YamlConfiguration file
    void saveislandCFG();

    void savePlayersCFG();

    //getting serversystem Main folder (example: "plugins/Serversystem")
    File getServerFolder();

    File getSchematicsFolder();
}
