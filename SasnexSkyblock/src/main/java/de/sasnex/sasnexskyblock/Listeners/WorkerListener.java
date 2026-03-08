package de.sasnex.sasnexskyblock.Listeners;

import de.sasnex.sasnexskyblock.Commands.IslandCreate;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class WorkerListener implements Listener {

    @EventHandler
    public void onWorkerHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Villager villager)) return;

        if (!IslandCreate.stopWorkerByVillager(villager.getUniqueId())) return;

        event.setCancelled(true);

        if (event.getDamager() instanceof Player player) {
            player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&7Worker wurde entfernt."));
        }
    }
}
