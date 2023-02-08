package yusama125718.man10_bank_robber.data_class;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import yusama125718.man10_bank_robber.Man10BankRobber;

import java.util.ArrayList;
import java.util.List;

public class RobberTeamData {

    public ConfigurationSection config;
    private RobberGame game;
    String teamName;

    // params
    String alias;
    String prefix;

    //ロケーション
    Location spawnPoint;

    // nexus
    List<Location> nexusBlocks = new ArrayList<>();
    public RobberTeamData(RobberGame game, String teamName, ConfigurationSection config){
        this.config = config;
        this.teamName = teamName;
        this.game = game;

        loadConfig();
    }

    private void loadConfig(){
        alias = this.config.getString("alias", teamName);
        prefix = this.config.getString("prefix", null);

        spawnPoint = this.config.getLocation("spawnPoint");
        nexusBlocks = (List<Location>) this.config.getList("nexusBlocks");
    }

    public boolean canPlayGame(){
        if(spawnPoint == null){
            Man10BankRobber.logWarn(teamName + "のスポーンがセットされていません");
            return false;
        }
        if(nexusBlocks.size() == 0){
            Man10BankRobber.logWarn(teamName + "のネクサスブロックがセットされていません");
            return false;
        }
        return true;
    }


}
