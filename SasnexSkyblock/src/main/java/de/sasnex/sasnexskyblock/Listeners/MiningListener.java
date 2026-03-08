package de.sasnex.sasnexskyblock.Listeners;

import de.sasnex.sasnexskyblock.SasnexSkyblock;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MiningListener implements Listener {

    private static final Map<UUID, Integer> BLOCK_COUNTER = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        handleMine(event.getPlayer(), event.getBlock().getType());
    }

    public static void handleMine(Player player, Material material) {
        if (!isOre(material)) return;

        UUID uuid = player.getUniqueId();
        int count = BLOCK_COUNTER.getOrDefault(uuid, 0) + 1;
        BLOCK_COUNTER.put(uuid, count);

        if (count % 20 == 0) {
            SasnexSkyblock.getVaultSystem().addMoney(player, 50);
            player.giveExp(100);
            sendMiningMessage(player, material, count);
        }
    }

    private static boolean isOre(Material m) {
        return m == Material.STONE || m == Material.COAL_ORE || m == Material.IRON_ORE
                || m == Material.GOLD_ORE || m == Material.DIAMOND_ORE
                || m == Material.COBBLESTONE;
    }

    private static void sendMiningMessage(Player player, Material material, int total) {
        player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&7Du hast bereits &e" + total + " &7Bloecke abgebaut!"));
        player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&7Du hast &e50€ &7bekommen :)"));

        if (material == Material.DIAMOND_ORE) {
            player.sendMessage(SasnexSkyblock.color("&bRespekt! Dein 20. Block war sogar ein Diamant!"));
        }
    }
}
