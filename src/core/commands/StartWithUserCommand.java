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
        if (gameServer.sessionServer().hasSessionWithPlayer(user)){
            return new CommandResult("You already have session", null);
        }
        // Проверка что пользователь ожидает в очереди
        if(!gameServer.playerQueue().hasUser(user)){
            gameServer.playerQueue().enqueue(gameServer.userDataBase().getUserElseNull(user.getChatID()));
        }
        try{
            var users = gameServer.playerQueue().dequeuePair();
            gameServer.sessionServer().createSession(users.getFirst(), users.getSecond());
            users.getFirst().setRole(UserGameRole.RIDDLER);
            users.getSecond().setRole(UserGameRole.GUESSER);
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
