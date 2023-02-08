package yusama125718.man10_bank_robber.data_class.states;

import com.shojabon.mcutils.Utils.BaseUtils;
import com.shojabon.mcutils.Utils.SScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.data_class.RobberGameStateData;
import yusama125718.man10_bank_robber.data_class.RobberPlayer;
import yusama125718.man10_bank_robber.data_class.RobberTeam;
import yusama125718.man10_bank_robber.enums.NexusMode;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

import java.util.*;

public class InGameState extends RobberGameStateData {

    RobberGame game = Man10BankRobber.currentGame;
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Man10BankRobber");

    @Override
    public void start() {
        //プレイヤーをスポーン位置にテレポート
        for(RobberTeam team: game.teams.values()){
            team.initializeTeam();
            team.teleportAllPlayersToSpawn();
        }
        timerTillNextState.start();
    }


    @Override
    public void end() {
        for(RobberTeam team: game.teams.values()){
            team.bar.setVisible(false);
            team.bar = null;
        }
    }

    @Override
    public void defineTimer(){
        timerTillNextState.setRemainingTime(game.timeIN_GAME);
        timerTillNextState.addOnEndEvent(() -> {
            game.setGameState(RobberGameStateType.END);
        });
    }

    @Override
    public void defineBossBar() {
        String title = "§c§l試合中 §a§l残り§e§l{time}§a§l秒";
        this.bar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        timerTillNextState.linkBossBar(bar, true);
        timerTillNextState.addOnIntervalEvent(e -> bar.setTitle(title.replace("{time}", String.valueOf(e))));
        timerTillNextState.addOnIntervalEvent(e -> {
            for(RobberTeam team : game.teams.values()){
                team.updateTeamBar();
            }
        });
    }

    @Override
    public void defineScoreboard() {
        scoreboard = new SScoreboard("TEST");
        scoreboard.setTitle("§4§lMan10GroundWars");
        scoreboard.setText(0, "§c§l試合中");
        timerTillNextState.addOnIntervalEvent(e -> {
            scoreboard.setText(2, "§a§l残り§e§l" + e + "§a§l秒");
            scoreboard.renderText();
        });
    }

    //ボスバー処理

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        for(RobberTeam team: game.teams.values()){
            team.bar.addPlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent e){
        for(RobberTeam team: game.teams.values()){
            team.bar.removePlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void onNexusBreak(BlockBreakEvent e){
        RobberTeam nexusTeam = game.getNexus(e.getBlock().getLocation());
        if(nexusTeam == null) return;
        e.setCancelled(true);
        RobberPlayer player = game.getPlayer(e.getPlayer().getUniqueId());
        if(player.team.equals(nexusTeam.teamName)) return;
        if(nexusTeam.money <= 0){
            e.getPlayer().sendMessage(Man10BankRobber.prefix + "§c§lこのチームの金庫は残金がありません");
            return;
        }
        //敵ネクサス破壊中

        int removeValue = 0;
        if(game.nexusMode == NexusMode.FIXED){
            removeValue += game.nexusModeValue;
        }else if(game.nexusMode == NexusMode.PERCENTAGE){
            removeValue += nexusTeam.initialMoney * (1/game.nexusModeValue);
        }

        if(removeValue > nexusTeam.money){
            removeValue = nexusTeam.money;
        }
        if(!player.carryingMoney.containsKey(nexusTeam.teamName)) player.carryingMoney.put(nexusTeam.teamName, 0);
        player.carryingMoney.put(nexusTeam.teamName, player.carryingMoney.get(nexusTeam.teamName) + removeValue);
        nexusTeam.money -= removeValue;

        e.getPlayer().sendMessage(Man10BankRobber.prefix + "§a§l" + BaseUtils.priceString(removeValue) + "円を奪った");
        // message?
    }

    @EventHandler
    public void onNexusReturn(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getClickedBlock() == null) return;
        RobberTeam nexusTeam = game.getNexus(e.getClickedBlock().getLocation());
        if(nexusTeam == null) return;
        e.setCancelled(true);
        RobberPlayer player = game.getPlayer(e.getPlayer().getUniqueId());
        if(!player.team.equals(nexusTeam.teamName)) return;
        for(String team: player.carryingMoney.keySet()){
            int balance = player.carryingMoney.get(team);
            nexusTeam.money += balance;
            e.getPlayer().sendMessage(Man10BankRobber.prefix + "§a§l" + BaseUtils.priceString(balance) + "円を届けた");
            player.carryingMoney.remove(team);
        }
        player.carryingMoney.clear();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        RobberPlayer player = game.getPlayer(e.getPlayer().getUniqueId());
        if(player == null) return;
        for(String teamName: player.carryingMoney.keySet()){
            RobberTeam team = game.getTeam(teamName);
            int balance = player.carryingMoney.get(teamName);
            team.money += balance;
            e.getPlayer().sendMessage(Man10BankRobber.prefix + "§c§l" + BaseUtils.priceString(balance) + "円が返金された");
            player.carryingMoney.remove(teamName);
        }
    }



}
