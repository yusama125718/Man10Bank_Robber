package yusama125718.man10_bank_robber.commands.op_commands;


import com.shojabon.mcutils.Utils.SCommandRouter.SCommandArgument;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandObject;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandRouter;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.CreateNewPresetCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.TestCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.set.team.AddTeamNexusLocationCommand;
import yusama125718.man10_bank_robber.commands.op_commands.sub_commands.config.set.team.SetTeamSpawnLocationCommand;
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
                        .addArgument(new SCommandArgument().addAllowedString("spawnPoint"))
                        .addRequiredPermission("man10bankrobber.op.config.team.spawnPoint")
                        .addExplanation("チームのスポーンをセットする").
                        setExecutor(new SetTeamSpawnLocationCommand(plugin))
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
                        .addArgument(new SCommandArgument().addAllowedString("test"))
                        .addRequiredPermission("man10bankrobber.op.test")
                        .addExplanation("テストコマンド").
                        setExecutor(new TestCommand(plugin))
        );
    }

}
