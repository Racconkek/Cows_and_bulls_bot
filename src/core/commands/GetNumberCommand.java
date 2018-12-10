package core.commands;

import com.google.inject.Inject;
import core.GameServer;
import core.player.IPlayer;
import core.player.RiddlerBot;
import core.primitives.CommandResult;

public class GetNumberCommand implements ICommand {

    private final GameServer gameServer;

    @Inject
    public GetNumberCommand(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public CommandResult execute(IPlayer user) {
        if (gameServer.sessionServer().hasSessionWithPlayer(user) &&
                gameServer.sessionServer().getSessionWithPlayerElseNull(user).getOther(user) instanceof RiddlerBot)
        {
            return new CommandResult(user.getStringCowsAndBullsNumber(), null, true);
        }
        return new CommandResult("You can't use this command", null, false);
    }

    @Override
    public String getName() {
        return "/getnum";
    }
}
