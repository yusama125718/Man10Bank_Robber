package yusama125718.man10_bank_robber.data_class;

import org.bukkit.entity.Player;
import yusama125718.man10_bank_robber.Man10BankRobber;

import java.util.UUID;

public class RobberPlayer {
    private Player original;
    public RobberPlayer(Player original){
        this.original = original;
    }
    public int betPrice;

    public Player getPlayer(){
        return original;
    }

    public boolean takeMoney(int value){
        return Man10BankRobber.vault.withdraw(original.getUniqueId(), value);
    }

    public boolean giveMoney(int value){
        return Man10BankRobber.vault.deposit(original.getUniqueId(), value);
    }


}
