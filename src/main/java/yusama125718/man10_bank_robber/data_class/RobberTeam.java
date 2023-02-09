package yusama125718.man10_bank_robber.data_class;

import com.shojabon.mcutils.Utils.BaseUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

import java.util.ArrayList;
import java.util.List;

public class RobberTeam {

    public ConfigurationSection config;
    private RobberGame game;
    public String teamName;

    public BossBar bar;

    // game params
    public int initialMoney = 0;
    public int money = 0;
    public boolean finished = false;
    // params
    public String alias;
    public String prefix;
    public String barColor;

    //ロケーション
    public Location spawnPoint;
    public Location respawnLocation;

    // nexus
    List<Location> nexusBlocks = new ArrayList<>();
    public RobberTeam(RobberGame game, String teamName, ConfigurationSection config){
        this.config = config;
        this.teamName = teamName;
        this.game = game;

        loadConfig();
    }
    //コンフィグ
    private void loadConfig(){
        alias = this.config.getString("alias", teamName);
        prefix = this.config.getString("prefix", null);
        barColor = this.config.getString("barColor", "WHITE");

        spawnPoint = this.config.getLocation("spawnLocation");
        respawnLocation = this.config.getLocation("respawnLocation");
        nexusBlocks = (List<Location>) this.config.getList("nexusBlocks");
    }

    public boolean canPlayGame(){
        if(spawnPoint == null){
            Man10BankRobber.logWarn(teamName + "のスポーンがセットされていません");
            return false;
        }
        if(respawnLocation == null){
            Man10BankRobber.logWarn(teamName + "のリスポーンが設定されていません");
            return false;
        }
        if(nexusBlocks == null || nexusBlocks.size() == 0){
            Man10BankRobber.logWarn(teamName + "のネクサスブロックがセットされていません");
            return false;
        }
        return true;
    }

    public void initializeTeam(){
        initialMoney = calculateTotalBet();
        money = initialMoney;
        bar = Bukkit.createBossBar(alias, BarColor.valueOf(barColor), BarStyle.SOLID);
        for(Player p: Bukkit.getOnlinePlayers()){
            bar.addPlayer(p);
            p.setBedSpawnLocation(spawnPoint, true);
        }
    }

    public void cleanUpTeam(){
        for(RobberPlayer player : game.getPlayersInTeam(teamName)){
            player.returnCarryingMoney();
            player.getPlayer().setBedSpawnLocation(Man10BankRobber.lobbyLocation, true);
            player.getPlayer().setHealth(0);
        }
        finished = true;
    }

    //バー
    public void updateTeamBar(){
        bar.setTitle(alias + "§f§l 現在:" + BaseUtils.priceString(money) + "円");
        double percentage = (double) money/initialMoney;
        if(percentage > 1) percentage = 1;
        bar.setProgress(percentage);
    }

    public void teleportAllPlayersToSpawn(){
        for(RobberPlayer player: game.getPlayersInTeam(teamName)){
            player.getPlayer().teleport(spawnPoint);
        }
    }
    //賞金計算
    public void payBackAsWinner(){
        List<RobberPlayer> players = game.getPlayersInTeam(teamName);
        int winSplit = (money - calculateTotalBet())/players.size();
        if(winSplit < 0) winSplit = 0;
        for(RobberPlayer player : players){
            int value = player.betPrice + winSplit;
            player.giveMoney(value);
            Man10BankRobber.broadcastMessage(Man10BankRobber.getMessage("end.winner.payment")
                    .replace("{player}", player.getPlayer().getName())
                    .replace("{money}", BaseUtils.priceString(value)));
        }
    }

    public void payBackAsLoser(){
        List<RobberPlayer> players = game.getPlayersInTeam(teamName);
        for(RobberPlayer player : players){
            int value = (player.betPrice/initialMoney)*money;
            player.giveMoney(value);
            Man10BankRobber.broadcastMessage(Man10BankRobber.getMessage("end.loser.payment")
                    .replace("{player}", player.getPlayer().getName())
                    .replace("{money}", BaseUtils.priceString(value)));

        }
    }

    public int calculateTotalBet(){
        int result = 0;
        for(RobberPlayer player: game.getPlayersInTeam(teamName)){
            result += player.betPrice;
        }
        return result;
    }

    public int calculateDifferenceFromStart(){
        return money - initialMoney;
    }


}
