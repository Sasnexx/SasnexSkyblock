package de.sasnex.sasnexskyblock.Listeners;

import de.sasnex.sasnexskyblock.GUIS.GUI;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopSignListener implements Listener {

    private final GUI gui = new GUI();

    @EventHandler
    public void onSignWrite(SignChangeEvent event) {
        String line0 = event.getLine(0);
        if (line0 == null) return;

        if (!line0.equalsIgnoreCase("[Shop]")) return;

        event.setLine(0, SasnexSkyblock.color("&8[&bShop&8]"));
        event.setLine(1, SasnexSkyblock.color("&7Rechtsklick"));
        event.setLine(2, SasnexSkyblock.color("&7für Shop"));
        event.getPlayer().sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&aShop-Schild erstellt."));
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;
        if (!(block.getState() instanceof Sign sign)) return;

        String line0 = ChatColor.stripColor(sign.getLine(0));
        if (line0 == null) return;
        if (!line0.equalsIgnoreCase("[Shop]")) return;

        event.setCancelled(true);
        event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
        event.setUseItemInHand(org.bukkit.event.Event.Result.DENY);

        Player player = event.getPlayer();
        gui.openMainShop(player);
    }
}
