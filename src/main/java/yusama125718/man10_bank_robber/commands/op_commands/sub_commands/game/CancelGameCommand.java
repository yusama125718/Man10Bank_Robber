package yusama125718.man10_bank_robber.commands.op_commands.sub_commands.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.data_class.RobberPlayer;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

public class CancelGameCommand implements CommandExecutor {
    Man10BankRobber plugin;

    public CancelGameCommand(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        RobberGame game = Man10BankRobber.currentGame;
        if(game == null){
            sender.sendMessage(Man10BankRobber.prefix + "§c§l現在ゲームが行われていません");
            return true;
        }
        for(RobberPlayer player: game.players.values()){
            player.giveMoney(player.betPrice);
        }
        Man10BankRobber.api.endGame();
        sender.sendMessage(Man10BankRobber.prefix + "§a§lゲームをキャンセルしました");
        return true;
    }

}
