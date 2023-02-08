package yusama125718.man10_bank_robber;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import static yusama125718.man10_bank_robber.Man10_Bank_Robber.dissableplayers;

public class BossBar {
    private static final org.bukkit.boss.BossBar info = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
    private static final org.bukkit.boss.BossBar b = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
    private static final org.bukkit.boss.BossBar y = Bukkit.createBossBar("", BarColor.YELLOW, BarStyle.SOLID);

    public static void ShowInfo(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!dissableplayers.contains(player.getUniqueId())) {
                info.addPlayer(player);
            }
        }
    }

    public static void ShowNexus(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!dissableplayers.contains(player.getUniqueId())) {
                b.addPlayer(player);
                y.addPlayer(player);
            }
        }
    }

    public static void ClearAll(){
        info.removeAll();
        b.removeAll();
        y.removeAll();
    }

    public static void HideBar(Player p){
        info.removePlayer(p);
        b.removePlayer(p);
        y.removePlayer(p);
    }

    public static void ShowBar(Player p){
        info.addPlayer(p);
        b.addPlayer(p);
        y.addPlayer(p);
    }

    public static void UpdateInfo(String title, Double progress){
        info.setTitle(title);
        info.setProgress(progress);
    }

    public static void UpdateBlue(String title, Double progress){
        b.setTitle(title);
        b.setProgress(progress);
    }

    public static void UpdateYellow(String title, Double progress){
        y.setTitle(title);
        y.setProgress(progress);
    }


}
