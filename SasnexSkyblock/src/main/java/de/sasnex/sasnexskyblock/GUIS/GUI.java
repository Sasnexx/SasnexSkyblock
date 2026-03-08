package de.sasnex.sasnexskyblock.GUIS;

import de.sasnex.sasnexskyblock.SasnexSkyblock;
import de.sasnex.sasnexskyblock.Utils.Shop.ShopItem;
import de.sasnex.sasnexskyblock.Utils.Shop.ShopService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUI {

    public static final String MAIN_SHOP_KEY = "hub";
    private final ShopService shopService = new ShopService();

    public void openMainShop(Player player) {
        openShop(player, MAIN_SHOP_KEY);
    }

    public void openShop(Player player, String shopKey) {
        Inventory inv = Bukkit.createInventory(null, shopService.getSize(shopKey), shopService.getTitle(shopKey));

        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        if (fillerMeta != null) {
            fillerMeta.setDisplayName(" ");
            filler.setItemMeta(fillerMeta);
        }

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, filler);
        }

        for (ShopItem si : shopService.getItems(shopKey).values()) {
            ItemStack item = new ItemStack(si.material(), Math.max(1, si.buyAmount()));
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(si.displayName());

                if (si.buyPrice() <= 0 && si.sellPrice() <= 0) {
                    meta.setLore(List.of(SasnexSkyblock.color("&7Klick zum Oeffnen")));
                } else {
                    meta.setLore(List.of(
                            SasnexSkyblock.color("&aKaufe: &f" + si.buyPrice() + "€"),
                            SasnexSkyblock.color("&cVerkaufe: &f" + si.sellPrice() + "€"),
                            "",
                            SasnexSkyblock.color("&7Links = kaufen, Rechts = verkaufen")
                    ));
                }
                item.setItemMeta(meta);
            }
            inv.setItem(si.slot(), item);
        }
        player.openInventory(inv);
    }
}
