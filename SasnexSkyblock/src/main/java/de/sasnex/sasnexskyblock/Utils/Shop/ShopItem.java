package de.sasnex.sasnexskyblock.Utils.Shop;

import org.bukkit.Material;

public record ShopItem(
    String id,
    Material material, 
    int slot,
    String displayName,
    int buyPrice,
    int sellPrice,
    int buyAmount,
    String targetShop
) {}
