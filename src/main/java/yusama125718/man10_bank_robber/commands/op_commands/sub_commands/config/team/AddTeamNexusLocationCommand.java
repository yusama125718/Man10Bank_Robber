package yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.team;

import com.shojabon.mcutils.Utils.SConfigFile;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import yusama125718.man10_bank_robber.Man10BankRobber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddTeamNexusLocationCommand implements CommandExecutor {
    Man10BankRobber plugin;

    public AddTeamNexusLocationCommand(Man10BankRobber plugin){
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
        List<Location> currentNexusLocations = (List<Location>) config.getList("teams." + args[4] + ".nexusBlocks");
        if(currentNexusLocations == null){
            currentNexusLocations = new ArrayList<>();
        }
        Block b = ((Player)sender).getTargetBlock(20);
        if(b == null){
            Man10BankRobber.senderWarn(sender, "ブロックを見てコマンドを実行してください");
            return false;
        }
        if(currentNexusLocations.contains(b.getLocation())){
            Man10BankRobber.senderWarn(sender, "ブロックがすでに存在します");
            return false;
        }
        currentNexusLocations.add(b.getLocation());
        config.set("teams." + args[4] + ".nexusBlocks", currentNexusLocations);
        try {
            config.save(new File(filePath));
        } catch (IOException e) {
            Man10BankRobber.senderWarn(sender, "保存時にエラーが発生しました");
        }
        Man10BankRobber.senderWarn(sender, "ロケーションを保存しました");
        return true;
    }

}
