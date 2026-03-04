package de.sasnex.sasnexskyblock.Listeners;

import de.sasnex.sasnexskyblock.GUIS.GUI;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import de.sasnex.sasnexskyblock.Utils.VaultSystem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {
    //Das wird schwierig das zu coden hahaha
    public GUI gui = new GUI();
    VaultSystem vm = new VaultSystem();

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        String title = event.getView().getTitle();
        if ("§b§lMaterial kaufen".equals(title)) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            if (clickedItem.getType().equals(Material.GRASS_BLOCK)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.GRASS_BLOCK, "Gras", "Gras",1);
                } else {
                    clicketItem(Material.GRASS_BLOCK, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.DIRT)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.DIRT, "Erde", "Erde",1);
                } else {
                    clicketItem(Material.DIRT, clickedItem, player, 100, 10);
                }
            }

            if (clickedItem.getType().equals(Material.GRAVEL)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.GRAVEL, "Gravel", "Gravel",1);
                } else {
                    clicketItem(Material.GRAVEL, clickedItem, player, 100, 10);
                }
            }

            if (clickedItem.getType().equals(Material.GRANITE)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.GRANITE, "Granit", "Granit",1);
                } else {
                    clicketItem(Material.GRANITE, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.DIORITE)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.DIORITE, "Diorit", "Diorit",1);
                } else {
                    clicketItem(Material.DIORITE, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.OAK_LOG)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.OAK_LOG, "Eichen Stamm", "Eichen Stamm",1);
                } else {
                    clicketItem(Material.OAK_LOG, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.ANDESITE)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.ANDESITE, "Andesit", "Andesit",1);
                } else {
                    clicketItem(Material.ANDESITE, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.SPRUCE_LOG)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.SPRUCE_LOG, "Schweinfelds Stamm", "Schweinfelds Stamm",1);
                } else {
                    clicketItem(Material.SPRUCE_LOG, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.BIRCH_LOG)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.BIRCH_LOG, "Birken Stamm", "Birken Stamm",1);
                } else {
                    clicketItem(Material.BIRCH_LOG, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.JUNGLE_LOG)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.JUNGLE_LOG, "Dschungelfelds Stamm", "Dschungelfelds Stamm",1);
                } else {
                    clicketItem(Material.JUNGLE_LOG, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.ACACIA_LOG)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.ACACIA_LOG, "Akazien Stamm", "Akazien Stamm",1);
                } else {
                    clicketItem(Material.ACACIA_LOG, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.DARK_OAK_LOG)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.DARK_OAK_LOG, "dunkler Eichenstamm", "dunkler Eichenstamm",1);
                } else {
                    clicketItem(Material.DARK_OAK_LOG, clickedItem, player, 100, 16);
                }
            }

            if (clickedItem.getType().equals(Material.SNOW_BLOCK)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.SNOW_BLOCK, "Schnee-Block", "Schnee-Block",1);
                } else {
                    clicketItem(Material.SNOW_BLOCK, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.ICE)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.ICE, "Eis", "Eis",1);
                } else {
                    clicketItem(Material.ICE, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.PACKED_ICE)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.PACKED_ICE, "Gefrierter Eis", "Gefrierter Eis",1);
                } else {
                    clicketItem(Material.PACKED_ICE, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.SPONGE)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.SPONGE, "Sponge", "Sponge",1);
                } else {
                    clicketItem(Material.SPONGE, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.SAND)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.SAND, "Sand", "Sand",1);
                } else {
                    clicketItem(Material.SAND, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.SANDSTONE)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.SANDSTONE, "Sandstein", "Sandstein",1);
                } else{
                    clicketItem(Material.SANDSTONE, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.CLAY_BALL)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.CLAY_BALL, "Klay", "Klay",1);
                } else {
                    clicketItem(Material.CLAY_BALL, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.OBSIDIAN)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.OBSIDIAN, "Obsidian", "Obsidian",1);
                } else {
                    clicketItem(Material.OBSIDIAN, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.GLOWSTONE)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.GLOWSTONE, "Glowstone", "Glowstone",1);
                } else {
                    clicketItem(Material.GLOWSTONE, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.END_STONE)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.END_STONE, "Endestein", "Endestein",1);
                } else {
                    clicketItem(Material.END_STONE, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.PRISMARINE)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.PRISMARINE, "Prisma", "Prisma",1);
                } else {
                    clicketItem(Material.PRISMARINE, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.WHITE_WOOL)) {
                if(event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.WHITE_WOOL, "Wolle", "Wolle",1);
                } else {
                    clicketItem(Material.WHITE_WOOL, clickedItem, player, 200, 16);
                }
            }

            if (clickedItem.getType().equals(Material.NETHER_STAR)) {
                gui.IslandShopGUI(player);
            }
            return;
        }

        if("§b§lEssen".equals(title)){
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            if (clickedItem.getType().equals(Material.APPLE)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.APPLE, "Apfel", "Äpfel",5);
                } else {
                    clicketItem(Material.APPLE, clickedItem, player, 70, 2);
                }
            }

            if (clickedItem.getType().equals(Material.GOLDEN_APPLE)) {
                if (event.getClick() == ClickType.RIGHT) {
                    sellItem(player, Material.GOLDEN_APPLE, "Apfel", "Äpfel",5);
                } else {
                    clicketItem(Material.GOLDEN_APPLE, clickedItem, player, 70, 2);
                }
            }

            if(clickedItem.getType().equals(Material.NETHER_STAR)) {
                gui.IslandShopGUI(player);
            }
        }

        if ("§b§lIsland Shop".equals(title)) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null) {
                Player player = (Player) event.getWhoClicked();
                if (clickedItem.getType().equals(Material.GRASS_BLOCK)) {
                    gui.BlockShopGui(player);//Offnet das GUI
                }

                if (clickedItem.getType().equals(Material.COOKED_CHICKEN)) {
                    gui.FoodShopGUI(player);
                }
            }
        }
    }

    public void clicketItem(Material material, ItemStack clickedItem, Player player, int price, int amount) {
        if (clickedItem.getType().equals(material)) {
            if (SasnexSkyblock.getVaultSystem().removeMoney(player, price)) {
                player.getInventory().addItem(new ItemStack(material, amount));
            } else {
                player.closeInventory();
                player.sendMessage("§9Du hast nicht genug Geld.");
            }
        }
    }

    private boolean sellItem(Player player, Material material, String displayName, String notEnoughName, int amount) {
        if (player.getInventory().contains(material, amount)) {
            player.getInventory().removeItem(new ItemStack(material, amount));
            SasnexSkyblock.getVaultSystem().addMoney(player, 10); // z.B. 10 pro Block
            player.sendMessage("§8[§bSasnexSkyblock§8] §7Verkauft: §e" + amount + " §7" + displayName + " für §a10€, §eGeld:" + SasnexSkyblock.getVaultSystem().getBalance(player));
        } else {
            player.sendMessage("§8[§bSasnexSkyblock§8] §cDu hast nicht genug §7" + notEnoughName + ".");
        }
        return true;
    }
}

