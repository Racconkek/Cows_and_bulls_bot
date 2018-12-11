package core.commands;

import com.google.inject.Inject;
import core.IGameServer;
import core.player.GuesserBot;
import core.player.IPlayer;
import core.player.User;
import core.primitives.CommandResult;
import exceptions.SessionServerException;
import exceptions.UserDataBaseException;

public class EndSessionCommand implements ICommand {

    private final IGameServer gameServer;

    @Inject
    public EndSessionCommand(IGameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public CommandResult execute(IPlayer user) {
        var session = gameServer.sessionServer().getSessionWithPlayerElseNull(user);
        if (session == null){
            return new CommandResult("You don't have session", null, false);
        }
        try {
            var otherUser = session.getOther(user);
            if (otherUser instanceof User) {
                gameServer.userDataBase().delete(otherUser.getChatID());
            }
            gameServer.userDataBase().delete(user.getChatID());
            gameServer.sessionServer().endSession(session);
            return new CommandResult("Your session has ended", null, true);
        }
        catch (SessionServerException | UserDataBaseException e){
            return new CommandResult(e.getMessage(), null, false);
        }
    }

    @Override
    public String getName() {
        return "/ends";
    }
}
