package core.commands;

import com.google.inject.Inject;
import core.GameServer;
import core.player.IPlayer;
import core.primitives.CommandResult;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;
import exceptions.UserQueueException;

public class StartWithUserCommand implements ICommand {

    private final GameServer gameServer;

    @Inject
    public StartWithUserCommand(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public CommandResult execute(IPlayer user) {

        if (gameServer.sessionServer().hasSessionWithPlayer(user)){
            return new CommandResult("You already have session", null, true);
        }
        try{
            var users = gameServer.playerQueue().dequeuePair();
            gameServer.sessionServer().createSession(users.getFirst(), users.getSecond());
            users.getFirst().setRole(UserGameRole.RIDDLER);
            users.getSecond().setRole(UserGameRole.GUESSER);
            return new CommandResult("You are riddler", "You are guesser", false);
        }
        catch (UserQueueException | SessionServerException e){
           return new CommandResult(e.getMessage(), null, false);
        }
    }

    @Override
    public String getName() {
        return "/startu";
    }
}
