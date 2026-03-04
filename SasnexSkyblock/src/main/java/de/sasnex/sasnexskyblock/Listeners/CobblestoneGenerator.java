package de.sasnex.sasnexskyblock.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

import java.util.Random;

public class CobblestoneGenerator implements Listener{

    private final Random random = new Random();

    @EventHandler
    public void onGenerate(BlockFormEvent event){
        Block block = event.getBlock();
        Material newState = event.getNewState().getType();

        if (newState != Material.COBBLESTONE && newState != Material.STONE) {
            return; // Wenn es nur Schnee oder Eis ist -> Abbruch, wir machen nichts
        }

        double chance = random.nextDouble();

        if (chance < 0.01) { // 1% Chance auf Diamant
            event.getNewState().setType(Material.DIAMOND_ORE);
        } else if (chance < 0.05) { // 4% Chance auf Gold (0.01 bis 0.05)
            event.getNewState().setType(Material.GOLD_ORE);
        } else if (chance < 0.15) { // 10% Chance auf Eisen
            event.getNewState().setType(Material.IRON_ORE);
        } else if (chance < 0.30) { // 15% Chance auf Kohle
            event.getNewState().setType(Material.COAL_ORE);
        }
    }
}
