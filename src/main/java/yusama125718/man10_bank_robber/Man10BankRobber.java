package yusama125718.man10_bank_robber;

import com.shojabon.mcutils.Utils.SConfigFile;
import com.shojabon.mcutils.Utils.STimer;
import com.shojabon.mcutils.Utils.VaultAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import yusama125718.man10_bank_robber.commands.normal_commands.NormalCommands;
import yusama125718.man10_bank_robber.commands.op_commands.OpCommands;
import yusama125718.man10_bank_robber.data_class.RobberGame;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Man10BankRobber extends JavaPlugin {
    public static String prefix;
    public static FileConfiguration config;
    public static FileConfiguration messages;
    public static ExecutorService threadPool = Executors.newCachedThreadPool();
    public static VaultAPI vault;
    public static Man10BankRobberAPI api;
    public static Location lobbyLocation;
    public static RobberGame currentGame;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        SConfigFile.saveResource(this, "messages.yml", getDataFolder() + File.separator + "messages.yml");
        config = getConfig();
        prefix = getConfig().getString("prefix");
        messages = SConfigFile.getConfigFile(getDataFolder() + File.separator + "messages.yml");

        vault = new VaultAPI();
        lobbyLocation = config.getLocation("lobbyLocation");

        api = new Man10BankRobberAPI(this);

        NormalCommands normalCommands = new NormalCommands(this);
        getCommand("mbr").setExecutor(normalCommands);
        getCommand("mbr").setTabCompleter(normalCommands);

        OpCommands opCommands = new OpCommands(this);
        getCommand("mbrop").setExecutor(opCommands);
        getCommand("mbrop").setTabCompleter(opCommands);

        api.getRobberGame("test");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        STimer.pluginEnabled = false;
        api.endGame();
    }

    public static void logWarn(String message){
        Bukkit.getLogger().info(prefix + "§c§l" + message);
    }

    public static void senderWarn(CommandSender sender, String message){sender.sendMessage(prefix + "§c§l" + message);}
    public static void broadcastMessage(String message){
        if(message.equals("")) return;
        Bukkit.broadcast(Component.text(Man10BankRobber.prefix + message));
    }

    public static String getMessage(String key){
        if(!messages.contains(key)) return "";
        return messages.getString(key);
    }

}
