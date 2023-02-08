package yusama125718.man10_bank_robber.data_class;

import com.shojabon.mcutils.Utils.SConfigFile;
import org.apache.commons.lang.enums.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.enums.NexusMode;

import java.util.HashMap;

public class RobberGame {

    String gameName;
    public FileConfiguration config;
    HashMap<String, RobberTeamData> teams = new HashMap<>();

    // === configuration ====
    //ロケーション
    Location readyLocation;
    //ベット金額
    int minimumBet = 10000;
    int maximumBet = 0;
    //プレイヤー人数
    int minimumPlayersPerTeam = 3;
    int maximumPlayersPerTeam = 5;
    //ネクサス金額モード
    NexusMode nexusMode = NexusMode.FIXED;
    int nexusModeValue = 1000;
    public RobberGame(String gameName, FileConfiguration config){
        this.gameName = gameName;
        this.config = config;

        loadConfig();
    }

    private void loadConfig(){
        readyLocation = this.config.getLocation("locations.ready");

        minimumBet = this.config.getInt("bet.minimumBet", minimumBet);
        maximumBet = this.config.getInt("bet.maximumBet", maximumBet);

        minimumPlayersPerTeam = this.config.getInt("players.minimumPlayersPerTeam", minimumPlayersPerTeam);
        maximumPlayersPerTeam = this.config.getInt("players.maximumPlayersPerTeam", maximumPlayersPerTeam);

        String nexusModeString = this.config.getString("nexus.mode");
        if(!NexusMode.isEnumString(nexusModeString)) nexusModeString = "FIXED";
        nexusMode = NexusMode.valueOf(nexusModeString);
        nexusModeValue = this.config.getInt("nexus.value", nexusModeValue);

        // load time
        // load team
        ConfigurationSection teamsData = this.config.getConfigurationSection("teams");
        if(teamsData != null){
            for(String teamName: teamsData.getKeys(false)){
                teams.put(teamName, new RobberTeamData(this, teamName, teamsData.getConfigurationSection(teamName)));
            }
        }
    }


    public boolean canPlayGame(){
        for(String team: teams.keySet()){
            if(!teams.get(team).canPlayGame()) return false;
        }
        if(maximumPlayersPerTeam < minimumPlayersPerTeam || maximumPlayersPerTeam <= 0 || minimumPlayersPerTeam < 0){
            Man10BankRobber.logWarn("最大最小プレイヤー人数が不正です");
            return false;
        }
        if(maximumBet < minimumBet || maximumBet <= 0 || minimumBet < 0){
            Man10BankRobber.logWarn("最大最小ベット金額が不正です");
            return false;
        }
        if(nexusMode == NexusMode.PERCENTAGE && (nexusModeValue > 100 || nexusModeValue <= 0)){
            Man10BankRobber.logWarn("ネクサスモードの値が不正です");
            return false;
        }
        if(nexusMode == NexusMode.FIXED && nexusModeValue <= 0){
            Man10BankRobber.logWarn("ネクサスモードの値が不正です");
            return false;
        }
        if(readyLocation == null){
            Man10BankRobber.logWarn("準備位置が不正です");
            return false;
        }

        return true;
    }





}
