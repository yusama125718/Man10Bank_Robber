package yusama125718.man10_bank_robber;

import com.shojabon.mcutils.Utils.SConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;

import java.io.File;

public class Man10BankRobberAPI {
    Man10BankRobber plugin;
    public Man10BankRobberAPI(Man10BankRobber plugin){
        this.plugin = plugin;
    }

    public RobberGame getRobberGame(String configName){
        YamlConfiguration config = SConfigFile.getConfigFile(plugin.getDataFolder() + File.separator + "games" + File.separator + configName + ".yml");
        if(config == null) return null;
        return new RobberGame(plugin, configName, config);
    }

    public void endGame(){
        Bukkit.getScheduler().runTask(plugin, ()-> {
            if(Man10BankRobber.currentGame == null) return;

            Man10BankRobber.currentGame.setGameState(RobberGameStateType.NO_GAME);
            Man10BankRobber.currentGame = null;
        });
    }
}
