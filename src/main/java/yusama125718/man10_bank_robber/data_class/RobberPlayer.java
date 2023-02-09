package yusama125718.man10_bank_robber.data_class;

import com.shojabon.mcutils.Utils.BaseUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import yusama125718.man10_bank_robber.Man10BankRobber;

import java.util.HashMap;
import java.util.UUID;

public class RobberPlayer {
    private UUID playerUUID;
    public RobberPlayer(UUID player){
        this.playerUUID = player;
    }
    public int betPrice;
    public String team;
    public long diedTime;
    public HashMap<String, Integer> carryingMoney = new HashMap<>();

    public Player getPlayer(){
        return Bukkit.getPlayer(playerUUID);
    }

    public RobberTeam getTeam(){
        return Man10BankRobber.currentGame.getTeam(team);
    }

    public void returnCarryingMoney(){
        for(String teamName: carryingMoney.keySet()){
            RobberTeam team = Man10BankRobber.currentGame.getTeam(teamName);
            int balance = carryingMoney.get(teamName);
            team.money += balance;

            Man10BankRobber.broadcastMessage(Man10BankRobber.getMessage("game.nexus.return")
                    .replace("{team}", team.alias)
                    .replace("{player}", getPlayer().getName())
                    .replace("{money}", BaseUtils.priceString(balance))
            );

            carryingMoney.remove(teamName);
        }
    }

    public void deliverCarryingMoney(){
        RobberGame game = Man10BankRobber.currentGame;
        for(String teamName: carryingMoney.keySet()){
            int balance = carryingMoney.get(teamName);
            game.getTeam(team).money += balance;
            carryingMoney.remove(teamName);
            RobberTeam enemyTeam = game.getTeam(teamName);
            Man10BankRobber.broadcastMessage(Man10BankRobber.getMessage("game.nexus.deliver")
                    .replace("{team.source}", game.getTeam(team).alias)
                    .replace("{team.target}", getTeam().alias)
                    .replace("{money}", BaseUtils.priceString(balance))
            );
            if(enemyTeam.money <= 0){
                game.makeTeamLose(enemyTeam.teamName);
            }

        }
        carryingMoney.clear();
    }

    public boolean takeMoney(int value){
        return Man10BankRobber.vault.withdraw(playerUUID, value);
    }

    public boolean giveMoney(int value){
        return Man10BankRobber.vault.deposit(playerUUID, value);
    }


}
