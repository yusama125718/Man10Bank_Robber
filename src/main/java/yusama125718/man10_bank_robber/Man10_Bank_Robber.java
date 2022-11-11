package yusama125718.man10_bank_robber;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public final class Man10_Bank_Robber extends JavaPlugin {
    public static VaultAPI vaultapi;
    public static JavaPlugin mbr;
    public static Data.Status gamestatus = Data.Status.nogame;
    public static Boolean system;
    public static Boolean entrymode = true;     //trueなら通常、falseならopの手動
    public static Boolean pause = true;
    public static Boolean freeze = false;
    public static Integer maxplayer;
    public static Integer minplayer;
    public static Double entrytime;
    public static Double readytime;
    public static Double gametime;
    public static Double timer;
    public static Double nexus;
    public static Double nexusdamage;   //ネクサスへの攻撃１回あたりのリワード
    public static Double bluenexus;
    public static Double yellownexus;
    public static Double cost;
    public static Location bspawn;
    public static Location yspawn;
    public static Data.BuyMenuData buymenu;
    public static List<UUID> dissableplayers = new ArrayList<>();
    public static HashMap<String, Data.ShopData> shops = new HashMap<>();
    public static HashMap<Player, Data.PlayerData> players = new HashMap<>();
    public static HashMap<Player, Data.PlayerData> entryplayer = new HashMap<>();

    Consumer<BukkitTask> ontimer = bukkitTask -> {
        if (pause || !system) return;
        timer--;
        if (gamestatus.equals(Data.Status.entry) && entrymode){
            if (timer >= 0){
                Game.EndWaiting();
            } else {
                BossBar.UpdateInfo("§e§l[MBR] §r§lエントリー受付中 Time："+timer, entrytime/timer);
            }
        }
        else if (gamestatus.equals(Data.Status.ready)){
            if (timer >= 0){
                Game.Fight();
            } else {
                BossBar.UpdateInfo("§e§l[MBR] §r§l準備フェーズ Time："+timer, readytime/timer);
            }
        }
        else if (gamestatus.equals(Data.Status.fight)){
            if (timer >= 0){
                gamestatus = Data.Status.end;
                Game.GameEnd();
            } else {
                BossBar.UpdateInfo("§e§l[MBR] §r§l試合中！ Time："+timer, readytime/timer);
                BossBar.UpdateBlue("§e§l[MBR] §r§9§lBlueチーム §r§l金庫の残高："+bluenexus*nexusdamage, nexus/bluenexus);
                BossBar.UpdateYellow("§e§l[MBR] §r§e§lYellowチーム §r§l金庫の残高："+yellownexus*nexusdamage, nexus/yellownexus);
            }
        }
    };

    public void onEnable() {
        getCommand("mbr").setExecutor(new Command());
        new Event(this);
        vaultapi = new VaultAPI();
        mbr = this;
        Config.LoadFile();
        Config.LoadBuyMenu();
        mbr.saveDefaultConfig();
        system = mbr.getConfig().getBoolean("system");
        maxplayer = mbr.getConfig().getInt("maxplayer");
        minplayer = mbr.getConfig().getInt("minplayer");
        cost = mbr.getConfig().getDouble("cost");
        entrytime = mbr.getConfig().getDouble("entrytime");
        readytime = mbr.getConfig().getDouble("readytime");
        gametime = mbr.getConfig().getDouble("gametime");
        nexus = mbr.getConfig().getDouble("nexus");
        nexusdamage = mbr.getConfig().getDouble("nexusdamage");
        buymenu = Config.LoadBuyMenu();
        if (mbr.getConfig().get("bspawn") != null) bspawn = mbr.getConfig().getLocation("bspawn");
        if (mbr.getConfig().get("yspawn") != null) yspawn = mbr.getConfig().getLocation("yspawn");
        Bukkit.getScheduler().runTaskTimer(this, ontimer, 20, 0);
        new Thread(() -> {
            MySQLManager mysql = new MySQLManager(mbr, "mbr");
            mysql.execute("create table if not exists player_log(id int auto_increment,time timestamp,gameid int,name varchar(16),uuid varchar(36),result varchar(4),kill int,death int,bet bigint,return bigint,primary key(id))");
            mysql.execute("create table if not exists player_data(id int auto_increment,time timestamp,name varchar(16),uuid varchar(36),kill int,death int,win int,lose int,profit bigint,primary key(id))");
            mysql.execute("create table if not exists match_log(id int auto_increment,time timestamp,start datetime,totalbet bigint,primary key(id))");
        });
    }
}
