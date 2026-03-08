package de.sasnex.sasnexskyblock.Listeners;

import de.sasnex.sasnexskyblock.GUIS.GUI;
import de.sasnex.sasnexskyblock.SasnexSkyblock;
import de.sasnex.sasnexskyblock.Utils.Shop.ShopItem;
import de.sasnex.sasnexskyblock.Utils.Shop.ShopService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {

    private final GUI gui = new GUI();
    private final ShopService shopService = new ShopService();

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        String title = event.getView().getTitle();
        String shopKey = shopService.resolveShopKeyByTitle(title);
        if (shopKey == null) return;

        event.setCancelled(true);

        if (!event.getClickedInventory().equals(event.getView().getTopInventory())) return;

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) return;

        Player player = (Player) event.getWhoClicked();

        ShopItem si = shopService.getItems(shopKey).get(clickedItem.getType());
        if (si == null) return;

        if (si.targetShop() != null && !si.targetShop().isEmpty()) {
            gui.openShop(player, si.targetShop());
            return;
        }

        if (si.buyPrice() <= 0 && si.sellPrice() <= 0) return;

        if (event.getClick() == ClickType.RIGHT) {
            sellItem(player, si.material(), si.sellPrice(), si.buyAmount());
        } else {
            buyItem(player, si.material(), si.buyPrice(), si.buyAmount());
        }
    }

    private void buyItem(Player player, Material material, int price, int amount) {
        if (SasnexSkyblock.getVaultSystem().removeMoney(player, price)) {
            player.getInventory().addItem(new ItemStack(material, amount));
        } else {
            player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&cNicht genug Geld."));
        }
    }

    private void sellItem(Player player, Material material, int sellPrice, int amount) {
        if (!player.getInventory().contains(material, amount)) {
            player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&cNicht genug Items."));
            return;
        }

        player.getInventory().removeItem(new ItemStack(material, amount));
        SasnexSkyblock.getVaultSystem().addMoney(player, sellPrice);
        player.sendMessage(SasnexSkyblock.color(SasnexSkyblock.PREFIX + "&aVerkauft fuer " + sellPrice + "€."));
    }
}
