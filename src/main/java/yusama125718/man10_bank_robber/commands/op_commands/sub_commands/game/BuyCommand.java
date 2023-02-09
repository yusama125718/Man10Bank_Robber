package yusama125718.man10_bank_robber.commands.op_commands.sub_commands.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.data_class.RobberPlayer;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

import java.util.UUID;

public class BuyCommand implements CommandExecutor {
    Man10BankRobber plugin;

    public BuyCommand(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        RobberGame game = Man10BankRobber.currentGame;
        if(game == null){
            sender.sendMessage(Man10BankRobber.prefix + "§c§l現在ゲームが行われていません");
            return true;
        }
        if(game.gameStateType != RobberGameStateType.READY){
            sender.sendMessage(Man10BankRobber.prefix + "§c§l現在購入することはできません");
            return true;
        }
        RobberPlayer player = game.getPlayer(UUID.fromString(args[1]));
        if(player == null){
            sender.sendMessage(Man10BankRobber.prefix + "§c§lプレイヤーが存在しません");
            return true;
        }
        player.shop.buyItem(args[2], Integer.parseInt(args[3]));
        return true;
    }

}
