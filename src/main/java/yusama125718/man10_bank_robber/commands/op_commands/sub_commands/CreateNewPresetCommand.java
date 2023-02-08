package yusama125718.man10_bank_robber.commands.op_commands.sub_commands;

import com.shojabon.mcutils.Utils.SConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import yusama125718.man10_bank_robber.Man10BankRobber;

import java.io.*;

public class CreateNewPresetCommand implements CommandExecutor {
    Man10BankRobber plugin;

    public CreateNewPresetCommand(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File file = new File(plugin.getDataFolder() + File.separator + "games" + File.separator + args[2] + ".yml");
        if(file.exists()){
            sender.sendMessage(Man10BankRobber.prefix + "§c§lプリセットが存在します");
            return false;
        }

        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                sender.sendMessage(Man10BankRobber.prefix + "§c§l games フォルダーの作成に失敗しました");
                return false;
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try{
            InputStream input = plugin.getResource("game/GameConfig.yml");
            OutputStream output = new FileOutputStream(file);
            int DEFAULT_BUFFER_SIZE = 1024 * 4;
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int size;
            while (-1 != (size = input.read(buffer))) {
                output.write(buffer, 0, size);
            }
            input.close();
            output.close();
        }catch (Exception e){
            sender.sendMessage(Man10BankRobber.prefix + "§c§l内部エラーが発生しました");
            return false;
        }


        return true;
    }

}
