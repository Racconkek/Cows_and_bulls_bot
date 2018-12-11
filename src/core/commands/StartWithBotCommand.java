package core.commands;

import com.google.inject.Inject;
import core.IGameServer;
import core.player.IPlayer;
import core.primitives.CommandResult;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;

public class StartWithBotCommand implements ICommand {

    private final IGameServer gameServer;

    @Inject
    public StartWithBotCommand(IGameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public CommandResult execute(IPlayer user) {
        if(gameServer.sessionServer().hasSessionWithPlayer(user)){
            return new CommandResult("You already have session", null, true);
        }
        // Проверка на то что пользователь ожидает в очереди
        if(gameServer.playerQueue().hasUser(user)){
            return new CommandResult("You waiting for other user", null, true);
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
