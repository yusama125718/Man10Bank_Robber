package yusama125718.man10_bank_robber;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static yusama125718.man10_bank_robber.Man10_Bank_Robber.mbr;
import static yusama125718.man10_bank_robber.Man10_Bank_Robber.shops;

public class Config {
    private static final File folder = new File(mbr.getDataFolder().getAbsolutePath() + File.separator + "shops");
    private static File shopfile;

    public static void LoadFile(){
        if (mbr.getDataFolder().listFiles() != null){
            for (File file : Objects.requireNonNull(mbr.getDataFolder().listFiles())) {
                if (file.getName().equals("shops")) {
                    LoadShops(file);
                    shopfile = file;
                    return;
                }
            }
        }
        if (folder.mkdir()) {
            shopfile = folder;
            Bukkit.broadcast(Component.text("§e§l[MBR] §rフォルダを作成しました"), "mbr.op");
        } else {
            Bukkit.broadcast(Component.text("§e§l[MBR] §rフォルダの作成に失敗しました"), "mbr.op");
        }
    }

    public static Data.BuyMenuData LoadBuyMenu(){
        if (mbr.getDataFolder().listFiles() != null){
            for (File file : Objects.requireNonNull(mbr.getDataFolder().listFiles())) {
                if (file.getName().equals("buymenu.yml")) {
                    YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
                    if (yml.get("size") == null || yml.getConfigurationSection("items") == null){
                        return new Data.BuyMenuData(Bukkit.createInventory(null,9, Component.text("[MBR] BuyMenu")), new HashMap<>(), new ArrayList<>());
                    }
                    Inventory inv = Bukkit.createInventory(null,yml.getInt("size"), Component.text("[MBR] BuyMenu"));
                    HashMap<ItemStack,String> additems = new HashMap<>();
                    List<Integer> money = new ArrayList<>();
                    ConfigurationSection items = yml.getConfigurationSection("items");
                    for (String s : items.getValues(false).keySet()){
                        if (items.getConfigurationSection(s).getString("post").equals("money")) money.add(Integer.parseInt(s));
                        else additems.put(items.getConfigurationSection(s).getItemStack("item"),items.getConfigurationSection(s).getString("post"));
                        inv.setItem(Integer.parseInt(s), items.getConfigurationSection(s).getItemStack("item"));
                    }
                    return new Data.BuyMenuData(inv, additems, money);
                }
            }
            return CreateBuyMenu();
        }
        else {
            Bukkit.broadcast(Component.text("§e§l[MBR] §rフォルダが見つかりません"), "mbr.op");
            return null;
        }
    }

    private static Data.BuyMenuData CreateBuyMenu(){
        new Thread(() -> {
            File folder = new File(mbr.getDataFolder().getAbsolutePath() + File.separator + "buymenu.yml");
            YamlConfiguration yml = new YamlConfiguration();
            yml.set("size", 9);
            yml.set("items", null);
            try {
                yml.save(folder);
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.broadcast(Component.text("§e§l[MBR] §rbuymenu.ymlの保存に失敗しました"),"mserial.op");
            }
        }).start();
        return new Data.BuyMenuData(Bukkit.createInventory(null,9, Component.text("[MBR] BuyMenu")), new HashMap<>(), new ArrayList<>());
    }

    public static void SaveBuyMenu(Data.BuyMenuData data){
        new Thread(() -> {
            File folder = new File(mbr.getDataFolder().getAbsolutePath() + File.separator + "buymenu.yml");
            YamlConfiguration yml = new YamlConfiguration();
            yml.set("size", data.inv.getSize());
            for (int i = 0;i < data.inv.getSize();i++){
                if (data.inv.getItem(i) == null) continue;
                yml.set("items."+i+".item",data.inv.getItem(i));
                if (data.moneyslot.contains(i)) yml.set("items."+i+".post","money");
                else yml.set("items."+i+".post",data.items.get(data.inv.getItem(i)));
            }
            try {
                yml.save(folder);
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.broadcast(Component.text("§e§l[MBR] §rbuymenu.ymlの保存に失敗しました"),"mserial.op");
            }
        }).start();
    }

    public static void LoadShops(File files){
        if (files.listFiles() != null){
            for (File file : files.listFiles()){
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
                if (yml.getString("size") == null || yml.getString("name") == null || yml.getConfigurationSection("items") == null){
                    Bukkit.broadcast(Component.text("§e§l[MBR] §r" + file.getName() + "の読み込みに失敗しました"),"mbr.op");
                    continue;
                }
                ConfigurationSection items = yml.getConfigurationSection("items");
                HashMap<ItemStack, ItemStack> itemlist = new HashMap<>();
                HashMap<ItemStack, Double> valuelist = new HashMap<>();
                Inventory inv = Bukkit.createInventory(null, yml.getInt("size"),Component.text("[MBR Buy] " + yml.getString("name")));
                for (String s : items.getValues(false).keySet()) {
                    inv.setItem(Integer.parseInt(s), items.getConfigurationSection(s).getItemStack("icon"));
                    itemlist.put(items.getConfigurationSection(s).getItemStack("icon"),items.getConfigurationSection(s).getItemStack("item"));
                    valuelist.put(items.getConfigurationSection(s).getItemStack("icon"),items.getConfigurationSection(s).getDouble("price"));
                }
                shops.put(yml.getString("name"),new Data.ShopData(inv,itemlist,valuelist,items.getItemStack("icon")));
            }
        }
    }

    public static void SaveShops(String name, Data.ShopData data){
        new Thread(() -> {
            File folder = new File(shopfile.getAbsolutePath() + File.separator + name +".yml");
            YamlConfiguration yml = new YamlConfiguration();
            yml.set("size", data.inv.getSize());
            for (int i = 0;i < data.inv.getSize();i++){
                if (data.inv.getItem(i) == null) continue;
                yml.set("icon",data.inv.getItem(i));
                yml.set("item",data.items.get(data.inv.getItem(i)));
                yml.set("price",data.values.get(data.inv.getItem(i)));
            }
            try {
                yml.save(folder);
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.broadcast(Component.text("§e§l[MBR] §rbuymenu.ymlの保存に失敗しました"),"mserial.op");
            }
        }).start();
    }
}
