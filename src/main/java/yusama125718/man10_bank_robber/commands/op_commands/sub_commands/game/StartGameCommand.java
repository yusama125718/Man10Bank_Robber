package yusama125718.man10_bank_robber.commands.op_commands.sub_commands.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.Man10BankRobberAPI;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

import java.io.*;

public class StartGameCommand implements CommandExecutor {
    Man10BankRobber plugin;

    public StartGameCommand(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(Man10BankRobber.currentGame != null){
            Man10BankRobber.senderWarn(sender, "現在ゲームが進行中です");
            return false;
        }
        RobberGame game = Man10BankRobber.api.getRobberGame(args[2]);
        if(game == null){
            Man10BankRobber.senderWarn(sender, "ゲーム設定が存在しません");
            return false;
        }
        if(!game.canPlayGame()){
            Man10BankRobber.senderWarn(sender, "ゲームの設定にエラーがあります、詳細はコンソールを見てください");
            return false;
        }
        if(Man10BankRobber.currentGame != null){
            Man10BankRobber.senderWarn(sender, "現在ゲームが進行中です");
            return false;
        }
        Man10BankRobber.currentGame = game;
        Man10BankRobber.currentGame.setGameState(RobberGameStateType.ENTRY);
        // log game?
        return true;
    }

}
