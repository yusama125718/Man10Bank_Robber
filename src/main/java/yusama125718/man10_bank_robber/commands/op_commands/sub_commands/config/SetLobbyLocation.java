package yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config;

import com.shojabon.mcutils.Utils.SConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import yusama125718.man10_bank_robber.Man10BankRobber;

import java.io.File;
import java.io.IOException;

public class SetLobbyLocation implements CommandExecutor {
    Man10BankRobber plugin;

    public SetLobbyLocation(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        plugin.getConfig().set("lobbyLocation", ((Player) sender).getLocation());
        plugin.saveConfig();
        Man10BankRobber.senderWarn(sender, "ロケーションを保存しました");
        return true;
    }

}
