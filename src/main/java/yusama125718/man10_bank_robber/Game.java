package yusama125718.man10_bank_robber;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.util.*;

import static yusama125718.man10_bank_robber.Man10_Bank_Robber.*;

public class Game {
    private static Double blueinit;
    private static Double yellowinit;

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
                if (number.size() == 1){
                    if (team) lottery.put(number.get(0), Data.Team.blue);
                    else lottery.put(number.get(0), Data.Team.yellow);
                    number.remove(0);
                } else {
                    Random random = new Random();
                    int n = random.nextInt(number.size() - 1);
                    if (team) lottery.put(number.get(n), Data.Team.blue);
                    else lottery.put(number.get(n), Data.Team.yellow);
                    number.remove(n);
                }
                team = !team;
            }
        } else {
            while (number.size() > 0){
                if (number.size() == 1){
                    if (team) lottery.put(number.get(0), Data.Team.blue);
                    else lottery.put(number.get(0), Data.Team.yellow);
                    number.remove(0);
                } else {
                    Random random = new Random();
                    int n = random.nextInt(number.size() - 1);
                    if (team) lottery.put(number.get(n), Data.Team.blue);
                    else lottery.put(number.get(n), Data.Team.yellow);
                    number.remove(n);
                }
                team = !team;
            }
        }
        int i = 0;
        for (UUID p : entryplayer.keySet()){
            if (Bukkit.getPlayer(p) == null) continue;
            if (lottery.containsKey(i)){
                entryplayer.get(p).team = lottery.get(i);
                players.put(p, entryplayer.get(p));
                if (dissableplayers.contains(p)) {
                    dissableplayers.remove(p);
                    BossBar.ShowBar(Bukkit.getPlayer(p));
                    Bukkit.getPlayer(p).sendMessage("§e§l[MBR] §r表示します");
                }
            } else {
                if (Bukkit.getPlayer(p) == null) continue;
                vaultapi.deposit(p, entryplayer.get(p).bet + cost);
                Bukkit.getPlayer(p).sendMessage("§e§l[MBR] §rあなたはプレイヤーに選ばれなかったのでベットしたお金を返金しました");
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
        for (UUID p :players.keySet()){
            if (Bukkit.getPlayer(p) == null) continue;
            if (!players.get(p).team.equals(Data.Team.blue)) continue;
            Bukkit.getPlayer(p).closeInventory();
            players.get(p).saveinv = Bukkit.getPlayer(p).getInventory();
            Bukkit.getPlayer(p).getInventory().clear();
            Bukkit.getPlayer(p).teleport(bspawn);
            Function.Broadcast("§9§l" + Bukkit.getPlayer(p).getName());
        }
        Function.Broadcast("§e§l[MBR] §r§e§l来チーム");
        for (UUID p :players.keySet()){
            if (Bukkit.getPlayer(p) == null) continue;
            if (!players.get(p).team.equals(Data.Team.yellow)) continue;
            Bukkit.getPlayer(p).closeInventory();
            players.get(p).saveinv = Bukkit.getPlayer(p).getInventory();
            Bukkit.getPlayer(p).getInventory().clear();
            Bukkit.getPlayer(p).teleport(yspawn);
            Function.Broadcast("§e§l" + Bukkit.getPlayer(p).getName());
        }
        Function.Broadcast("§e§l[MBR] §r§lプレイヤーは準備を始めてください");
        Function.Broadcast("§e§l[MBR] §r§l"+ readytime +"秒後に開始します");
        Bukkit.getScheduler().runTaskLater(mbr, () -> {
            for (UUID p :players.keySet()){
                if (Bukkit.getPlayer(p) == null) continue;
                GUI.BuyMenu(Bukkit.getPlayer(p));
            }
        },20);
    }

    public static void Fight(){
        BossBar.ShowNexus();
        for (UUID p : players.keySet()){
            if (Bukkit.getPlayer(p) == null) continue;
            if (players.get(p).team.equals(Data.Team.blue)) bluenexus += players.get(p).bet;
            else yellownexus += players.get(p).bet;
        }
        blueinit = bluenexus;
        yellowinit = yellownexus;
        freeze = false;
        timer = gametime;
        gamestatus = Data.Status.fight;
        for (UUID p : players.keySet()){
            if (Bukkit.getPlayer(p) == null) continue;
            Bukkit.getPlayer(p).closeInventory();
            players.get(p).inv = Bukkit.getPlayer(p).getInventory();
        }
    }

    public static void GameEnd(){
        switch (gamestatus){
            case entry:
                for (UUID p: entryplayer.keySet()) vaultapi.deposit(p,entryplayer.get(p).bet + cost);
                entryplayer.clear();
                Function.Broadcast("§e§l[MBR] §rゲームが強制終了しました。");
                Function.Broadcast("§e§l[MBR] §rベットしたお金は返金されます");
                break;

            case ready:
            case fight:
                for (UUID p: entryplayer.keySet()) vaultapi.deposit(p,entryplayer.get(p).bet + cost);
                for (UUID p :players.keySet()){
                    if (Bukkit.getPlayer(p) == null) continue;
                    Bukkit.getPlayer(p).getInventory().clear();
                    Bukkit.getPlayer(p).getInventory().setContents(players.get(p).saveinv.getContents());
                    Bukkit.getPlayer(p).getInventory().setArmorContents(players.get(p).saveinv.getArmorContents());
                    Bukkit.getPlayer(p).updateInventory();
                }
                Function.Broadcast("§e§l[MBR] §rゲームが強制終了しました。");
                Function.Broadcast("§e§l[MBR] §rベットしたお金は返金されます");
                break;

            case end:
                for (UUID p :players.keySet()){
                    if (players.get(p).goldheld > 0){
                        if (players.get(p).team.equals(Data.Team.yellow)) bluenexus += players.get(p).goldheld * nexusdamage;
                        else yellownexus += players.get(p).goldheld * nexusdamage;
                    }
                    Bukkit.getPlayer(p).getInventory().clear();
                    Bukkit.getPlayer(p).getInventory().setContents(players.get(p).saveinv.getContents());
                    Bukkit.getPlayer(p).getInventory().setArmorContents(players.get(p).saveinv.getArmorContents());
                    Bukkit.getPlayer(p).updateInventory();
                }
                Function.Broadcast("§e§l[MBR] §r青チームの獲得金額：" + (yellowinit - yellownexus));
                Function.Broadcast("§e§l[MBR] §r黄チームの獲得金額：" + (blueinit - bluenexus));
                if ((bluenexus - blueinit) > (yellownexus - yellowinit)){
                    for (UUID p : players.keySet()) {
                        if (players.get(p).team.equals(Data.Team.blue)){
                            vaultapi.deposit(Bukkit.getPlayer(p).getUniqueId(), (bluenexus - blueinit) / 5 + players.get(p).bet);
                        } else {
                            vaultapi.deposit(Bukkit.getPlayer(p).getUniqueId(), yellownexus / 5);
                        }
                    }
                    Function.Broadcast("§e§l[MBR] §r青チームの勝利！");
                    Function.Broadcast("§e§l[MBR] §r勝者にお金が分配されます");
                } else if ((blueinit - bluenexus) < (yellowinit - yellownexus)){
                    for (UUID p : players.keySet()) {
                        if (players.get(p).team.equals(Data.Team.yellow)){
                            vaultapi.deposit(Bukkit.getPlayer(p).getUniqueId(), (yellownexus - yellowinit) / 5 + players.get(p).bet);
                        } else {
                            vaultapi.deposit(Bukkit.getPlayer(p).getUniqueId(), bluenexus / 5);
                        }
                    }
                    Function.Broadcast("§e§l[MBR] §r黄チームの勝利！");
                    Function.Broadcast("§e§l[MBR] §r勝者にお金が分配されます");
                } else {
                    Function.Broadcast("§e§l[MBR] §r引き分け！");
                    Function.Broadcast("§e§l[MBR] §rベットしたお金は返金されます");
                    for (UUID p: entryplayer.keySet()) vaultapi.deposit(p,entryplayer.get(p).bet + cost);
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
