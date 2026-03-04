package de.sasnex.sasnexskyblock.Utils;

import de.sasnex.sasnexskyblock.Filemanager.FileManager;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import org.bukkit.entity.Player;

public class VaultSystem {

    FileManager fm = new FileManager();

    public int getBalance(Player player){
        String path = "island." + player.getUniqueId() + ".money";
        SasnexSkyblock.getFileManager().loadPlayersCFG();
        return SasnexSkyblock.getFileManager().getPlayersDataCFG().getInt(path);
    }

    public void setBalance(Player player, int amount){
        String path = "island." + player.getUniqueId() + ".money";
        SasnexSkyblock.getFileManager().setPlayersData(path, amount);
    }

    public void addMoney(Player player, int amount){
        int current = getBalance(player);
        setBalance(player, current + amount);
    }

    public boolean removeMoney(Player player, int amount){
        int current = getBalance(player);
        if (current < amount) return false;
        setBalance(player, current - amount);
        return true;
    }
}
