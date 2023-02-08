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
import static yusama125718.man10_bank_robber.Man10_Bank_Robber.*;

public class GUI {
    public static void MainMenu(Player p){
        Inventory inv = Bukkit.createInventory(null,9, Component.text("[MBR] MainMenu"));
        inv.setItem(4, GetItem(Material.CLOCK, 1, "エントリー", 0));
        inv.setItem(8, GetItem(Material.BOOK, 1, "スタッツを確認する", 0));
        p.openInventory(inv);
    }

    public static void BetMenu(Player p){
        Inventory inv = Bukkit.createInventory(null,54, Component.text("[MBR] BetMenu"));
        inv.setItem(22, GetItem(Material.QUARTZ, 1, "7", 55));
        inv.setItem(23, GetItem(Material.QUARTZ, 1, "8", 56));
        inv.setItem(24, GetItem(Material.QUARTZ, 1, "9", 57));
        inv.setItem(31, GetItem(Material.QUARTZ, 1, "4", 52));
        inv.setItem(32, GetItem(Material.QUARTZ, 1, "5", 53));
        inv.setItem(33, GetItem(Material.QUARTZ, 1, "6", 54));
        inv.setItem(34, GetItem(Material.TNT, 1, "クリア", 0));
        inv.setItem(40, GetItem(Material.QUARTZ, 1, "1", 49));
        inv.setItem(41, GetItem(Material.QUARTZ, 1, "2", 50));
        inv.setItem(42, GetItem(Material.QUARTZ, 1, "3", 51));
        inv.setItem(43, GetItem(Material.REDSTONE_BLOCK, 1, "１文字消す", 0));
        inv.setItem(50, GetItem(Material.QUARTZ, 1, "0", 48));
        inv.setItem(52, GetItem(Material.EMERALD_BLOCK, 1, "決定", 1));
        p.openInventory(inv);
    }

    public static void BuyMenu(Player p){
        Inventory inv = buymenu.inv;
        for (int i = 0;i < inv.getSize(); i++){
            if (inv.getItem(i) == null) continue;
            int cmd = 0;
            if (inv.getItem(i).hasItemMeta() && inv.getItem(i).getItemMeta().hasCustomModelData()) cmd = inv.getItem(i).getItemMeta().getCustomModelData();
            if (buymenu.items.get(inv.getItem(i)).equals("money")) GetItem(inv.getItem(i).getType(), inv.getItem(i).getAmount(), "所持金："+players.get(p).money,cmd);
        }
        p.openInventory(inv);
    }

    public static void EditBuyMenu(Player p, Integer size){
        Inventory inv = Bukkit.createInventory(null,size + 9, Component.text("[MBR] EditBuyMenu"));
        for (int i = size; i < size + 9;i++) inv.setItem(i, GetItem(Material.RED_STAINED_GLASS_PANE,1,"決定",1));
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
        inv.setItem(22, GetItem(Material.QUARTZ, 1, "7", 55));
        inv.setItem(23, GetItem(Material.QUARTZ, 1, "8", 56));
        inv.setItem(24, GetItem(Material.QUARTZ, 1, "9", 57));
        inv.setItem(31, GetItem(Material.QUARTZ, 1, "4", 52));
        inv.setItem(32, GetItem(Material.QUARTZ, 1, "5", 53));
        inv.setItem(33, GetItem(Material.QUARTZ, 1, "6", 54));
        inv.setItem(34, GetItem(Material.TNT, 1, "クリア", 0));
        inv.setItem(40, GetItem(Material.QUARTZ, 1, "1", 49));
        inv.setItem(41, GetItem(Material.QUARTZ, 1, "2", 50));
        inv.setItem(42, GetItem(Material.QUARTZ, 1, "3", 51));
        inv.setItem(43, GetItem(Material.REDSTONE_BLOCK, 1, "１文字消す", 0));
        inv.setItem(50, GetItem(Material.QUARTZ, 1, "0", 48));
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
        Inventory inv = Bukkit.createInventory(null,size + 9, Component.text("[MBR EditShop] "+ name));
        for (int i = size; i < size + 9;i++) inv.setItem(i, GetItem(Material.RED_STAINED_GLASS_PANE,1,"決定",1));
        p.openInventory(inv);
    }

    public static void ShopMenu(Player p,String shop){
        Inventory inv = shops.get(shop).inv;
        for (int i = 0;i < inv.getSize(); i++){
            if (inv.getItem(i) == null) continue;
            if (shops.get(shop).values.get(inv.getItem(i)) == -1.0){
                int cmd = 0;
                if (inv.getItem(i).hasItemMeta() && inv.getItem(i).getItemMeta().hasCustomModelData()) cmd = inv.getItem(i).getItemMeta().getCustomModelData();
                GetItem(inv.getItem(i).getType(), inv.getItem(i).getAmount(), "所持金："+players.get(p).money,cmd);
            }
        }
        p.openInventory(inv);
    }

    public static void EditShopIcon(Player p){
        Inventory inv = Bukkit.createInventory(null,9, Component.text("[MBR] EditIcon"));
        for (int i = 0; i < 8; i++) if (i != 4) inv.setItem(i, GetItem(Material.WHITE_STAINED_GLASS_PANE,1,"空きスロットにアイコンをセット",1));
        inv.setItem(8, GetItem(Material.RED_STAINED_GLASS_PANE,1,"決定",1));
        p.openInventory(inv);
    }
}
