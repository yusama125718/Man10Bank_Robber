package yusama125718.man10_bank_robber;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static yusama125718.man10_bank_robber.Function.GetItem;
import static yusama125718.man10_bank_robber.Man10_Bank_Robber.buymenu;
import static yusama125718.man10_bank_robber.Man10_Bank_Robber.shops;

public class GUI {
    private static final List<String> numberTextures = new ArrayList(Arrays.asList(
        "http://textures.minecraft.net/texture/3f09018f46f349e553446946a38649fcfcf9fdfd62916aec33ebca96bb21b5",
        "http://textures.minecraft.net/texture/ca516fbae16058f251aef9a68d3078549f48f6d5b683f19cf5a1745217d72cc",
        "http://textures.minecraft.net/texture/4698add39cf9e4ea92d42fadefdec3be8a7dafa11fb359de752e9f54aecedc9a",
        "http://textures.minecraft.net/texture/b85d4fda56bfeb85124460ff72b251dca8d1deb6578070d612b2d3adbf5a8",
        "http://textures.minecraft.net/texture/f2a3d53898141c58d5acbcfc87469a87d48c5c1fc82fb4e72f7015a3648058",
        "http://textures.minecraft.net/texture/d1fe36c4104247c87ebfd358ae6ca7809b61affd6245fa984069275d1cba763",
        "http://textures.minecraft.net/texture/3ab4da2358b7b0e8980d03bdb64399efb4418763aaf89afb0434535637f0a1",
        "http://textures.minecraft.net/texture/297712ba32496c9e82b20cc7d16e168b035b6f89f3df014324e4d7c365db3fb",
        "http://textures.minecraft.net/texture/abc0fda9fa1d9847a3b146454ad6737ad1be48bdaa94324426eca0918512d",
        "http://textures.minecraft.net/texture/d6abc61dcaefbd52d9689c0697c24c7ec4bc1afb56b8b3755e6154b24a5d8ba"
    ));
    public static void MainMenu(Player p){
        Inventory inv = Bukkit.createInventory(null,9, Component.text("[MBR] MainMenu"));
        inv.setItem(4, GetItem(Material.CLOCK, 1, "エントリー", 0));
        inv.setItem(8, GetItem(Material.BOOK, 1, "スタッツを確認する", 0));
        p.openInventory(inv);
    }

    public static void BetMenu(Player p){
        Inventory inv = Bukkit.createInventory(null,54, Component.text("[MBR] BetMenu"));
        inv.setItem(22, new SkullMaker().withSkinUrl(numberTextures.get(7)).withName("7").build());
        inv.setItem(23, new SkullMaker().withSkinUrl(numberTextures.get(8)).withName("8").build());
        inv.setItem(24, new SkullMaker().withSkinUrl(numberTextures.get(9)).withName("9").build());
        inv.setItem(31, new SkullMaker().withSkinUrl(numberTextures.get(4)).withName("4").build());
        inv.setItem(32, new SkullMaker().withSkinUrl(numberTextures.get(5)).withName("5").build());
        inv.setItem(33, new SkullMaker().withSkinUrl(numberTextures.get(6)).withName("6").build());
        inv.setItem(34, GetItem(Material.TNT, 1, "クリア", 0));
        inv.setItem(40, new SkullMaker().withSkinUrl(numberTextures.get(1)).withName("1").build());
        inv.setItem(41, new SkullMaker().withSkinUrl(numberTextures.get(2)).withName("2").build());
        inv.setItem(42, new SkullMaker().withSkinUrl(numberTextures.get(3)).withName("3").build());
        inv.setItem(43, GetItem(Material.REDSTONE_BLOCK, 1, "１文字消す", 0));
        inv.setItem(50, new SkullMaker().withSkinUrl(numberTextures.get(0)).withName("0").build());
        inv.setItem(52, GetItem(Material.EMERALD_BLOCK, 1, "決定", 1));
        p.openInventory(inv);
    }

    public static void BuyMenu(Player p){
        p.openInventory(buymenu.inv);
    }

    public static void EditBuyMenu(Player p, Integer size){
        Inventory inv = Bukkit.createInventory(null,size, Component.text("[MBR] EditBuyMenu"));
        for (int i = 45; i < 54;i++) inv.setItem(i, GetItem(Material.RED_STAINED_GLASS_PANE,1,"決定",1));
        p.openInventory(inv);
    }

    public static void AssignmentBuy(Integer slot, ItemStack item, Player p){
        Inventory inv = Bukkit.createInventory(null,54, Component.text("[MBR] AssignmentBuy"));
        int i = 0;
        for (String s : shops.keySet()){
            inv.setItem(i,shops.get(s).icon);
            i++;
        }
        inv.setItem(45,item);
        inv.setItem(46, GetItem(Material.BLACK_STAINED_GLASS_PANE,1,"選択したshopはこのアイテムにセットされます",1));
        for (int j = 47; j < 52; j++) inv.setItem(j, GetItem(Material.WHITE_STAINED_GLASS_PANE,1,slot.toString(),1));
        inv.setItem(52, GetItem(Material.GOLD_NUGGET,1,"お金を表示する",1));
        inv.setItem(53, GetItem(Material.BARRIER,1,"割り当てない",1));
        p.openInventory(inv);
    }

    public static void AssignmentShop(Integer slot, ItemStack item, Player p){
        Inventory inv = Bukkit.createInventory(null,54, Component.text("[MBR] AssignmentShop"));
        inv.setItem(22, new SkullMaker().withSkinUrl(numberTextures.get(7)).withName("7").build());
        inv.setItem(23, new SkullMaker().withSkinUrl(numberTextures.get(8)).withName("8").build());
        inv.setItem(24, new SkullMaker().withSkinUrl(numberTextures.get(9)).withName("9").build());
        inv.setItem(31, new SkullMaker().withSkinUrl(numberTextures.get(4)).withName("4").build());
        inv.setItem(32, new SkullMaker().withSkinUrl(numberTextures.get(5)).withName("5").build());
        inv.setItem(33, new SkullMaker().withSkinUrl(numberTextures.get(6)).withName("6").build());
        inv.setItem(34, GetItem(Material.TNT, 1, "クリア", 0));
        inv.setItem(40, new SkullMaker().withSkinUrl(numberTextures.get(1)).withName("1").build());
        inv.setItem(41, new SkullMaker().withSkinUrl(numberTextures.get(2)).withName("2").build());
        inv.setItem(42, new SkullMaker().withSkinUrl(numberTextures.get(3)).withName("3").build());
        inv.setItem(43, GetItem(Material.REDSTONE_BLOCK, 1, "１文字消す", 0));
        inv.setItem(50, new SkullMaker().withSkinUrl(numberTextures.get(0)).withName("0").build());
        inv.setItem(52, GetItem(Material.EMERALD_BLOCK, 1, "決定", 1));
        inv.setItem(28, GetItem(Material.WHITE_STAINED_GLASS_PANE,1,slot.toString(),1));
        inv.setItem(29, GetItem(Material.BLACK_STAINED_GLASS_PANE,1,"下に販売アイテムをセットする",1));
        inv.setItem(30, GetItem(Material.WHITE_STAINED_GLASS_PANE,1,slot.toString(),1));
        inv.setItem(37, GetItem(Material.WHITE_STAINED_GLASS_PANE,1,slot.toString(),1));
        inv.setItem(39, GetItem(Material.WHITE_STAINED_GLASS_PANE,1,slot.toString(),1));
        inv.setItem(45, GetItem(Material.GOLD_NUGGET,1,"お金を表示する",1));
        inv.setItem(46, GetItem(Material.WHITE_STAINED_GLASS_PANE,1,slot.toString(),1));
        inv.setItem(47, item);
        inv.setItem(48, GetItem(Material.WHITE_STAINED_GLASS_PANE,1,slot.toString(),1));
        p.openInventory(inv);
    }

    public static void EditShopMenu(Player p, Integer size, String name){
        Inventory inv = Bukkit.createInventory(null,size, Component.text("[MBR EditShop] "+ name));
        for (int i = 45; i < 54;i++) inv.setItem(i, GetItem(Material.RED_STAINED_GLASS_PANE,1,"決定",1));
        p.openInventory(inv);
    }

    public static void ShopMenu(Player p,String shop){
        p.openInventory(shops.get(shop).inv);
    }

    public static void EditShopIcon(Player p){
        Inventory inv = Bukkit.createInventory(null,9, Component.text("[MBR] EditIcon"));
        for (int i = 0; i < 8; i++) if (i != 4) inv.setItem(i, GetItem(Material.WHITE_STAINED_GLASS_PANE,1,"空きスロットにアイコンをセット",1));
        inv.setItem(8, GetItem(Material.RED_STAINED_GLASS_PANE,1,"決定",1));
        p.openInventory(inv);
    }
}
