package yusama125718.man10_bank_robber.data_class.states;

import com.shojabon.mcutils.Utils.SScoreboard;
import it.unimi.dsi.fastutil.floats.Float2DoubleArrayMap;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.plugin.Plugin;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.data_class.RobberGameStateData;
import yusama125718.man10_bank_robber.data_class.RobberTeam;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class EndState extends RobberGameStateData {

    RobberGame game = Man10BankRobber.currentGame;
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Man10BankRobber");

    @Override
    public void start() {
        //優勝者処理
        HashMap<String, Integer> difference = new HashMap<>();
        for(RobberTeam team: game.teams.values()){
            team.cleanUpTeam();
            difference.put(team.teamName, team.calculateDifferenceFromStart());
        }

        ArrayList<Integer> differenceList = new ArrayList<>(difference.values());
        int biggestTeam = Collections.max(differenceList);

        for(RobberTeam team: game.teams.values()){
            if(team.calculateDifferenceFromStart() == biggestTeam){
                team.payBackAsWinner();
                Bukkit.broadcastMessage(team.alias + "チームの勝ち");
            }else{
                team.payBackAsLoser();
                Bukkit.broadcastMessage(team.alias + "チームの負け");
            }
        }

        timerTillNextState.start();
    }


    @Override
    public void end() {
    }

    @Override
    public void defineTimer(){
        timerTillNextState.setRemainingTime(20);
        timerTillNextState.addOnEndEvent(() -> {
            game.setGameState(RobberGameStateType.ENTRY);
        });
    }

    @Override
    public void defineBossBar() {
        String title = "§c§l終了処理中 §a§l残り§e§l{time}§a§l秒";
        this.bar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        timerTillNextState.linkBossBar(bar, true);
        timerTillNextState.addOnIntervalEvent(e -> bar.setTitle(title.replace("{time}", String.valueOf(e))));
    }

}
