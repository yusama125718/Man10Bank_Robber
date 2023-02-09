package yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.team;

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

public class SetTeamRespawnLocationCommand implements CommandExecutor {
    Man10BankRobber plugin;

    public SetTeamRespawnLocationCommand(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String filePath = plugin.getDataFolder() + File.separator + "games" + File.separator + args[2] + ".yml";
        YamlConfiguration config = SConfigFile.getConfigFile(filePath);
        if(config == null){
            Man10BankRobber.senderWarn(sender, "ゲームが存在しません");
            return false;
        }
        config.set("teams." + args[4] + ".respawnLocation", ((Player) sender).getLocation());
        try {
            config.save(new File(filePath));
        } catch (IOException e) {
            Man10BankRobber.senderWarn(sender, "保存時にエラーが発生しました");
        }
        Man10BankRobber.senderWarn(sender, "ロケーションを保存しました");
        return true;
    }

}
