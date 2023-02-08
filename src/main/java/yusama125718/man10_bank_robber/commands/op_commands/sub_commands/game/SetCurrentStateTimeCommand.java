package yusama125718.man10_bank_robber.commands.op_commands.sub_commands.game;


import com.shojabon.mcutils.Utils.BaseUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.RobberGame;

public class SetCurrentStateTimeCommand implements CommandExecutor {
    Man10BankRobber plugin;

    public SetCurrentStateTimeCommand(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        RobberGame game = Man10BankRobber.currentGame;
        if(game == null){
            sender.sendMessage(Man10BankRobber.prefix + "§c§l現在試合が行われていません");
            return true;
        }
        if(!BaseUtils.isInt(args[2])){
            sender.sendMessage(Man10BankRobber.prefix + "§c§l時間は数字でなくてはなりません");
            return true;
        }
        game.gameState.timerTillNextState.setRemainingTime(Integer.parseInt(args[2]));
        sender.sendMessage(Man10BankRobber.prefix + "§a§l時間を設定しました");
        return true;
    }
}
