package yusama125718.man10_bank_robber.commands.op_commands;


import com.shojabon.mcutils.Utils.SCommandRouter.SCommandArgument;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandArgumentType;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandObject;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandRouter;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.CreateNewPresetCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.TestCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.SetLobbyLocation;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.SetReadyLocation;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.team.AddTeamNexusLocationCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.team.SetTeamRespawnLocationCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.team.SetTeamSpawnLocationCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.game.BuyCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.game.CancelGameCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.game.SetCurrentStateTimeCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.game.StartGameCommand;


public class OpCommands extends SCommandRouter {

    Man10BankRobber plugin;

    public OpCommands(Man10BankRobber plugin){
        this.plugin = plugin;
        registerCommands();
        registerEvents();
    }

    public void registerEvents(){
        setNoPermissionEvent(e -> e.sender.sendMessage(Man10BankRobber.prefix + "§c§lあなたは権限がありません"));
        setOnNoCommandFoundEvent(e -> e.sender.sendMessage(Man10BankRobber.prefix + "§c§lコマンドが存在しません"));
    }

    public void registerCommands(){
        //コンフィグ系コマンド
        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("config"))
                        .addArgument(new SCommandArgument().addAllowedString("create"))
                        .addArgument(new SCommandArgument().addAlias("ゲーム名"))
                        .addRequiredPermission("man10bankrobber.op.config.create")
                        .addExplanation("新たにプリセットを作成する").
                        setExecutor(new CreateNewPresetCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("config"))
                        .addArgument(new SCommandArgument().addAllowedString("set"))
                        .addArgument(new SCommandArgument().addAlias("ゲーム名"))
                        .addArgument(new SCommandArgument().addAlias("team"))
                        .addArgument(new SCommandArgument().addAlias("チーム内部名"))
                        .addArgument(new SCommandArgument().addAllowedString("spawnLocation"))
                        .addRequiredPermission("man10bankrobber.op.config.team.spawnLocation")
                        .addExplanation("チームのスポーンをセットする").
                        setExecutor(new SetTeamSpawnLocationCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("config"))
                        .addArgument(new SCommandArgument().addAllowedString("set"))
                        .addArgument(new SCommandArgument().addAlias("ゲーム名"))
                        .addArgument(new SCommandArgument().addAlias("team"))
                        .addArgument(new SCommandArgument().addAlias("チーム内部名"))
                        .addArgument(new SCommandArgument().addAllowedString("respawnLocation"))
                        .addRequiredPermission("man10bankrobber.op.config.team.respawnLocation")
                        .addExplanation("チームのリススポーンをセットする").
                        setExecutor(new SetTeamRespawnLocationCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("config"))
                        .addArgument(new SCommandArgument().addAllowedString("add"))
                        .addArgument(new SCommandArgument().addAlias("ゲーム名"))
                        .addArgument(new SCommandArgument().addAlias("team"))
                        .addArgument(new SCommandArgument().addAlias("チーム内部名"))
                        .addArgument(new SCommandArgument().addAllowedString("nexusBlocks"))
                        .addRequiredPermission("man10bankrobber.op.config.team.nexusBlocks")
                        .addExplanation("チームのネクサスブロック位置を追加する").
                        setExecutor(new AddTeamNexusLocationCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("config"))
                        .addArgument(new SCommandArgument().addAllowedString("set"))
                        .addArgument(new SCommandArgument().addAlias("ゲーム名"))
                        .addArgument(new SCommandArgument().addAllowedString("readyLocation"))
                        .addRequiredPermission("man10bankrobber.op.config.readyLocation")
                        .addExplanation("準備中の位置を追加する").
                        setExecutor(new SetReadyLocation(plugin))
        );
        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("config"))
                        .addArgument(new SCommandArgument().addAllowedString("set"))
                        .addArgument(new SCommandArgument().addAllowedString("lobbyLocation"))
                        .addRequiredPermission("man10bankrobber.op.config.lobbyLocation")
                        .addExplanation("ロビーの位置を設定する").
                        setExecutor(new SetLobbyLocation(plugin))
        );


        //ゲーム系コマンド
        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("game"))
                        .addArgument(new SCommandArgument().addAllowedString("start"))
                        .addArgument(new SCommandArgument().addAlias("ゲーム名"))
                        .addRequiredPermission("man10bankrobber.op.game.start")
                        .addExplanation("ゲームを開始する").
                        setExecutor(new StartGameCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("game"))
                        .addArgument(new SCommandArgument().addAllowedString("cancel"))
                        .addRequiredPermission("man10bankrobber.op.game.cancel")
                        .addExplanation("終了する")
                        .setExecutor(new CancelGameCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("game"))
                        .addArgument(new SCommandArgument().addAllowedString("setTime"))
                        .addArgument(new SCommandArgument().addAllowedType(SCommandArgumentType.INT).addAlias("時間"))
                        .addRequiredPermission("man10bankrobber.op.game.setTime")
                        .addExplanation("現在のステートの時間を設定する")
                        .setExecutor(new SetCurrentStateTimeCommand(plugin))
        );

        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("test"))
                        .addRequiredPermission("man10bankrobber.op.test")
                        .addExplanation("テストコマンド").
                        setExecutor(new TestCommand(plugin))
        );


        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("buy"))
                        .addArgument(new SCommandArgument().addAlias("プレイヤーUUID"))
                        .addArgument(new SCommandArgument().addAlias("アイテムID"))
                        .addArgument(new SCommandArgument().addAlias("値段"))
                        .addRequiredPermission("man10bankrobber.op.buy")
                        .addExplanation("アイテムを購入する").
                        setExecutor(new BuyCommand(plugin))
        );



    }

}
