package yusama125718.man10_bank_robber;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static yusama125718.man10_bank_robber.Man10_Bank_Robber.*;

public class Game {
    public static void Waiting(){
        timer = entrytime;
        gamestatus = Data.Status.entry;
        Function.Broadcast("§e§l[MBR] §r§lエントリーが開始されました。");
        Function.Broadcast("§e§l[MBR] §r§l/mbrからエントリーが可能です");
        pause = false;
    }

    public static void OpWaiting(){
        gamestatus = Data.Status.entry;
        Function.Broadcast("§e§l[MBR] §r§lエントリーが開始されました。");
        Function.Broadcast("§e§l[MBR] §r§lGMはエントリーを行ってください。");
        Bukkit.broadcast(Component.text("§e§l[MBR] §r/mbr b/y [MCID]でエントリーできます"), "mbr.op");
    }

    public static void EndWaiting(){
        if (entryplayer.size() < minplayer){
            Function.Broadcast("§e§l[MBR] §r人数が不足しているため終了しました");
            GameEnd();
            return;
        }
        players.clear();
        boolean team = true;
        HashMap<Integer,Data.Team> lottery = new HashMap<>();
        List<Integer> number = new ArrayList<>();
        for (int i = 0; i < entryplayer.size(); i++) number.add(i);
        if (entryplayer.size() > maxplayer){
            for (int i = 0; i < maxplayer; i++){
                Random random = new Random();
                int n = random.nextInt(number.size() - 1);
                if (team) lottery.put(number.get(n), Data.Team.blue);
                else lottery.put(number.get(n), Data.Team.yellow);
                number.remove(n);
                team = !team;
            }
        } else {
            while (number.size() != 0){
                Random random = new Random();
                int n = random.nextInt(number.size() - 1);
                if (team) lottery.put(number.get(n), Data.Team.blue);
                else lottery.put(number.get(n), Data.Team.yellow);
                number.remove(n);
                team = !team;
            }
        }
        int i = 0;
        for (Player p :entryplayer.keySet()){
            if (lottery.containsKey(i)){
                entryplayer.get(p).team = lottery.get(i);
                players.put(p, entryplayer.get(p));
                if (dissableplayers.contains(p.getUniqueId())) {
                    dissableplayers.remove(p.getUniqueId());
                    BossBar.ShowBar(p);
                    p.sendMessage("§e§l[MBR] §r表示します");
                }
            } else {
                vaultapi.deposit(p.getUniqueId(), entryplayer.get(p).bet + cost);
                p.sendMessage("§e§l[MBR] §rあなたはプレイヤーに選ばれなかったのでベットしたお金を返金しました");
            }
            entryplayer.remove(p);
            i++;
        }
        entryplayer.clear();
        Ready();
    }

    public static void Ready(){
        timer = readytime;
        gamestatus = Data.Status.ready;
        freeze = true;
        Function.Broadcast("§e§l[MBR] §r§lプレイヤーが決定しました");
        Function.Broadcast("§e§l[MBR] §r§9§l青チーム");
        for (Player p :players.keySet()){
            if (!players.get(p).team.equals(Data.Team.blue)) continue;
            p.closeInventory();
            players.get(p).saveinv = p.getInventory();
            p.getInventory().clear();
            p.teleport(bspawn);
            Function.Broadcast("§9§l" + p.getName());
        }
        Function.Broadcast("§e§l[MBR] §r§e§l来チーム");
        for (Player p :players.keySet()){
            if (!players.get(p).team.equals(Data.Team.yellow)) continue;
            p.closeInventory();
            players.get(p).saveinv = p.getInventory();
            p.getInventory().clear();
            p.teleport(yspawn);
            Function.Broadcast("§e§l" + p.getName());
        }
        Function.Broadcast("§e§l[MBR] §r§lプレイヤーは準備を始めてください");
        Function.Broadcast("§e§l[MBR] §r§l"+ readytime +"秒後に開始します");
        Bukkit.getScheduler().runTaskLater(mbr, () -> {
            for (Player p :players.keySet()){
                GUI.BuyMenu(p);
            }
        },20);
    }

    public static void Fight(){
        BossBar.ShowNexus();
        bluenexus = nexus;
        yellownexus = nexus;
        freeze = false;
        timer = gametime;
        gamestatus = Data.Status.fight;
        for (Player p : players.keySet()){
            p.closeInventory();
            players.get(p).inv = p.getInventory();
        }
    }

    public static void GameEnd(){
        switch (gamestatus){
            case entry:
                for (Player p: entryplayer.keySet()) vaultapi.deposit(p.getUniqueId(),entryplayer.get(p).bet + cost);
                entryplayer.clear();
                Function.Broadcast("§e§l[MBR] §rゲームが強制終了しました。");
                Function.Broadcast("§e§l[MBR] §rベットしたお金は返金されます");
                break;

            case ready:
            case fight:
                for (Player p: entryplayer.keySet()) vaultapi.deposit(p.getUniqueId(),entryplayer.get(p).bet + cost);
                for (Player p :players.keySet()){
                    p.getInventory().clear();
                    p.getInventory().setContents(players.get(p).saveinv.getContents());
                    p.getInventory().setArmorContents(players.get(p).saveinv.getArmorContents());
                    p.updateInventory();
                }
                Function.Broadcast("§e§l[MBR] §rゲームが強制終了しました。");
                Function.Broadcast("§e§l[MBR] §rベットしたお金は返金されます");
                break;

            case end:
                for (Player p :players.keySet()){
                    if (players.get(p).goldheld > 0){
                        if (players.get(p).team.equals(Data.Team.yellow)) bluenexus += players.get(p).goldheld * nexusdamage;
                        else yellownexus += players.get(p).goldheld * nexusdamage;
                    }
                    p.getInventory().clear();
                    p.getInventory().setContents(players.get(p).saveinv.getContents());
                    p.getInventory().setArmorContents(players.get(p).saveinv.getArmorContents());
                    p.updateInventory();
                }
                if (bluenexus > yellownexus){
                    for (Player p : players.keySet()){
                        if (players.get(p).team.equals(Data.Team.blue)){
                            vaultapi.deposit(p.getUniqueId(), bluenexus / 5);
                        } else {
                            vaultapi.deposit(p.getUniqueId(), yellownexus / 5);
                        }
                    }
                    Function.Broadcast("§e§l[MBR] §r青チームの勝利！");
                    Function.Broadcast("§e§l[MBR] §r勝者にお金が分配されます");
                } else if (bluenexus < yellownexus){
                    Function.Broadcast("§e§l[MBR] §r黄チームの勝利！");
                    Function.Broadcast("§e§l[MBR] §r勝者にお金が分配されます");
                } else {
                    Function.Broadcast("§e§l[MBR] §r引き分け！");
                    Function.Broadcast("§e§l[MBR] §rベットしたお金は返金されます");
                    for (Player p: entryplayer.keySet()) vaultapi.deposit(p.getUniqueId(),entryplayer.get(p).bet + cost);
                }
                break;
        }
        players.clear();
        freeze = false;
        pause = true;
        gamestatus = Data.Status.nogame;
        BossBar.ClearAll();
    }
}
