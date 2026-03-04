package de.sasnex.sasnexskyblock.Listeners;

import de.sasnex.sasnexskyblock.SasnexSkyblock;
import de.sasnex.sasnexskyblock.Utils.VaultSystem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.UUID;

public class MiningListener implements Listener {

    private HashMap<UUID, Integer> blockCounter = new HashMap<>();

    VaultSystem vm = new VaultSystem();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        UUID uuid = player.getUniqueId();

        if (isOre(material)) {
            // Aktuellen Stand holen oder 0 setzen
            int count = blockCounter.getOrDefault(uuid, 0) + 1;
            blockCounter.put(uuid, count);

            // Prüfen, ob die Zahl durch 10 teilbar ist.
            if (count % 20 == 0) {
                // Hier später die kak addMoney rotze
                SasnexSkyblock.getVaultSystem().addMoney(player, 50);
                player.giveExp(100);
                sendMiningMessage(player, material, count);
            }
        }
    }

    private boolean isOre(Material m) {
        return m == Material.STONE || m == Material.COAL_ORE || m == Material.IRON_ORE
                || m == Material.GOLD_ORE || m == Material.DIAMOND_ORE
                || m == Material.COBBLESTONE;
    }

    private void sendMiningMessage(Player player, Material material, int total) {
        String name = material.name().toLowerCase().replace("_", " ");
        player.sendMessage("§8[§bSasnexSkyblock§8] §7Du hast bereits §e" + total + " §7Blöcke abgebaut!");
        player.sendMessage("§8[§bSasnexSkyblock§8] §7Du hast §e50€ §7bekommen :)");

        // Spezielle Nachricht je nach letztem Block
        if (material == Material.DIAMOND_ORE) {
            player.sendMessage("§bRespekt! Dein 10. Block war sogar ein Diamant!");
        }
    }
}
