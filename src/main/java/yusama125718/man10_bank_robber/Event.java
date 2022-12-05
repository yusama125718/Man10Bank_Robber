package yusama125718.man10_bank_robber;

import it.unimi.dsi.fastutil.Hash;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static net.kyori.adventure.text.Component.text;
import static yusama125718.man10_bank_robber.Man10_Bank_Robber.*;

public class Event implements Listener {
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
    private static HashMap<Player, HashMap<Integer, ItemStack>> editplayer = new HashMap<>();
    private static HashMap<Player, List<Integer>> editplayerlist = new HashMap<>();
    private static HashMap<Player, Data.AddItemData> addshoplist = new HashMap<>();
    private static HashMap<ItemStack, String> edit = new HashMap<>();
    private static HashMap<Player, Inventory> editinv = new HashMap<>();
    private static HashMap<Player, List<Integer>> money = new HashMap<>();

    public Event(Man10_Bank_Robber plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void MainMenuClick(InventoryClickEvent e) {
        if (!e.getView().title().equals(text("[MBR] MainMenu")) || e.getInventory().getSize() != 9) return;
        e.setCancelled(true);
        if (e.getRawSlot() == 4){
            if (!gamestatus.equals(Data.Status.entry) || !entrymode){
                e.getWhoClicked().sendMessage("§e§l[MBR] §r今はエントリーできません");
                e.getWhoClicked().closeInventory();
                return;
            }
            if (entryplayer.containsKey((Player) e.getWhoClicked())){
                e.getWhoClicked().sendMessage("§e§l[MBR] §rあなたはすでにエントリーしています");
                e.getWhoClicked().closeInventory();
                return;
            }
            GUI.BetMenu((Player) e.getWhoClicked());
        }
        else if (e.getRawSlot() == 8){
            //ここにスタッツ取得処理をいれる
        }
    }

    @EventHandler
    public void BetMenuClick(InventoryClickEvent e) {
        if (!e.getView().title().equals(text("[MBR] BetMenu")) || e.getInventory().getSize() != 54) return;
        e.setCancelled(true);
        switch (e.getRawSlot()){
            case 50: //0
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(0)).withName("0").build());
                    break;
                }
                break;

            case 40: //1
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(1)).withName("1").build());
                    break;
                }
                break;

            case 41: //2
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(2)).withName("2").build());
                    break;
                }
                break;

            case 42: //3
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(3)).withName("3").build());
                    break;
                }
                break;

            case 31: //4
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(4)).withName("4").build());
                    break;
                }
                break;

            case 32: //5
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(5)).withName("5").build());
                    break;
                }
                break;

            case 33: //6
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(6)).withName("6").build());
                    break;
                }
                break;

            case 22: //7
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(7)).withName("7").build());
                    break;
                }
                break;

            case 23: //8
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(8)).withName("8").build());
                    break;
                }
                break;

            case 24: //9
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(9)).withName("9").build());
                    break;
                }
                break;

            case 34: //clear
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) == null) continue;
                    e.getInventory().setItem(i, null);
                }
                break;

            case 43: //delete
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) != null) continue;
                    if (i == 0) break;
                    e.getInventory().setItem(i - 1, null);
                    break;
                }
                break;

            case 52:
                if (!gamestatus.equals(Data.Status.entry)){
                    e.getWhoClicked().sendMessage("§e§l[MBR] §r今はエントリーできません");
                    e.getWhoClicked().closeInventory();
                    return;
                }
                if (entryplayer.containsKey((Player) e.getWhoClicked()) && entrymode){
                    e.getWhoClicked().sendMessage("§e§l[MBR] §rあなたはすでにエントリーしています");
                    e.getWhoClicked().closeInventory();
                    return;
                }
                StringBuilder number = new StringBuilder();
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) == null) continue;
                    if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(0)).withName("0").build())) number.append("0");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(1)).withName("1").build())) number.append("1");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(2)).withName("2").build())) number.append("2");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(3)).withName("3").build())) number.append("3");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(4)).withName("4").build())) number.append("4");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(5)).withName("5").build())) number.append("5");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(6)).withName("6").build())) number.append("6");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(7)).withName("7").build())) number.append("7");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(8)).withName("8").build())) number.append("8");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(9)).withName("9").build())) number.append("9");
                }
                double money = Double.parseDouble(number.toString());
                if (vaultapi.getBalance(e.getWhoClicked().getUniqueId()) < money){
                    e.getWhoClicked().sendMessage("§e§l[MBR] §r所持金が不足しています");
                    return;
                }
                vaultapi.withdraw(e.getWhoClicked().getUniqueId(), money);
                entryplayer.put((Player) e.getWhoClicked(), new Data.PlayerData(money));
                e.getWhoClicked().sendMessage("§e§l[MBR] §rエントリーしました");
                if (!entrymode) Bukkit.broadcast(Component.text("§e§l[MBR] §r" + e.getWhoClicked().getName() + "がエントリーしました"));
                e.getWhoClicked().closeInventory();
                break;
        }
    }

    @EventHandler
    public void BetMenuClose(InventoryCloseEvent e){
        if (entrymode || !entryplayer.containsKey((Player) e.getPlayer()) || entryplayer.get((Player) e.getPlayer()).bet != null || !e.getView().title().equals(Component.text("[MBR] BetMenu"))) return;
        entryplayer.remove((Player) e.getPlayer());
        Bukkit.broadcast(Component.text("§e§l[MBR] §r" + e.getPlayer().getName() + "がベットしなかったのでエントリーをキャンセルしました"));
    }

    @EventHandler
    public void BuyMenuClose(InventoryCloseEvent e){
        if (!entryplayer.containsKey((Player) e.getPlayer()) || !gamestatus.equals(Data.Status.ready) || !e.getView().title().equals(Component.text("[MBR] BuyMenu"))) return;
        String title = null;
        Component component = e.getView().title();
        if (component instanceof TextComponent text) title = text.content();
        if (title == null || !(title.startsWith("[MBR Buy] ") || title.equals("[MBR] BuyMenu"))) return;
        GUI.BuyMenu((Player) e.getPlayer());
    }

    @EventHandler
    public void PlayerMove(PlayerMoveEvent e){
        if (!freeze || !gamestatus.equals(Data.Status.ready)) return;
        if (players.containsKey(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        if (!system || !gamestatus.equals(Data.Status.fight) || !players.containsKey(e.getPlayer())) return;
        if (players.get(e.getPlayer()).team.equals(Data.Team.blue)) e.getPlayer().teleport(bspawn);
        else e.getPlayer().teleport(yspawn);
    }

    @EventHandler
    public void ItemDrop(PlayerDropItemEvent e){
        if (!system || !(gamestatus.equals(Data.Status.ready) || gamestatus.equals(Data.Status.fight)) || !players.containsKey(e.getPlayer())) return;
        e.setCancelled(true);
        e.getPlayer().sendMessage("§e§l[MBR] §rゲーム中はアイテムを捨てることはできまません");
    }

    @EventHandler
    public void BuyMenuClick(InventoryClickEvent e){
        if (!e.getView().title().equals(text("[MBR] BuyMenu"))) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null || !buymenu.items.containsKey(e.getCurrentItem())) return;
        GUI.ShopMenu((Player) e.getWhoClicked(), buymenu.items.get(e.getCurrentItem()));
    }

    @EventHandler
    public void ShopClick(InventoryClickEvent e){
        String title = null;
        Component component = e.getView().title();
        if (component instanceof TextComponent text) title = text.content();
        if (title == null || !title.startsWith("[MBR Buy] ")) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;
        if (!shops.containsKey(title.substring(10))) return;
        Data.ShopData items = shops.get(title.substring(10));
        if (!items.items.containsKey(e.getCurrentItem())) return;
        if (players.get((Player) e.getWhoClicked()).money <= items.values.get(e.getCurrentItem())) return;
        players.get((Player) e.getWhoClicked()).money -= items.values.get(e.getCurrentItem());
        e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
        players.get((Player) e.getWhoClicked()).inv = e.getWhoClicked().getInventory();
    }

    @EventHandler
    public void EditBuyClick(InventoryClickEvent e){
        if (!e.getView().title().equals(text("[MBR] EditBuyMenu"))) return;
        if (e.getInventory().getSize() - 9 > e.getRawSlot()) return;
        HashMap<Integer, ItemStack> items = new HashMap<>();
        Inventory addinv = Bukkit.createInventory(null,e.getInventory().getSize() - 9, Component.text("[MBR] BuyMenu"));
        List<Integer> addlist = new ArrayList<>();
        e.setCancelled(true);
        for (int i = 0; i < e.getInventory().getSize() - 9; i++) {
            if (e.getInventory().getItem(i) == null) continue;
            addinv.setItem(i,e.getInventory().getItem(i));
            items.put(i,e.getInventory().getItem(i));
            addlist.add(i);
        }
        if (addlist.size() == 0) return;
        editplayer.put((Player) e.getWhoClicked(),items);
        GUI.AssignmentBuy(addlist.get(0), items.get(addlist.get(0)), (Player) e.getWhoClicked());
        addlist.remove(0);
        editplayerlist.put((Player) e.getWhoClicked(), addlist);
        editinv.put((Player) e.getWhoClicked(), addinv);
    }

    @EventHandler
    public void AssignmentBuyClick(InventoryClickEvent e){
        if (!e.getView().title().equals(text("[MBR] AssignmentBuy"))) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;
        if (e.getRawSlot() != 53 && e.getRawSlot() != 52){
            for (String s : shops.keySet()){
                if (e.getCurrentItem().equals(shops.get(s).icon)){
                    edit.put(e.getInventory().getItem(45),s);
                }
            }
        }
        else if (e.getRawSlot() == 52){
            String title = null;
            Component component = e.getInventory().getItem(47).getItemMeta().displayName();
            if (component instanceof TextComponent text) title = text.content();
            money.get((Player) e.getWhoClicked()).add(Integer.parseInt(title));
        }
        if (editplayerlist.get((Player) e.getWhoClicked()).size() == 0) return;
        List<Integer> intlist = editplayerlist.get((Player) e.getWhoClicked());
        GUI.AssignmentBuy(intlist.get(0), editplayer.get((Player) e.getWhoClicked()).get(intlist.get(0)), (Player) e.getWhoClicked());
        intlist.remove(0);
        buymenu = new Data.BuyMenuData(editinv.get((Player) e.getWhoClicked()), edit, money.get((Player) e.getWhoClicked()));
        Config.SaveBuyMenu(buymenu);
    }

    @EventHandler
    public void AssignmentBuyClose(InventoryCloseEvent e){
        if (!e.getView().title().equals(text("[MBR] AssignmentBuy"))) return;
        editplayer.remove((Player) e.getPlayer());
        editplayerlist.remove((Player) e.getPlayer());
        editinv.remove((Player) e.getPlayer());
    }

    @EventHandler
    public void EditShopClick(InventoryClickEvent e){
        String title = null;
        Component component = e.getView().title();
        if (component instanceof TextComponent text) title = text.content();
        if (title == null || !title.startsWith("[MBR EditShop] ")) return;
        if (e.getInventory().getSize() - 9 > e.getRawSlot()) return;
        e.setCancelled(true);
        HashMap<Integer, ItemStack> items = new HashMap<>();
        List<Integer> addlist = new ArrayList<>();
        Inventory addinv = Bukkit.createInventory(null,e.getInventory().getSize() - 9, Component.text("[MBR] BuyMenu"));
        for (int i = 0; i < e.getInventory().getSize() - 9; i++) {
            if (e.getInventory().getItem(i) == null) continue;
            addinv.setItem(i,e.getInventory().getItem(i));
            items.put(i,e.getInventory().getItem(i));
            addlist.add(i);
        }
        editplayer.put((Player) e.getWhoClicked(),items);
        GUI.AssignmentShop(addlist.get(0), items.get(addlist.get(0)), (Player) e.getWhoClicked());
        addlist.remove(0);
        editplayerlist.put((Player) e.getWhoClicked(), addlist);
        editinv.put((Player) e.getWhoClicked(), addinv);
        addshoplist.put((Player) e.getWhoClicked(), new Data.AddItemData(title.substring(15),null,null));
    }

    @EventHandler
    public void AssignmentShopClick(InventoryClickEvent e){
        if (!e.getView().title().equals(text("[MBR] AssignmentShop"))) return;
        switch (e.getRawSlot()) {
            case 50: //0
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(0)).withName("0").build());
                    break;
                }
                break;

            case 40: //1
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(1)).withName("1").build());
                    break;
                }
                break;

            case 41: //2
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(2)).withName("2").build());
                    break;
                }
                break;

            case 42: //3
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(3)).withName("3").build());
                    break;
                }
                break;

            case 31: //4
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(4)).withName("4").build());
                    break;
                }
                break;

            case 32: //5
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(5)).withName("5").build());
                    break;
                }
                break;

            case 33: //6
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(6)).withName("6").build());
                    break;
                }
                break;

            case 22: //7
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(7)).withName("7").build());
                    break;
                }
                break;

            case 23: //8
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(8)).withName("8").build());
                    break;
                }
                break;

            case 24: //9
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, new SkullMaker().withSkinUrl(numberTextures.get(9)).withName("9").build());
                    break;
                }
                break;

            case 34: //clear
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) == null) continue;
                    e.getInventory().setItem(i, null);
                }
                break;

            case 43: //delete
                e.setCancelled(true);
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    if (i == 0) break;
                    e.getInventory().setItem(i - 1, null);
                    break;
                }
                break;

            case 45:
                e.setCancelled(true);
                List<Integer> moneylist = new ArrayList<>();
                if (money.containsKey((Player) e.getWhoClicked())) moneylist = money.get((Player) e.getWhoClicked());
                String name = null;
                Component component = e.getInventory().getItem(30).getItemMeta().displayName();
                if (component instanceof TextComponent text) name = text.content();
                moneylist.add(Integer.parseInt(name));
                if (editplayerlist.get((Player) e.getWhoClicked()).size() == 0){
                    GUI.EditShopIcon((Player) e.getWhoClicked());
                } else {
                    int next = editplayerlist.get((Player) e.getWhoClicked()).get(0);
                    GUI.AssignmentShop(next, editplayer.get((Player) e.getWhoClicked()).get(next), (Player) e.getWhoClicked());
                }
                return;

            case 52:
                e.setCancelled(true);
                if (e.getInventory().getItem(38) == null) return;
                StringBuilder number = new StringBuilder();
                for (int i = 0; i < 9; i++){
                    if (e.getInventory().getItem(i) == null) continue;
                    if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(0)).withName("0").build())) number.append("0");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(1)).withName("1").build())) number.append("1");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(2)).withName("2").build())) number.append("2");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(3)).withName("3").build())) number.append("3");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(4)).withName("4").build())) number.append("4");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(5)).withName("5").build())) number.append("5");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(6)).withName("6").build())) number.append("6");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(7)).withName("7").build())) number.append("7");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(8)).withName("8").build())) number.append("8");
                    else if (e.getInventory().getItem(i).equals(new SkullMaker().withSkinUrl(numberTextures.get(9)).withName("9").build())) number.append("9");
                }
                double price = Double.parseDouble(number.toString());
                if (addshoplist.get((Player) e.getWhoClicked()).items == null){
                    HashMap<ItemStack, Double> values = new HashMap<>();
                    HashMap<ItemStack, ItemStack> items = new HashMap<>();
                    values.put(e.getInventory().getItem(47),price);
                    items.put(e.getInventory().getItem(47),e.getInventory().getItem(38));
                    addshoplist.get((Player) e.getWhoClicked()).items = items;
                    addshoplist.get((Player) e.getWhoClicked()).values = values;
                } else {
                    HashMap<ItemStack, Double> values = addshoplist.get((Player) e.getWhoClicked()).values;
                    HashMap<ItemStack, ItemStack> items = addshoplist.get((Player) e.getWhoClicked()).items;
                    values.put(e.getInventory().getItem(47),price);
                    items.put(e.getInventory().getItem(47),e.getInventory().getItem(38));
                    addshoplist.get((Player) e.getWhoClicked()).items = items;
                    addshoplist.get((Player) e.getWhoClicked()).values = values;
                }
                if (editplayerlist.get((Player) e.getWhoClicked()).size() == 0){
                    GUI.EditShopIcon((Player) e.getWhoClicked());
                } else {
                    int next = editplayerlist.get((Player) e.getWhoClicked()).get(0);
                    GUI.AssignmentShop(next, editplayer.get((Player) e.getWhoClicked()).get(next), (Player) e.getWhoClicked());
                }
        }
    }

    @EventHandler
    public void AssignmentShopClose(InventoryCloseEvent e){
        if (!(e.getView().title().equals(text("[MBR] AssignmentShop")) || e.getView().title().equals(text("[MBR] EditIcon")))) return;
        editplayer.remove((Player) e.getPlayer());
        editinv.remove((Player) e.getPlayer());
    }

    @EventHandler
    public void EditIconClick(InventoryClickEvent e){
        if (!e.getView().title().equals(text("[MBR] EditIcon"))) return;
        if (e.getRawSlot() == 4) return;
        if (e.getRawSlot() != 8){
            e.setCancelled(true);
            return;
        }
        if (e.getInventory().getItem(4) == null) return;
        Data.ShopData add = new Data.ShopData(editinv.get((Player) e.getWhoClicked()), addshoplist.get((Player) e.getWhoClicked()).items, addshoplist.get((Player) e.getWhoClicked()).values, e.getInventory().getItem(4));
        shops.put(addshoplist.get((Player) e.getWhoClicked()).name, add);
        e.getWhoClicked().closeInventory();
        editplayer.remove((Player) e.getWhoClicked());
        editinv.remove((Player) e.getWhoClicked());
        addshoplist.remove((Player) e.getWhoClicked());
        Config.SaveShops(addshoplist.get((Player) e.getWhoClicked()).name, add);
        e.getWhoClicked().sendMessage("§e§l[MBR] §r追加しました");
    }
}
