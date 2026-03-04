package de.sasnex.sasnexskyblock.GUIS;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUI {
    public static Inventory gui;

    public GUI() {}

    @Deprecated
    public void IslandShopGUI(Player player) {
        gui = Bukkit.createInventory(null, 36, "§b§lIsland Shop");

        ItemStack greyglass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassmeta = greyglass.getItemMeta();
        glassmeta.setDisplayName(" ");
        greyglass.setItemMeta(glassmeta);

        ItemStack grassBlock = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta grassmeta = grassBlock.getItemMeta();
        grassmeta.setDisplayName("§aBlöcke");
        grassBlock.setItemMeta(grassmeta);

        ItemStack chickenflesh = new ItemStack(Material.COOKED_CHICKEN);
        ItemMeta chickenmeta = chickenflesh.getItemMeta();
        chickenmeta.setDisplayName("§aEssen");
        chickenflesh.setItemMeta(chickenmeta);

        ItemStack goldeningot = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldmeta = goldeningot.getItemMeta();
        goldmeta.setDisplayName("§aStuff");
        goldeningot.setItemMeta(goldmeta);

        ItemStack wheat = new ItemStack(Material.WHEAT);
        ItemMeta wheatmeta = wheat.getItemMeta();
        wheatmeta.setDisplayName("§aFarm");
        wheat.setItemMeta(wheatmeta);

        ItemStack spidereye = new ItemStack(Material.SPIDER_EYE);
        ItemMeta spidereyeItemMeta = spidereye.getItemMeta();
        spidereyeItemMeta.setDisplayName("§aTier-Loot");
        spidereye.setItemMeta(spidereyeItemMeta);

        ItemStack saddle = new ItemStack(Material.SADDLE);
        ItemMeta saddleMeta = spidereye.getItemMeta();
        saddleMeta.setDisplayName("§aTier-Zubehör");
        saddle.setItemMeta(saddleMeta);

        for (int i = 0; i < 36; i++) {
            gui.setItem(i, greyglass);
        }

        gui.setItem(12, grassBlock);
        gui.setItem(13, chickenflesh);
        gui.setItem(14, goldeningot);
        gui.setItem(21, wheat);
        gui.setItem(22, spidereye);
        gui.setItem(23, saddle);

        player.openInventory(gui);
    }

    public void BlockShopGui(Player player) {
        gui = Bukkit.createInventory(null, 54, "§b§lMaterial kaufen");

        ItemStack greyglass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassmeta = greyglass.getItemMeta();
        glassmeta.setDisplayName(" ");
        greyglass.setItemMeta(glassmeta);

        for(int i = 0; i < 54; i++){
            gui.setItem(i, greyglass);
        }

        ItemStack grassBlock = createShopItem(Material.GRASS_BLOCK, 10, "§9Grass Block", 100);
        ItemStack dirtBlock = createShopItem(Material.DIRT, 10, "§9Erde", 50);
        ItemStack gravel = createShopItem(Material.GRAVEL, 10, "§9Kies", 100);
        ItemStack granite = createShopItem(Material.GRANITE, 10, "§9Granit", 50);
        ItemStack Diorite = createShopItem(Material.DIORITE, 10, "§9Diorit", 100);
        ItemStack Andesite = createShopItem(Material.ANDESITE, 10, "§9Andesit", 100);
        ItemStack Oaklog = createShopItem(Material.OAK_LOG, 16, "§9Eichen Stamm", 100);
        ItemStack sprucelog = createShopItem(Material.SPRUCE_LOG, 16, "§9Fichten Stamm", 100);
        ItemStack birchlog = createShopItem(Material.BIRCH_LOG, 16, "§9Birken Stamm", 100);
        ItemStack junglelog = createShopItem(Material.JUNGLE_LOG, 16, "§9Tropen Stamm", 100);
        ItemStack acacialog = createShopItem(Material.ACACIA_LOG, 16, "§9Akazia Stamm", 100);
        ItemStack darkoaklog = createShopItem(Material.DARK_OAK_LOG, 16, "§9dunkler Eichenstamm", 100);
        ItemStack snowblock = createShopItem(Material.SNOW_BLOCK, 16, "§9Schnee-Block", 200);
        ItemStack ice = createShopItem(Material.ICE, 8, "§9Eis", 300);
        ItemStack packedice = createShopItem(Material.PACKED_ICE, 8, "§Eis BLock", 300);
        ItemStack sponge = createShopItem(Material.SPONGE, 4, "§9Schwamm", 1000);
        ItemStack sand = createShopItem(Material.SAND, 8, "§9Sand", 80);
        ItemStack sandstone = createShopItem(Material.SANDSTONE, 16, "§9Sandstein", 70);
        ItemStack clayball = createShopItem(Material.CLAY_BALL, 32, "§9Lehmkugel", 250);
        ItemStack Obsidian = createShopItem(Material.OBSIDIAN, 4, "§9Obsidian", 175);
        ItemStack Glowstone = createShopItem(Material.GLOWSTONE, 8, "§9Glühstein", 250);
        ItemStack EndStone = createShopItem(Material.END_STONE, 4, "§9End-Stein", 200);
        ItemStack Prismarine = createShopItem(Material.PRISMARINE, 16, "§9Prisma", 250);
        ItemStack Wool = createShopItem(Material.WHITE_WOOL, 8, "§9Wolle", 250);
        ItemStack netherstar = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta nethermeta = netherstar.getItemMeta();
        nethermeta.setDisplayName("§cZurück");
        netherstar.setItemMeta(nethermeta);

        gui.setItem(10, grassBlock);
        gui.setItem(11, dirtBlock);
        gui.setItem(12, gravel);
        gui.setItem(13, granite);
        gui.setItem(14, Diorite);
        gui.setItem(15, Andesite);
        gui.setItem(16, Oaklog);
        gui.setItem(19, sprucelog);
        gui.setItem(20, birchlog);
        gui.setItem(21, junglelog);
        gui.setItem(22, acacialog);
        gui.setItem(23, darkoaklog);
        gui.setItem(24, snowblock);
        gui.setItem(25, ice);
        gui.setItem(28, packedice);
        gui.setItem(29, sponge);
        gui.setItem(30, sand);
        gui.setItem(31, sandstone);
        gui.setItem(32, clayball);
        gui.setItem(33, Obsidian);
        gui.setItem(34, Glowstone);
        gui.setItem(39, EndStone);
        gui.setItem(40, Prismarine);
        gui.setItem(41, Wool);
        gui.setItem(49, netherstar);

        player.openInventory(gui);
    }

    public void FoodShopGUI(Player player) {
        gui = Bukkit.createInventory(null, 36, "§b§lEssen");

        ItemStack greyglass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassmeta = greyglass.getItemMeta();
        glassmeta.setDisplayName(" ");
        greyglass.setItemMeta(glassmeta);

        for (int i = 0; i < 36; i++) {
            gui.setItem(i, greyglass);
        }

        ItemStack apple = createShopItem(Material.APPLE, 16, "§9Apfel", 120);
        ItemStack goldenApple = createShopItem(Material.GOLDEN_APPLE, 4, "§9Goldener Apfel", 300);
        ItemStack carrot = createShopItem(Material.CARROT, 16, "§9Karotte", 110);
        ItemStack bread = createShopItem(Material.BREAD, 16, "§9Brot", 140);
        ItemStack bakedPotato = createShopItem(Material.BAKED_POTATO, 16, "§9Ofenkartoffel", 130);
        ItemStack cookie = createShopItem(Material.COOKIE, 16, "§9Keks", 120);
        ItemStack mushroomStew = createShopItem(Material.MUSHROOM_STEW, 8, "§9Pilzsuppe", 160);
        ItemStack cookedSalmon = createShopItem(Material.COOKED_SALMON, 8, "§9Gebratener Lachs", 200);
        ItemStack cookedChicken = createShopItem(Material.COOKED_CHICKEN, 8, "§9Gebratenes Huhn", 190);
        ItemStack cookedMutton = createShopItem(Material.COOKED_MUTTON, 8, "§9Gebratenes Hammel", 190);

        ItemStack netherstar = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta nethermeta = netherstar.getItemMeta();
        nethermeta.setDisplayName("§cZurück");
        netherstar.setItemMeta(nethermeta);

        gui.setItem(11, apple);
        gui.setItem(12, goldenApple);
        gui.setItem(13, carrot);
        gui.setItem(14, bread);
        gui.setItem(15, bakedPotato);

        gui.setItem(20, cookie);
        gui.setItem(21, mushroomStew);
        gui.setItem(22, cookedSalmon);
        gui.setItem(23, cookedChicken);
        gui.setItem(24, cookedMutton);

        gui.setItem(31, netherstar);

        player.openInventory(gui);
    }

    public void MobShopGUI(Player player){}//soon

    public void FarmShopGUI(Player player){}//soon
    public void AnimalShopGUI(Player player){}//soon
    public void OreShopGUI(Player player){}//soon
    public void MiscShopGUI(Player player){}//soon

    public ItemStack createShopItem(Material material, int amount, String name, int price) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(Arrays.asList(
                    "§aBuy Price: §f"+price+"€",
                    "§cVerkaufen für: §f10€",
                    "",
                    "§5[1] Links-Klick zum kaufen"
            ));
            item.setItemMeta(meta);
        }
        return item;
    }
}


