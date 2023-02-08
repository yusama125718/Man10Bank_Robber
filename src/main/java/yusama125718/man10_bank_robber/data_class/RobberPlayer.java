package yusama125718.man10_bank_robber.data_class;

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

    public boolean takeMoney(int value){
        return Man10BankRobber.vault.withdraw(playerUUID, value);
    }

    public boolean giveMoney(int value){
        return Man10BankRobber.vault.deposit(playerUUID, value);
    }


}
