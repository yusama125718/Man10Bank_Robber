package yusama125718.man10_bank_robber.data_class;

import com.shojabon.mcutils.Utils.BaseUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import yusama125718.man10_bank_robber.Man10BankRobber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RobberTeam {

    public ConfigurationSection config;
    private RobberGame game;
    public String teamName;

    public BossBar bar;

    // game params
    public int initialMoney = 0;
    public int money = 0;

    // params
    public String alias;
    public String prefix;
    public String barColor;

    //ロケーション
    Location spawnPoint;

    // nexus
    List<Location> nexusBlocks = new ArrayList<>();
    public RobberTeam(RobberGame game, String teamName, ConfigurationSection config){
        this.config = config;
        this.teamName = teamName;
        this.game = game;

        loadConfig();
    }

    public void initializeTeam(){
        initialMoney = calculateTotalBet();
        money = initialMoney;
        bar = Bukkit.createBossBar(alias, BarColor.valueOf(barColor), BarStyle.SOLID);
        for(Player p: Bukkit.getOnlinePlayers()){
            bar.addPlayer(p);
        }
    }

    public void updateTeamBar(){
        bar.setTitle(alias + "§f§l 現在:" + BaseUtils.priceString(money) + "円");
        double percentage = (double) money/initialMoney;
        if(percentage > 1) percentage = 1;
        bar.setProgress(percentage);
    }

    private void loadConfig(){
        alias = this.config.getString("alias", teamName);
        prefix = this.config.getString("prefix", null);
        barColor = this.config.getString("barColor", "WHITE");

        spawnPoint = this.config.getLocation("spawnPoint");
        nexusBlocks = (List<Location>) this.config.getList("nexusBlocks");
    }

    public boolean canPlayGame(){
        if(spawnPoint == null){
            Man10BankRobber.logWarn(teamName + "のスポーンがセットされていません");
            return false;
        }
        if(nexusBlocks == null || nexusBlocks.size() == 0){
            Man10BankRobber.logWarn(teamName + "のネクサスブロックがセットされていません");
            return false;
        }
        return true;
    }

    public void teleportAllPlayersToSpawn(){
        for(RobberPlayer player: game.getPlayersInTeam(teamName)){
            player.getPlayer().teleport(spawnPoint);
        }
    }

    public int calculateTotalBet(){
        int result = 0;
        for(RobberPlayer player: game.getPlayersInTeam(teamName)){
            result += player.betPrice;
        }
        return result;
    }


}
