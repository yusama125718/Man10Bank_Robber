package yusama125718.man10_bank_robber.data_class.states;

import com.shojabon.mcutils.Utils.SScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.data_class.RobberGameStateData;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

public class EntryState extends RobberGameStateData {

    RobberGame game = Man10BankRobber.currentGame;


    @Override
    public void start() {
        Man10BankRobber.broadcastMessage(Man10BankRobber.getMessage("entry.message"));
        timerTillNextState.start();
    }

    @Override
    public void end() {
    }

    @Override
    public void defineTimer(){
        timerTillNextState.setRemainingTime(game.timeENTRY);
        timerTillNextState.addOnEndEvent(() -> {
            game.setGameState(RobberGameStateType.READY);
        });
    }

    @Override
    public void defineBossBar() {
        String barTitle = Man10BankRobber.getMessage("entry.bar");
        this.bar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        timerTillNextState.linkBossBar(bar, true);
        timerTillNextState.addOnIntervalEvent(e -> bar.setTitle(barTitle.replace("{registered}", String.valueOf(game.preRegisteredPlayers.size())).replace("{time}", String.valueOf(e))));
    }

    @Override
    public void defineScoreboard() {
        scoreboard = new SScoreboard("TEST");
        scoreboard.setTitle("§4§lMan10GroundWars");

        scoreboard.setText(0, "§c§l選手登録中");

        timerTillNextState.addOnIntervalEvent(e -> {
            scoreboard.setText(3, "§a§l残り§e§l" + e + "§a§l秒");

            scoreboard.setText(2, "§a§l現在登録者§e§l" + game.preRegisteredPlayers.size() + "§a§l人");
            scoreboard.renderText();
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        game.unRegisterPlayer(e.getPlayer().getUniqueId());
    }
}
