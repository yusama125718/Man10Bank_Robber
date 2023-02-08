package yusama125718.man10_bank_robber.commands.op_commands.sub_commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.RobberGame;

import java.io.*;

public class TestCommand implements CommandExecutor {
    Man10BankRobber plugin;

    public TestCommand(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        RobberGame game = Man10BankRobber.api.getRobberGame("test");
        return true;
    }

}
