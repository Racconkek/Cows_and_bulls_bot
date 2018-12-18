package core.commands;

import com.google.inject.Inject;
import core.IGameServer;
import core.player.IPlayer;
import core.primitives.CommandResult;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;
import exceptions.UserQueueException;

public class StartWithUserCommand implements ICommand {

    private final IGameServer gameServer;

    @Inject
    public StartWithUserCommand(IGameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public CommandResult execute(IPlayer user) {
        // Проверка что пользователь ожидает в очереди
        if(gameServer.playerQueue().hasUser(user)){
            return new CommandResult("You are waiting for other user", null);
        }
        try{
            var secondUser = gameServer.playerQueue().dequeue();
            gameServer.sessionServer().createSession(user, secondUser);
            user.setRole(UserGameRole.RIDDLER);
            secondUser.setRole(UserGameRole.GUESSER);
            return new CommandResult("You are riddler", "You are guesser");
        }
        catch (UserQueueException | SessionServerException e){
           return new CommandResult(e.getMessage(), null);
        }
    }

    @Override
    public String getName() {
        return "/startu";
    }
}
