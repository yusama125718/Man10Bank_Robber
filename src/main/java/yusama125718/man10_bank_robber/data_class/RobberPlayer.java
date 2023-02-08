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
            getPlayer().sendMessage(Man10BankRobber.prefix + "§c§l" + BaseUtils.priceString(balance) + "円が返金された");
            carryingMoney.remove(teamName);
        }
    }

    public void deliverCarryingMoney(){
        RobberGame game = Man10BankRobber.currentGame;
        for(String teamName: carryingMoney.keySet()){
            int balance = carryingMoney.get(teamName);
            game.getTeam(team).money += balance;
            game.getTeam(team).stolenMoney += balance;
            getPlayer().sendMessage(Man10BankRobber.prefix + "§a§l" + BaseUtils.priceString(balance) + "円を届けた");
            carryingMoney.remove(teamName);
            RobberTeam enemyTeam = game.getTeam(teamName);
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
