package yusama125718.man10_bank_robber;

import com.shojabon.mcutils.Utils.SConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import yusama125718.man10_bank_robber.data_class.RobberGame;
import yusama125718.man10_bank_robber.data_class.RobberPlayer;
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
            RobberGame game = Man10BankRobber.currentGame;
            if(game == null) return;

            for(RobberPlayer player: game.players.values()){
                player.giveMoney(player.betPrice);
            }

            game.setGameState(RobberGameStateType.NO_GAME);
            Man10BankRobber.currentGame = null;
        });
    }
}
