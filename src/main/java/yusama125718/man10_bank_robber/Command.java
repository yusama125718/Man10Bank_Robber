package yusama125718.man10_bank_robber;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static yusama125718.man10_bank_robber.Man10_Bank_Robber.*;

public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("mbr.p")) return true;
        switch (args.length){
            case 0:
                if (!system){
                    sender.sendMessage("§e§l[MBR] §rシステムはOFFです");
                    return true;
                }
                GUI.MainMenu((Player) sender);
                return true;

            case 1:
                switch (args[0]) {
                    case "help":
                        if (sender.hasPermission("mbr.op")) {
                            sender.sendMessage("============ ゲーム進行 ============");
                            sender.sendMessage("§e§l[MBR] §r/mbr start ゲームを開始します");
                            sender.sendMessage("§e§l[MBR] §r/mbr stop ゲームを強制終了します");
                            sender.sendMessage("§e§l[MBR] §r/mbr b/y [MCID] プレイヤーをチームに入れます");
                            sender.sendMessage("§e§l[MBR] §r/mbr apply エントリーを終了します(addモードの時のみ)");
                            sender.sendMessage("============ ゲーム設定 ============");
                            sender.sendMessage("§e§l[MBR] §r/mbr on/off on/offにします");
                            sender.sendMessage("§e§l[MBR] §r/mbr entrymode join/add エントリー方法を変更します");
                            sender.sendMessage("§e§l[MBR] §r/mbr cost [額] エントリー額を変更します");
                            sender.sendMessage("§e§l[MBR] §r/mbr entrytime [時間(秒)] エントリー時間を変更します");
                            sender.sendMessage("§e§l[MBR] §r/mbr readytime [時間(秒)] 準備時間を変更します");
                            sender.sendMessage("§e§l[MBR] §r/mbr gametime [時間(秒)] 試合時間を変更します");
                            sender.sendMessage("§e§l[MBR] §r/mbr minplayer [人数] 最小人数を変更します");
                            sender.sendMessage("§e§l[MBR] §r/mbr maxplayer [人数] 最大人数を変更します");
                            sender.sendMessage("§e§l[MBR] §r/mbr setbspawn 現在地をblueチームのスポーン地点にします");
                            sender.sendMessage("§e§l[MBR] §r/mbr setyspawn 現在地をyellowチームのスポーン地点にします");
                            sender.sendMessage("§e§l[MBR] §r/mbr nexusdamage [金額] 金庫の攻撃１回あたりの金額を設定します");
                            sender.sendMessage("§e§l[MBR] §r/mbr editbuymenu [Inventorysize] shopを作成します");
                            sender.sendMessage("§e§l[MBR] §r/mbr createshop [Inventorysize] [shop名] shopを作成します");
                            sender.sendMessage("§e§l[MBR] §r/mbr [b/y] setnexus nexusをセットします");
                            sender.sendMessage("§e§l[MBR] §r/mbr world 試合のワールドを設定します");
                            sender.sendMessage("============ 一般 ============");
                        } else if (sender.hasPermission("mbr.start")) {
                            sender.sendMessage("§e§l[MBR] §r/mbr start ゲームを開始します");
                        }
                        sender.sendMessage("§e§l[MBR] §r/mbr メインメニューを出します");
                        sender.sendMessage("§e§l[MBR] §r/mbr hide 非表示にします");
                        sender.sendMessage("§e§l[MBR] §r/mbr show 表示します");
                        sender.sendMessage("§e§l[MBR] §r/mbr entry [bet額] エントリーします");
                        return true;

                    case "on":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbrでメニューを表示");
                            return true;
                        }
                        if (system) {
                            sender.sendMessage("§e§l[MBR] §rシステムはONです");
                            return true;
                        }
                        system = true;
                        mbr.getConfig().set("system", system);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §rONにしました");
                        return true;

                    case "off":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbrでメニューを表示");
                            return true;
                        }
                        if (!system) {
                            sender.sendMessage("§e§l[MBR] §rシステムはOFFです");
                            return true;
                        }
                        system = false;
                        mbr.getConfig().set("system", system);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §rOFFにしました");
                        return true;

                    case "start":
                        if (!sender.hasPermission("mbr.start")){
                            sender.sendMessage("§e§l[MBR] §r/mbrでメニューを表示");
                            return true;
                        }
                        if (!system) {
                            sender.sendMessage("§e§l[MBR] §rシステムはOFFです");
                            return true;
                        }
                        if (!gamestatus.equals(Data.Status.nogame)){
                            sender.sendMessage("§e§l[MBR] §rゲームが進行中です");
                            return true;
                        }
                        if (bspawn == null || yspawn == null){
                            sender.sendMessage("§e§l[MBR] §rスポーン地点がセットされていません");
                            return true;
                        }
                        if (entrymode) Game.Waiting();
                        else Game.OpWaiting();
                        BossBar.ShowInfo();
                        return true;

                    case "stop":
                        if (!sender.hasPermission("mbr.start")){
                            sender.sendMessage("§e§l[MBR] §r/mbrでメニューを表示");
                            return true;
                        }
                        if (gamestatus.equals(Data.Status.nogame)){
                            sender.sendMessage("§e§l[MBR] §rゲームが開始していません");
                            return true;
                        }
                        Game.GameEnd();

                    case "apply":
                        if (!sender.hasPermission("mbr.start")){
                            sender.sendMessage("§e§l[MBR] §r/mbrでメニューを表示");
                            return true;
                        }
                        if (!system) {
                            sender.sendMessage("§e§l[MBR] §rシステムはOFFです");
                            return true;
                        }
                        if (!entrymode){
                            sender.sendMessage("§e§l[MBR] §raddモードではありません");
                            return true;
                        }
                        if (!gamestatus.equals(Data.Status.entry)){
                            sender.sendMessage("§e§l[MBR] §rエントリー中ではありません");
                            return true;
                        }
                        for (Player p: entryplayer.keySet()){
                            if (entryplayer.get(p).bet == null){
                                Bukkit.broadcast(Component.text("§e§l[MBR] §r" + p.getName() + "がベットしなかったのでエントリーをキャンセルしました"));
                                entryplayer.remove(p);
                            }
                        }
                        if (entryplayer.size() < minplayer){
                            sender.sendMessage("§e§l[MBR] §r人数が不足しているため開始できません");
                            return true;
                        }
                        players.clear();
                        players = entryplayer;
                        entryplayer.clear();
                        new Thread(Game::Ready).start();

                    case "show":
                        if (dissableplayers.contains(((Player) sender).getUniqueId())) {
                            dissableplayers.remove(((Player) sender).getUniqueId());
                            BossBar.ShowBar((Player) sender);
                            sender.sendMessage("§e§l[MBR] §r表示します");
                        }
                        else sender.sendMessage("§e§l[MBR] §rすでに表示しています");
                        return true;

                    case "hide":
                        if (!dissableplayers.contains(((Player) sender).getUniqueId())) {
                            dissableplayers.add(((Player) sender).getUniqueId());
                            BossBar.HideBar((Player) sender);
                            sender.sendMessage("§e§l[MBR] §r非表示にします");
                        }
                        else sender.sendMessage("§e§l[MBR] §rすでに非表示です");
                        return true;

                    case "setbspawn":
                        if (!sender.hasPermission("mbr.start")){
                            sender.sendMessage("§e§l[MBR] §r/mbrでメニューを表示");
                            return true;
                        }
                        bspawn = ((Player) sender).getLocation();
                        mbr.getConfig().set("bspawn", bspawn);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §rセットしました");
                        return true;

                    case "setyspawn":
                        if (!sender.hasPermission("mbr.start")){
                            sender.sendMessage("§e§l[MBR] §r/mbrでメニューを表示");
                            return true;
                        }
                        yspawn = ((Player) sender).getLocation();
                        mbr.getConfig().set("yspawn", yspawn);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §rセットしました");
                        return true;

                    case "end":
                        if (!sender.hasPermission("mbr.start")){
                            sender.sendMessage("§e§l[MBR] §r/mbrでメニューを表示");
                            return true;
                        }
                        if (editnexus.isEmpty() || !editnexus.containsKey((Player) sender)) return true;
                        editnexus.remove((Player) sender);
                        mbr.getConfig().set("bnexus", bnexus);
                        mbr.getConfig().set("ynexus", ynexus);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §r登録モードを終了します");
                        return true;

                    case "world":
                        if (!sender.hasPermission("mbr.start")){
                            sender.sendMessage("§e§l[MBR] §r/mbrでメニューを表示");
                            return true;
                        }
                        world = ((Player) sender).getWorld();
                        mbr.getConfig().set("world", world.getName());
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §rワールドを"+ world.getName()+"に設定しました");
                        return true;
                }
                break;

            case 2:
                switch (args[0]){
                    case "entry":
                        if (!gamestatus.equals(Data.Status.entry) || !entrymode){
                            sender.sendMessage("§e§l[MBR] §r今はエントリーできません");
                            return true;
                        }
                        if (entryplayer.containsKey((Player) sender)){
                            sender.sendMessage("§e§l[MBR] §rすでにエントリーしています");
                            return true;
                        }
                        boolean isNumeric = args[1].matches("-?\\d+");
                        if (!isNumeric){
                            sender.sendMessage("§e§l[MBR] §r数字が無効です");
                            return true;
                        }
                        double money = Double.parseDouble(args[1]);
                        if (vaultapi.getBalance(((Player) sender).getUniqueId()) < money + cost){
                            sender.sendMessage("§e§l[MBR] §r所持金が不足しています");
                            return true;
                        }
                        vaultapi.withdraw(((Player) sender).getUniqueId(), money + cost);
                        entryplayer.put((Player) sender, new Data.PlayerData(money));
                        sender.sendMessage("§e§l[MBR] §rエントリーしました");
                        return true;

                    case "entrymode":
                        if (sender.hasPermission("mbr.op") && args[1].equals("join")){
                            if (!gamestatus.equals(Data.Status.nogame)){
                                sender.sendMessage("§e§l[MBR] §r現在設定は変更できません");
                                return true;
                            }
                            if (entrymode){
                                sender.sendMessage("§e§l[MBR] §rエントリーモードは現在joinです");
                                return true;
                            }
                            entrymode = true;
                            mbr.getConfig().set("entrymode", entrymode);
                            mbr.saveConfig();
                            sender.sendMessage("§e§l[MBR] §rエントリーモードをjoinにしました");
                            return true;
                        }
                        else if (sender.hasPermission("mbr.op") && args[1].equals("add")){
                            if (!entrymode){
                                sender.sendMessage("§e§l[MBR] §rエントリーモードは現在addです");
                                return true;
                            }
                            entrymode = false;
                            mbr.getConfig().set("entrymode", entrymode);
                            mbr.saveConfig();
                            sender.sendMessage("§e§l[MBR] §rエントリーモードをaddにしました");
                            return true;
                        }

                    case "b":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        if (args[1].equals("setnexus")){
                            editnexus.put((Player) sender, Data.Team.blue);
                            sender.sendMessage("§e§l[MBR] §rネクサスを設置してください");
                            sender.sendMessage("§e§l[MBR] §r設置したら/mbr endを実行してください");
                            return true;
                        }
                        else if (args[1].equals("deletenexus")){
                            bnexus.clear();
                            sender.sendMessage("§e§l[MBR] §rネクサスを削除しました");
                            return true;
                        }
                        else if (!gamestatus.equals(Data.Status.entry) || entrymode){
                            sender.sendMessage("§e§l[MBR] §r今はエントリーできません");
                            return true;
                        }
                        Player baddplayer = Bukkit.getPlayerExact(args[1]);
                        if (baddplayer == null)
                        {
                            sender.sendMessage("§e§l[MBR] §rそのプレイヤーは存在しません");
                            return true;
                        }
                        if (entryplayer.containsKey(baddplayer)){
                            sender.sendMessage("§e§l[MBR] §rそのプレイヤーはエントリーしています");
                            return true;
                        }
                        entryplayer.put(baddplayer, new Data.PlayerData(Data.Team.blue));
                        GUI.BetMenu(baddplayer);
                        sender.sendMessage("§e§l[MBR] §r"+args[1]+"にベットメニューを開きました");
                        return true;

                    case "y":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        if (args[1].equals("setnexus")){
                            editnexus.put((Player) sender, Data.Team.yellow);
                            sender.sendMessage("§e§l[MBR] §rネクサスを設置してください");
                            sender.sendMessage("§e§l[MBR] §r設置したら/mbr endを実行してください");
                            return true;
                        }
                        else if (args[1].equals("deletenexus")){
                            ynexus.clear();
                            sender.sendMessage("§e§l[MBR] §rネクサスを削除しました");
                            return true;
                        }
                        else if (!gamestatus.equals(Data.Status.entry) || entrymode){
                            sender.sendMessage("§e§l[MBR] §r今はエントリーできません");
                            return true;
                        }
                        Player yaddplayer = Bukkit.getPlayerExact(args[1]);
                        if (yaddplayer == null)
                        {
                            sender.sendMessage("§e§l[MBR] §rそのプレイヤーは存在しません");
                            return true;
                        }
                        if (entryplayer.containsKey(yaddplayer)){
                            sender.sendMessage("§e§l[MBR] §rそのプレイヤーはエントリーしています");
                            return true;
                        }
                        entryplayer.put(yaddplayer, new Data.PlayerData(Data.Team.yellow));
                        GUI.BetMenu(yaddplayer);
                        sender.sendMessage("§e§l[MBR] §r"+args[1]+"にベットメニューを開きました");
                        return true;

                    case "entrytime":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        boolean isNumeric1 = args[1].matches("-?\\d+");
                        if (!isNumeric1){
                            sender.sendMessage("§e§l[MBR] §r数字が無効です");
                            return true;
                        }
                        entrytime = Double.parseDouble(args[1]);
                        mbr.getConfig().set("entrytime", entrytime);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §r設定しました");
                        return true;

                    case "readytime":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        boolean isNumeric5 = args[1].matches("-?\\d+");
                        if (!isNumeric5){
                            sender.sendMessage("§e§l[MBR] §r数字が無効です");
                            return true;
                        }
                        readytime = Double.parseDouble(args[1]);
                        mbr.getConfig().set("readytime", readytime);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §r設定しました");
                        return true;

                    case "gametime":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        boolean isNumeric6 = args[1].matches("-?\\d+");
                        if (!isNumeric6){
                            sender.sendMessage("§e§l[MBR] §r数字が無効です");
                            return true;
                        }
                        gametime = Double.parseDouble(args[1]);
                        mbr.getConfig().set("gametime", gametime);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §r設定しました");
                        return true;

                    case "minplayer":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        boolean isNumeric2 = args[1].matches("-?\\d+");
                        if (!isNumeric2){
                            sender.sendMessage("§e§l[MBR] §r数字が無効です");
                            return true;
                        }
                        minplayer = Integer.parseInt(args[1]);
                        mbr.getConfig().set("minplayer", minplayer);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §r設定しました");
                        return true;

                    case "maxplayer":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        boolean isNumeric3 = args[1].matches("-?\\d+");
                        if (!isNumeric3){
                            sender.sendMessage("§e§l[MBR] §r数字が無効です");
                            return true;
                        }
                        maxplayer = Integer.parseInt(args[1]);
                        mbr.getConfig().set("maxplayer", maxplayer);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §r設定しました");
                        return true;

                    case "cost":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        boolean isNumeric4 = args[1].matches("-?\\d+");
                        if (!isNumeric4){
                            sender.sendMessage("§e§l[MBR] §r数字が無効です");
                            return true;
                        }
                        cost = Double.parseDouble(args[1]);
                        mbr.getConfig().set("cost", cost);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §r設定しました");
                        return true;

                    case "nexusdamage":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        boolean isNumeric8 = args[1].matches("-?\\d+");
                        if (!isNumeric8){
                            sender.sendMessage("§e§l[MBR] §r数字が無効です");
                            return true;
                        }
                        nexusdamage = Double.parseDouble(args[1]);
                        mbr.getConfig().set("nexusdamage", nexusdamage);
                        mbr.saveConfig();
                        sender.sendMessage("§e§l[MBR] §r設定しました");
                        return true;

                    case "editbuymenu":
                        if (!sender.hasPermission("mbr.op")){
                            sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
                            return true;
                        }
                        boolean isNumeric9 = args[1].matches("-?\\d+");
                        if (!isNumeric9){
                            sender.sendMessage("§e§l[MBR] §r数字が無効です");
                            return true;
                        }
                        int size = Integer.parseInt(args[1]);
                        if (size != 9 && size != 18 && size != 27 && size != 36 && size != 45){
                            sender.sendMessage("§e§l[MBR] §r数字は45以下の9の倍数にしてください");
                            return true;
                        }
                        GUI.EditBuyMenu((Player) sender, size);
                        return true;
                }
                break;

            case 3:
                if (sender.hasPermission("mbr.op") && args[0].equals("createshop")){
                    boolean isNumeric = args[1].matches("-?\\d+");
                    if (!isNumeric){
                        sender.sendMessage("§e§l[MBR] §r数字が無効です");
                        return true;
                    }
                    int size = Integer.parseInt(args[1]);
                    if (size != 9 && size != 18 && size != 27 && size != 36 && size != 45){
                        sender.sendMessage("§e§l[MBR] §r数字は45以下の9の倍数にしてください");
                        return true;
                    }
                    if (shops.containsKey(args[2])){
                        sender.sendMessage("§e§l[MBR] §rその名前のshopはすでに存在しています");
                        return true;
                    }
                    GUI.EditShopMenu((Player) sender, size, args[2]);
                }
        }
        sender.sendMessage("§e§l[MBR] §r/mbr help でhelpを表示");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
