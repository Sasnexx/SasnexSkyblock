package de.sasnex.sasnexskyblock.Utils.Shop;

import de.sasnex.sasnexskyblock.SasnexSkyblock;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.ChatColor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShopService {

    private final YamlConfiguration cfg = SasnexSkyblock.getFileManager().getShopCFG();
    private final Map<String, String> titles = new HashMap<>();
    private final Map<String, Integer> sizes = new HashMap<>();
    private final Map<String, Map<Material, ShopItem>> itemsByShop = new HashMap<>();

    public ShopService() {
        SasnexSkyblock.getFileManager().loadShopCFG();
        load();
    }

    private void load() {
        ConfigurationSection shops = cfg.getConfigurationSection("shops");
        if (shops == null) return;

        for (String shopKey : shops.getKeys(false)) {
            String base = "shops." + shopKey;

            titles.put(shopKey, SasnexSkyblock.color(cfg.getString(base + ".title", shopKey)));
            sizes.put(shopKey, cfg.getInt(base + ".size", 54));

            Map<Material, ShopItem> map = new HashMap<>();
            ConfigurationSection items = cfg.getConfigurationSection(base + ".items");
            if (items != null) {
                for (String itemId : items.getKeys(false)) {
                    String itemBase = base + ".items." + itemId + ".";
                    String matName = cfg.getString(itemBase + "material");
                    if (matName == null) continue;

                    Material material = Material.matchMaterial(matName);
                    if (material == null) continue;

                    ShopItem item = new ShopItem(
                            itemId,
                            material,
                            cfg.getInt(itemBase + "slot"),
                            SasnexSkyblock.color(cfg.getString(itemBase + "display", material.name())),
                            cfg.getInt(itemBase + "buy-price", 0),
                            cfg.getInt(itemBase + "sell-price", 0),
                            cfg.getInt(itemBase + "buy-amount", 1),
                            cfg.getString(itemBase + "target-shop")
                    );
                    map.put(material, item);
                }
            }
            itemsByShop.put(shopKey, map);
        }
    }

    public String getTitle(String shopKey) {
        return titles.getOrDefault(shopKey, shopKey);
    }

    public int getSize(String shopKey) {
        return sizes.getOrDefault(shopKey, 54);
    }

    public Map<Material, ShopItem> getItems(String shopKey) {
        return itemsByShop.getOrDefault(shopKey, Collections.emptyMap());
    }

    public String resolveShopKeyByTitle(String title) {
        String cleanTitle = stripColorsLoose(title);
        for (Map.Entry<String, String> entry : titles.entrySet()) {
            if (stripColorsLoose(entry.getValue()).equalsIgnoreCase(cleanTitle)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String stripColorsLoose(String text) {
        if (text == null) return "";
        return ChatColor.stripColor(SasnexSkyblock.color(text));
    }
}
