package yusama125718.man10_bank_robber.data_class.states;

import com.shojabon.mcutils.Utils.BaseUtils;
import com.shojabon.mcutils.Utils.SScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.data_class.RobberGameStateData;
import yusama125718.man10_bank_robber.data_class.RobberPlayer;
import yusama125718.man10_bank_robber.data_class.RobberTeam;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

import java.util.*;

public class ReadyState extends RobberGameStateData {

    RobberGame game = Man10BankRobber.currentGame;
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Man10BankRobber");

    @Override
    public void start() {
        int requiredPlayerCount = game.minimumPlayersPerTeam * game.teams.size();
        if(game.preRegisteredPlayers.size() < requiredPlayerCount){
            Man10BankRobber.broadcastMessage(Man10BankRobber.getMessage("ready.notEnoughPlayers")
                    .replace("{players}", String.valueOf(requiredPlayerCount)));

            game.setGameState(RobberGameStateType.ENTRY);
            return;
        }
        //抽選チームわけ
        List<UUID> playerUUID = new ArrayList<>(game.preRegisteredPlayers.keySet());
        Collections.shuffle(playerUUID);

        int possiblePlayerPerTeam = playerUUID.size()/game.teams.size();
        if(possiblePlayerPerTeam > game.maximumPlayersPerTeam) possiblePlayerPerTeam = game.maximumPlayersPerTeam;
        List<String> teamNames = new ArrayList<>(game.teams.keySet());
        for(int i = 0; i < possiblePlayerPerTeam*game.teams.size(); i++){
            String assignedTeam = teamNames.get(i%teamNames.size());
            UUID uuid = playerUUID.get(i);
            RobberPlayer assigningPlayer = game.preRegisteredPlayers.get(uuid);
            game.preRegisteredPlayers.remove(uuid);
            RobberTeam team = game.getTeam(assignedTeam);
            assigningPlayer.team = team.teamName;
            game.players.put(uuid, assigningPlayer);
            if(assigningPlayer.getPlayer() != null) assigningPlayer.getPlayer().sendMessage(Man10BankRobber.getMessage("ready.team.picked").replace("{team}", team.alias));
        }

        //当選しなかったプレイヤーに返金
        for(UUID uuid : game.preRegisteredPlayers.keySet()){
            int value = game.preRegisteredPlayers.get(uuid).betPrice;
            game.unRegisterPlayer(uuid);
            Player p = Bukkit.getPlayer(uuid);
            if(p == null) continue;
            p.sendMessage(Man10BankRobber.getMessage("ready.team.unpicked").replace("{money}", BaseUtils.priceString(value)));
        }
        game.preRegisteredPlayers.clear();

        //準備エリアへ転送
        for(RobberPlayer player: game.players.values()){
            if(player.getPlayer() == null) continue;
            player.getPlayer().teleport(game.readyLocation);
        }
        Man10BankRobber.broadcastMessage(Man10BankRobber.getMessage("ready.message"));
        timerTillNextState.start();
    }


    @Override
    public void end() {
    }

    @Override
    public void defineTimer(){
        timerTillNextState.setRemainingTime(game.timeREADY);
        timerTillNextState.addOnEndEvent(() -> {
            game.setGameState(RobberGameStateType.IN_GAME);
        });
    }

    @Override
    public void defineBossBar() {
        String title = Man10BankRobber.messages.getString("ready.bar");
        this.bar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        timerTillNextState.linkBossBar(bar, true);
        timerTillNextState.addOnIntervalEvent(e -> bar.setTitle(title.replace("{time}", String.valueOf(e))));
    }

    @Override
    public void defineScoreboard() {
        scoreboard = new SScoreboard("TEST");
        scoreboard.setTitle("§4§lMan10GroundWars");
        scoreboard.setText(0, "§c§l選手準備中");
        timerTillNextState.addOnIntervalEvent(e -> {
            scoreboard.setText(2, "§a§l残り§e§l" + e + "§a§l秒");
            scoreboard.renderText();
        });
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(!game.players.containsKey(e.getPlayer().getUniqueId())) return;
        if(e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(!game.players.containsKey(e.getPlayer().getUniqueId())) return;
        if(e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(!game.players.containsKey(e.getWhoClicked().getUniqueId())) return;
        if(e.getWhoClicked().isOp()) return;
        if(e.getClickedInventory() != e.getWhoClicked().getInventory()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e){
        if(!game.players.containsKey(e.getPlayer().getUniqueId())) return;
        if(e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickupItem(PlayerAttemptPickupItemEvent e){
        if(!game.players.containsKey(e.getPlayer().getUniqueId())) return;
        if(e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }




}
