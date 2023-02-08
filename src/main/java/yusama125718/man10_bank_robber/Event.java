package yusama125718.man10_bank_robber;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static net.kyori.adventure.text.Component.text;
import static yusama125718.man10_bank_robber.Function.GetItem;
import static yusama125718.man10_bank_robber.Man10_Bank_Robber.*;

public class Event implements Listener {
    private static HashMap<UUID, HashMap<Integer, ItemStack>> editplayer = new HashMap<>();
    private static HashMap<UUID, List<Integer>> editplayerlist = new HashMap<>();
    private static HashMap<UUID, Data.AddItemData> addshoplist = new HashMap<>();
    private static HashMap<ItemStack, String> edit = new HashMap<>();
    private static HashMap<UUID, Inventory> editinv = new HashMap<>();
    private static HashMap<UUID, Boolean> nowedit = new HashMap<>();

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
            if (entryplayer.containsKey(e.getWhoClicked().getUniqueId())){
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
            case 50 -> { //0
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "0", 48));
                    break;
                }
            }
            case 40 -> { //1
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "1", 49));
                    break;
                }
            }
            case 41 -> { //2
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "2", 50));
                    break;
                }
            }
            case 42 -> { //3
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "3", 51));
                    break;
                }
            }
            case 31 -> { //4
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "4", 52));
                    break;
                }
            }
            case 32 -> { //5
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "5", 53));
                    break;
                }
            }
            case 33 -> { //6
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "6", 54));
                    break;
                }
            }
            case 22 -> { //7
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "7", 55));
                    break;
                }
            }
            case 23 -> { //8
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "8", 56));
                    break;
                }
            }
            case 24 -> { //9
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "9", 57));
                    break;
                }
            }
            case 34 -> { //clear
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) == null) continue;
                    e.getInventory().setItem(i, null);
                }
            }

            case 43 -> { //delete
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    if (i == 0) break;
                    e.getInventory().setItem(i - 1, null);
                    break;
                }
            }

            case 52 -> {
                if (!gamestatus.equals(Data.Status.entry)) {
                    e.getWhoClicked().sendMessage("§e§l[MBR] §r今はエントリーできません");
                    e.getWhoClicked().closeInventory();
                    return;
                }
                if (entryplayer.containsKey(e.getWhoClicked().getUniqueId()) && entrymode) {
                    e.getWhoClicked().sendMessage("§e§l[MBR] §rあなたはすでにエントリーしています");
                    e.getWhoClicked().closeInventory();
                    return;
                }
                StringBuilder number = new StringBuilder();
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) == null) continue;
                    if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "0", 48))) number.append("0");
                    else if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "1", 49))) number.append("1");
                    else if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "2", 50))) number.append("2");
                    else if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "3", 51))) number.append("3");
                    else if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "4", 52))) number.append("4");
                    else if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "5", 53))) number.append("5");
                    else if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "6", 54))) number.append("6");
                    else if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "7", 55))) number.append("7");
                    else if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "8", 56))) number.append("8");
                    else if (e.getInventory().getItem(i).isSimilar(GetItem(Material.QUARTZ, 1, "9", 57))) number.append("9");
                }
                double money = Double.parseDouble(number.toString());
                if (vaultapi.getBalance(e.getWhoClicked().getUniqueId()) < money) {
                    e.getWhoClicked().sendMessage("§e§l[MBR] §r所持金が不足しています");
                    return;
                }
                vaultapi.withdraw(e.getWhoClicked().getUniqueId(), money);
                entryplayer.put(e.getWhoClicked().getUniqueId(), new Data.PlayerData(money));
                e.getWhoClicked().sendMessage("§e§l[MBR] §rエントリーしました");
                if (!entrymode) Bukkit.broadcast(Component.text("§e§l[MBR] §r" + e.getWhoClicked().getName() + "がエントリーしました"));
                e.getWhoClicked().closeInventory();
            }
        }
    }

    @EventHandler
    public void BetMenuClose(InventoryCloseEvent e){
        if (entrymode || !entryplayer.containsKey(e.getPlayer().getUniqueId()) || entryplayer.get(e.getPlayer().getUniqueId()).bet != null || !e.getView().title().equals(Component.text("[MBR] BetMenu"))) return;
        entryplayer.remove(e.getPlayer().getUniqueId());
        Bukkit.broadcast(Component.text("§e§l[MBR] §r" + e.getPlayer().getName() + "がベットしなかったのでエントリーをキャンセルしました"));
    }

    @EventHandler
    public void BuyMenuClose(InventoryCloseEvent e){
        if (!entryplayer.containsKey(e.getPlayer().getUniqueId()) || !gamestatus.equals(Data.Status.ready) || !e.getView().title().equals(Component.text("[MBR] BuyMenu"))) return;
        String title = null;
        Component component = e.getView().title();
        if (component instanceof TextComponent text) title = text.content();
        if (title == null || !(title.startsWith("[MBR Buy] ") || title.equals("[MBR] BuyMenu"))) return;
        GUI.BuyMenu((Player) e.getPlayer());
    }

    @EventHandler
    public void PlayerMove(PlayerMoveEvent e){
        if (!freeze || !gamestatus.equals(Data.Status.ready)) return;
        if (players.containsKey(e.getPlayer().getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        if (!system || !gamestatus.equals(Data.Status.fight) || !players.containsKey(e.getPlayer().getUniqueId())) return;
        if (players.get(e.getPlayer().getUniqueId()).team.equals(Data.Team.blue)) e.getPlayer().teleport(bspawn);
        else e.getPlayer().teleport(yspawn);
    }

    @EventHandler
    public void ItemDrop(PlayerDropItemEvent e){
        if (!system || !(gamestatus.equals(Data.Status.ready) || gamestatus.equals(Data.Status.fight)) || !players.containsKey(e.getPlayer().getUniqueId())) return;
        e.setCancelled(true);
        e.getPlayer().sendMessage("§e§l[MBR] §rゲーム中はアイテムを捨てることはできまません");
    }

    @EventHandler
    public void BuyMenuClick(InventoryClickEvent e){
        if (!e.getView().title().equals(text("[MBR] BuyMenu"))) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null || !buymenu.items.containsKey(e.getCurrentItem())) return;
        if (buymenu.items.get(e.getCurrentItem()).equals("money") || buymenu.items.get(e.getCurrentItem()).equals("none")) return;
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
        if (items.values.get(e.getCurrentItem()) == -1.0) return;
        if (players.get(e.getWhoClicked().getUniqueId()).money <= items.values.get(e.getCurrentItem())) return;
        players.get(e.getWhoClicked().getUniqueId()).money -= items.values.get(e.getCurrentItem());
        e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
        players.get(e.getWhoClicked().getUniqueId()).inv = e.getWhoClicked().getInventory();
    }

    @EventHandler
    public void EditBuyClick(InventoryClickEvent e){
        if (!e.getView().title().equals(text("[MBR] EditBuyMenu"))) return;
        if (e.getClickedInventory() == null || e.getClickedInventory().getSize() == 41) return;
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
        editplayer.put(e.getWhoClicked().getUniqueId(),items);
        GUI.AssignmentBuy(addlist.get(0), items.get(addlist.get(0)), (Player) e.getWhoClicked());
        addlist.remove(0);
        editplayerlist.put(e.getWhoClicked().getUniqueId(), addlist);
        editinv.put(e.getWhoClicked().getUniqueId(), addinv);
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
        else if (e.getRawSlot() == 52) edit.put(e.getInventory().getItem(45),"money");
        else if (e.getRawSlot() == 53) edit.put(e.getInventory().getItem(45),"none");
        if (editplayerlist.get(e.getWhoClicked().getUniqueId()).size() == 0) {
            buymenu = new Data.BuyMenuData(editinv.get(e.getWhoClicked().getUniqueId()), edit);
            Config.SaveBuyMenu(buymenu);
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage("§e§l[MBR] §r登録しました");
            return;
        }
        List<Integer> intlist = editplayerlist.get(e.getWhoClicked().getUniqueId());
        nowedit.put(e.getWhoClicked().getUniqueId(),true);
        GUI.AssignmentBuy(intlist.get(0), editplayer.get(e.getWhoClicked().getUniqueId()).get(intlist.get(0)), (Player) e.getWhoClicked());
        intlist.remove(0);
    }

    @EventHandler
    public void AssignmentBuyClose(InventoryCloseEvent e){
        if (!e.getView().title().equals(text("[MBR] AssignmentBuy"))) return;
        if (nowedit.get(e.getPlayer().getUniqueId())) {
            nowedit.put(e.getPlayer().getUniqueId(),false);
            return;
        }
        editplayer.remove(e.getPlayer().getUniqueId());
        editplayerlist.remove(e.getPlayer().getUniqueId());
        editinv.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void EditShopClick(InventoryClickEvent e){
        if (e.getCurrentItem() == null) return;
        String title = null;
        Component component = e.getView().title();
        if (component instanceof TextComponent text) title = text.content();
        if (title == null || !title.startsWith("[MBR EditShop] ")) return;
        if (!e.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE) || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasCustomModelData() || e.getCurrentItem().getItemMeta().getCustomModelData() != 1) return;
        HashMap<Integer, ItemStack> items = new HashMap<>();
        List<Integer> addlist = new ArrayList<>();
        Inventory addinv = Bukkit.createInventory(null,e.getInventory().getSize() - 9, Component.text("[MBR] BuyMenu"));
        for (int i = 0; i < e.getInventory().getSize() - 9; i++) {
            if (e.getInventory().getItem(i) == null) continue;
            addinv.setItem(i,e.getInventory().getItem(i));
            items.put(i,e.getInventory().getItem(i));
            addlist.add(i);
        }
        editplayer.put(e.getWhoClicked().getUniqueId(),items);
        GUI.AssignmentShop(addlist.get(0), items.get(addlist.get(0)), (Player) e.getWhoClicked());
        addlist.remove(0);
        editplayerlist.put(e.getWhoClicked().getUniqueId(), addlist);
        editinv.put(e.getWhoClicked().getUniqueId(), addinv);
        addshoplist.put(e.getWhoClicked().getUniqueId(), new Data.AddItemData(title.substring(15),null,null));
    }

    @EventHandler
    public void AssignmentShopClick(InventoryClickEvent e){
        if (!e.getView().title().equals(text("[MBR] AssignmentShop"))) return;
        if (e.getClickedInventory() == null || e.getClickedInventory().getSize() == 41) return;
        e.setCancelled(true);
        switch (e.getRawSlot()) {
            case 50 -> { //0
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "0", 48));
                    break;
                }
            }
            case 40 -> { //1
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "1", 49));
                    break;
                }
            }
            case 41 -> { //2
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "2", 50));
                    break;
                }
            }
            case 42 -> { //3
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "3", 51));
                    break;
                }
            }
            case 31 -> { //4
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "4", 52));
                    break;
                }
            }
            case 32 -> { //5
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "5", 53));
                    break;
                }
            }
            case 33 -> { //6
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "6", 54));
                    break;
                }
            }
            case 22 -> { //7
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "7", 55));
                    break;
                }
            }
            case 23 -> { //8
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "8", 56));
                    break;
                }
            }
            case 24 -> { //9
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    e.getInventory().setItem(i, GetItem(Material.QUARTZ, 1, "9", 57));
                    break;
                }
            }
            case 34 -> { //clear
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) == null) continue;
                    e.getInventory().setItem(i, null);
                }
            }
            case 43 -> { //delete
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) != null) continue;
                    if (i == 0) break;
                    if (i == 8) e.getInventory().setItem(i, null);
                    e.getInventory().setItem(i - 1, null);
                }
            }
            case 45 -> {
                double price = -1.0;
                if (addshoplist.get(e.getWhoClicked().getUniqueId()).items == null) {
                    HashMap<ItemStack, Double> values = new HashMap<>();
                    HashMap<ItemStack, ItemStack> items = new HashMap<>();
                    values.put(e.getInventory().getItem(47), price);
                    items.put(e.getInventory().getItem(47), e.getInventory().getItem(38));
                    addshoplist.get(e.getWhoClicked().getUniqueId()).items = items;
                    addshoplist.get(e.getWhoClicked().getUniqueId()).values = values;
                } else {
                    HashMap<ItemStack, Double> values = addshoplist.get(e.getWhoClicked().getUniqueId()).values;
                    HashMap<ItemStack, ItemStack> items = addshoplist.get(e.getWhoClicked().getUniqueId()).items;
                    values.put(e.getInventory().getItem(47), price);
                    items.put(e.getInventory().getItem(47), e.getInventory().getItem(38));
                    addshoplist.get(e.getWhoClicked().getUniqueId()).items = items;
                    addshoplist.get(e.getWhoClicked().getUniqueId()).values = values;
                }
                nowedit.put(e.getWhoClicked().getUniqueId(), true);
                if (editplayerlist.get(e.getWhoClicked().getUniqueId()).size() == 0) {
                    GUI.EditShopIcon((Player) e.getWhoClicked());
                } else {
                    int next = editplayerlist.get(e.getWhoClicked().getUniqueId()).get(0);
                    GUI.AssignmentShop(next, editplayer.get(e.getWhoClicked().getUniqueId()).get(next), (Player) e.getWhoClicked());
                    editplayerlist.get(e.getWhoClicked().getUniqueId()).remove(0);
                }
            }
            case 52 -> {
                if (e.getInventory().getItem(38) == null) return;
                StringBuilder number = new StringBuilder();
                for (int i = 0; i < 9; i++) {
                    if (e.getInventory().getItem(i) == null) continue;
                    if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "0", 48))) number.append("0");
                    else if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "1", 49))) number.append("1");
                    else if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "2", 50))) number.append("2");
                    else if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "3", 51))) number.append("3");
                    else if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "4", 52))) number.append("4");
                    else if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "5", 53))) number.append("5");
                    else if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "6", 54))) number.append("6");
                    else if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "7", 55))) number.append("7");
                    else if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "8", 56))) number.append("8");
                    else if (e.getInventory().getItem(i).equals(GetItem(Material.QUARTZ, 1, "9", 57))) number.append("9");
                }
                double price = Double.parseDouble(number.toString());
                if (addshoplist.get(e.getWhoClicked().getUniqueId()).items == null) {
                    HashMap<ItemStack, Double> values = new HashMap<>();
                    HashMap<ItemStack, ItemStack> items = new HashMap<>();
                    values.put(e.getInventory().getItem(47), price);
                    items.put(e.getInventory().getItem(47), e.getInventory().getItem(38));
                    addshoplist.get(e.getWhoClicked().getUniqueId()).items = items;
                    addshoplist.get(e.getWhoClicked().getUniqueId()).values = values;
                } else {
                    HashMap<ItemStack, Double> values = addshoplist.get(e.getWhoClicked().getUniqueId()).values;
                    HashMap<ItemStack, ItemStack> items = addshoplist.get(e.getWhoClicked().getUniqueId()).items;
                    values.put(e.getInventory().getItem(47), price);
                    items.put(e.getInventory().getItem(47), e.getInventory().getItem(38));
                    addshoplist.get(e.getWhoClicked().getUniqueId()).items = items;
                    addshoplist.get(e.getWhoClicked().getUniqueId()).values = values;
                }
                nowedit.put(e.getWhoClicked().getUniqueId(), true);
                if (editplayerlist.get(e.getWhoClicked().getUniqueId()).size() == 0) {
                    GUI.EditShopIcon((Player) e.getWhoClicked());
                } else {
                    int next = editplayerlist.get(e.getWhoClicked().getUniqueId()).get(0);
                    GUI.AssignmentShop(next, editplayer.get(e.getWhoClicked().getUniqueId()).get(next), (Player) e.getWhoClicked());
                    editplayerlist.get(e.getWhoClicked().getUniqueId()).remove(0);
                }
            }
            case 38 -> {
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void AssignmentShopClose(InventoryCloseEvent e){
        if (!(e.getView().title().equals(text("[MBR] AssignmentShop")) || e.getView().title().equals(text("[MBR] EditIcon")))) return;
        if (nowedit.get(e.getPlayer().getUniqueId()) == null || nowedit.get(e.getPlayer().getUniqueId()) == false){
            editplayer.remove(e.getPlayer().getUniqueId());
            editinv.remove(e.getPlayer().getUniqueId());
            nowedit.remove(e.getPlayer().getUniqueId());
        }
        else nowedit.put(e.getPlayer().getUniqueId(), false);
    }

    @EventHandler
    public void EditIconClick(InventoryClickEvent e){
        if (!e.getView().title().equals(text("[MBR] EditIcon"))) return;
        if (e.getClickedInventory() == null || e.getClickedInventory().getSize() == 41) return;
        if (e.getRawSlot() == 4) return;
        if (e.getRawSlot() != 8){
            e.setCancelled(true);
            return;
        }
        if (e.getInventory().getItem(4) == null) return;
        Data.ShopData add = new Data.ShopData(editinv.get(e.getWhoClicked().getUniqueId()), addshoplist.get(e.getWhoClicked().getUniqueId()).items, addshoplist.get(e.getWhoClicked().getUniqueId()).values, e.getInventory().getItem(4));
        shops.put(addshoplist.get(e.getWhoClicked().getUniqueId()).name, add);
        e.getWhoClicked().closeInventory();
        editplayer.remove(e.getWhoClicked().getUniqueId());
        editinv.remove(e.getWhoClicked().getUniqueId());
        Config.SaveShops(addshoplist.get(e.getWhoClicked().getUniqueId()).name, add);
        addshoplist.remove(e.getWhoClicked().getUniqueId());
        e.getWhoClicked().sendMessage("§e§l[MBR] §r追加しました");
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent e){
        if (!gamestatus.equals(Data.Status.fight) && !gamestatus.equals(Data.Status.ready)) return;
        if (!players.containsKey(e.getPlayer().getUniqueId())) return;
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setContents(players.get(e.getPlayer().getUniqueId()).inv.getContents());
        e.getPlayer().getInventory().setArmorContents(players.get(e.getPlayer().getUniqueId()).inv.getArmorContents());
        e.getPlayer().updateInventory();
        if (players.get(e.getPlayer().getUniqueId()).goldheld > 0){
            if (players.get(e.getPlayer().getUniqueId()).team.equals(Data.Team.blue)) yellownexus += players.get(e.getPlayer().getUniqueId()).goldheld * nexusdamage;
            else if (players.get(e.getPlayer().getUniqueId()).team.equals(Data.Team.yellow)) bluenexus += players.get(e.getPlayer().getUniqueId()).goldheld * nexusdamage;
            players.get(e.getPlayer().getUniqueId()).goldheld = (double) 0;
            for (UUID p : players.keySet()) {
                if (Bukkit.getPlayer(p) == null) continue;
                Bukkit.getPlayer(p).sendMessage("§e§l[MBR] §r"+e.getPlayer().getName()+"の持っていたお金が金庫に戻った");
            }
        }
        if (players.get(e.getPlayer().getUniqueId()).team.equals(Data.Team.yellow)) e.getPlayer().setBedSpawnLocation(yspawn);
        else e.getPlayer().setBedSpawnLocation(bspawn);
    }

    @EventHandler
    public void SetNexus(BlockPlaceEvent e){
        if (editnexus.isEmpty()) return;
        if (!editnexus.containsKey(e.getPlayer().getUniqueId())) return;
        if (!e.canBuild()) return;
        if (editnexus.get(e.getPlayer().getUniqueId()).equals(Data.Team.blue)) bnexus.add(e.getBlockReplacedState().getLocation());
        else ynexus.add(e.getBlockReplacedState().getLocation());
        e.getPlayer().sendMessage("§e§l[MBR] §r登録しました");
    }

    @EventHandler
    public void BreakNexus(BlockBreakEvent e){
        if (!ynexus.contains(e.getBlock().getLocation()) && !bnexus.contains(e.getBlock().getLocation())) return;
        if (e.getPlayer().hasPermission("mbr.start") && !gamestatus.equals(Data.Status.fight)) {
            e.getPlayer().sendMessage("§e§l[MBR] §rネクサスを破壊しました");
            if (ynexus.contains(e.getBlock().getLocation())) ynexus.remove(e.getBlock().getLocation());
            else bnexus.remove(e.getBlock().getLocation());
            mbr.getConfig().set("bnexus", bnexus);
            mbr.getConfig().set("ynexus", ynexus);
            mbr.saveConfig();
            return;
        }
        e.setCancelled(true);
        if (!gamestatus.equals(Data.Status.fight) || !players.containsKey(e.getPlayer().getUniqueId())) return;
        if (players.get(e.getPlayer().getUniqueId()).team.equals(Data.Team.blue) && ynexus.contains(e.getBlock().getLocation())){
            players.get(e.getPlayer().getUniqueId()).goldheld += nexusdamage;
            yellownexus -= nexusdamage;
            e.getPlayer().sendMessage("§e§l[MBR] §rお金を盗みました。拠点に戻って保存しましょう。");
            for (UUID p : players.keySet()) if (players.get(p).team.equals(Data.Team.yellow)) {
                if (Bukkit.getPlayer(p) == null) continue;
                Bukkit.getPlayer(p).sendMessage("§e§l[MBR] §r金庫が攻撃されています！");
            }
        }
        else if (players.get(e.getPlayer().getUniqueId()).team.equals(Data.Team.yellow) && bnexus.contains(e.getBlock().getLocation())){
            players.get(e.getPlayer().getUniqueId()).goldheld += nexusdamage;
            bluenexus -= nexusdamage;
            e.getPlayer().sendMessage("§e§l[MBR] §rお金を盗みました。拠点に戻って保存しましょう。");
            for (UUID p : players.keySet()) {
                if (Bukkit.getPlayer(p) == null) continue;
                if (players.get(p).team.equals(Data.Team.yellow)) Bukkit.getPlayer(p).sendMessage("§e§l[MBR] §r金庫が攻撃されています！");
            }
        }
    }

    @EventHandler
    public void GetHeld(PlayerInteractEvent e){
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !players.containsKey(e.getPlayer().getUniqueId())) return;
        if (players.get(e.getPlayer().getUniqueId()).team.equals(Data.Team.yellow) && ynexus.contains(e.getClickedBlock().getState().getLocation()) && players.get(e.getPlayer().getUniqueId()).goldheld != 0) {
            players.get(e.getPlayer().getUniqueId()).attack = players.get(e.getPlayer().getUniqueId()).goldheld;
            e.getPlayer().sendMessage("§e§l[MBR] §r盗んだお金($"+ players.get(e.getPlayer().getUniqueId()).goldheld +")をしまいました");
            e.getPlayer().sendMessage("§e§l[MBR] §rあなたが盗んだお金の総額：" + players.get(e.getPlayer().getUniqueId()).attack);
            for (UUID p : players.keySet()) {
                if (Bukkit.getPlayer(p) == null) continue;
                Bukkit.getPlayer(p).sendMessage("§e§l[MBR] §r§e§l"+e.getPlayer().getName()+"§rが$"+players.get(e.getPlayer().getUniqueId()).goldheld+"持ち帰りました！");
            }
            yellownexus += players.get(e.getPlayer().getUniqueId()).goldheld;
            players.get(e.getPlayer().getUniqueId()).goldheld = (double) 0;
            if (bluenexus <= 0){
                for (UUID p: players.keySet()){
                    if (!players.get(p).team.equals(Data.Team.yellow)) continue;
                    if (players.get(p).goldheld >= 0) return;
                }
                gamestatus = Data.Status.end;
                Game.GameEnd();
            }
        }
        else if (players.get(e.getPlayer().getUniqueId()).team.equals(Data.Team.blue) && bnexus.contains(e.getClickedBlock().getState().getLocation()) && players.get(e.getPlayer().getUniqueId()).goldheld != 0) {
            players.get(e.getPlayer().getUniqueId()).attack = players.get(e.getPlayer().getUniqueId()).goldheld;
            e.getPlayer().sendMessage("§e§l[MBR] §r盗んだお金($"+ players.get(e.getPlayer().getUniqueId()).goldheld +")をしまいました");
            e.getPlayer().sendMessage("§e§l[MBR] §rあなたが盗んだお金の総額：" + players.get(e.getPlayer().getUniqueId()).attack);
            for (UUID p : players.keySet()) if (Bukkit.getPlayer(p) != null) Bukkit.getPlayer(p).sendMessage("§e§l[MBR] §r§9§l"+e.getPlayer().getName()+"§rが$"+players.get(e.getPlayer().getUniqueId()).goldheld+"持ち帰りました！");
            bluenexus += players.get(e.getPlayer().getUniqueId()).goldheld;
            players.get(e.getPlayer().getUniqueId()).goldheld = (double) 0;
            if (yellownexus <= 0){
                for (UUID p: players.keySet()){
                    if (Bukkit.getPlayer(p) == null) continue;
                    if (!players.get(p).team.equals(Data.Team.blue)) continue;
                    if (players.get(p).goldheld >= 0) return;
                }
                gamestatus = Data.Status.end;
                Game.GameEnd();
            }
        }
    }

    @EventHandler
    public void OnDamage(EntityDamageByEntityEvent e){
        if (!e.getEntityType().equals(EntityType.PLAYER) || !e.getEntity().getWorld().equals(world) || (gamestatus.equals(Data.Status.fight) && players.containsKey(e.getEntity().getUniqueId()))) return;
        e.setCancelled(true);
    }
}
