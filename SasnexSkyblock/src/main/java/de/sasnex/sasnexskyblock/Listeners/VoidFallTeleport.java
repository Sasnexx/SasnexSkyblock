package de.sasnex.sasnexskyblock.Listeners;

import de.sasnex.sasnexskyblock.Filemanager.FileManager;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VoidFallTeleport implements Listener {

    @EventHandler
    public void onVoidDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.VOID) return;
        if (!(event.getEntity() instanceof Player player)) return;
        if (!"SasnexSkyblock".equals(player.getWorld().getName())) return;

        SasnexSkyblock.getFileManager().loadIslandCFG();
        String islandPath = "islands." + player.getUniqueId();
        if (!SasnexSkyblock.getFileManager().getIslandCFG().contains(islandPath)) return;

        double x = SasnexSkyblock.getFileManager().getIslandCFG().getDouble(islandPath + ".x");
        double z = SasnexSkyblock.getFileManager().getIslandCFG().getDouble(islandPath + ".z");

        Location islandSpawn = new Location(player.getWorld(), x, 100, z).add(0.5, 2, 0.5);

        event.setCancelled(true);
        player.teleport(islandSpawn);
        player.setFallDistance(0);
    }
}
