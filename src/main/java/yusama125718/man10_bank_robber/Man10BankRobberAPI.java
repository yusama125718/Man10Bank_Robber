package yusama125718.man10_bank_robber;

import com.shojabon.mcutils.Utils.SConfigFile;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import yusama125718.man10_bank_robber.data_class.RobberGame;

import java.io.File;

public class Man10BankRobberAPI {
    Man10BankRobber plugin;
    public Man10BankRobberAPI(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    public RobberGame getRobberGame(String configName){
        YamlConfiguration config = SConfigFile.getConfigFile(plugin.getDataFolder() + File.separator + "games" + File.separator + configName + ".yml");
        if(config == null) return null;
        return new RobberGame(configName, config);
    }
}
