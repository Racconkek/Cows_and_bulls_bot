package core.commands;

import com.google.inject.Inject;
import core.GameServer;
import core.player.IPlayer;
import core.primitives.CommandResult;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;

public class StartWithBotCommand implements ICommand {

    private final GameServer gameServer;

    @Inject
    public StartWithBotCommand(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public CommandResult execute(IPlayer user) {
        if(gameServer.sessionServer().hasSessionWithPlayer(user)){
            return new CommandResult("You already have session", null, true);
        }
        try{
            gameServer.sessionServer().createAISessionForPlayer(user);
            return new CommandResult("You started session with bot. Please make your guess",
                    null, false);
        }
        catch (SessionServerException e){
            return new CommandResult(e.getMessage(), null, false);
        }
    }

    @Override
    public String getName() {
        return "/startb";
    }
}
