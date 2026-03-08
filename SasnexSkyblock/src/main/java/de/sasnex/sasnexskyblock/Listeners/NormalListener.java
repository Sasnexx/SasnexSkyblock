package de.sasnex.sasnexskyblock.Listeners;

import de.sasnex.sasnexskyblock.Commands.IslandCreate;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class NormalListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String path = "island." + player.getUniqueId() + ".money";
        if (!SasnexSkyblock.getFileManager().getPlayersDataCFG().contains(path)) {
            SasnexSkyblock.getFileManager().setPlayersData(path, 500);
        }

        event.setJoinMessage(SasnexSkyblock.color("&8[&bSasnexSkyblock&8] - &e" + player.getName() + "&7 ist dem Server beigetreten."));
    }

    @EventHandler
    public void onServerList(ServerListPingEvent event) {
        event.setMotd(SasnexSkyblock.color("      &6&lSASNEX SKYBLOCK &7- &f[1.21]\n&e      » &bEigene Inseln &7| &bShop &7| &c&lHUEHNER! &e«"));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        IslandCreate.stopWorker(player.getUniqueId());
        event.setQuitMessage(SasnexSkyblock.color("&8[&bSasnexSkyblock&8] - &e" + player.getName() + "&7 hat den Server verlassen"));
    }
}
