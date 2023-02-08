package yusama125718.man10_bank_robber.commands.normal_commands.sub_commands;

import ToolMenu.NumericInputMenu;
import com.shojabon.mcutils.Utils.BaseUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

import java.io.*;

public class RegisterCommand implements CommandExecutor {
    Man10BankRobber plugin;

    public RegisterCommand(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        RobberGame game = Man10BankRobber.currentGame;
        if(game == null){
            sender.sendMessage(Man10BankRobber.prefix + "§c§l現在ゲームが行われていません");
            return true;
        }
        Player p = (Player) sender;
        NumericInputMenu menu = new NumericInputMenu("ベット金額を入力してください", plugin);
        menu.setOnCancel(e -> menu.close(p));
        menu.setOnConfirm(balance -> {
            if(game.gameStateType != RobberGameStateType.ENTRY){
                sender.sendMessage(Man10BankRobber.prefix + "§c§l現在はエントリーできません");
                return;
            }
            if(balance < game.minimumBet){
                sender.sendMessage(Man10BankRobber.prefix + "§c§lベット最低金額は" + BaseUtils.priceString(game.minimumBet) + "円です");
                return;
            }
            if(game.maximumBet != 0 && balance > game.maximumBet){
                sender.sendMessage(Man10BankRobber.prefix + "§c§lベット最大金額は" + BaseUtils.priceString(game.maximumBet) + "円です");
                return;
            }
            if(balance == 0){
                sender.sendMessage(Man10BankRobber.prefix + "§c§lベットは" + BaseUtils.priceString(game.minimumBet) + "円以上でなくてはなりません");
                return;
            }
            if(Man10BankRobber.vault.getBalance(p.getUniqueId()) < balance){
                sender.sendMessage(Man10BankRobber.prefix + "§c§l現金が足りません");
                return;
            }
            if(game.preRegisteredPlayers.containsKey(p.getUniqueId())){
                game.unRegisterPlayer(p.getUniqueId());
            }
            if(game.registerPlayer(p, balance)){
                sender.sendMessage(Man10BankRobber.prefix + "§c§l登録されました");
            }
            menu.close(p);
        });

        menu.open(p);

        return true;
    }

}
