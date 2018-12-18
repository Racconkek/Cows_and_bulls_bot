package core.commands;

import com.google.inject.Inject;
import core.IGameServer;
import core.player.IPlayer;
import core.primitives.CommandResult;
import exceptions.SessionServerException;

public class StartWithRiddlerBotCommand implements ICommand {

    private final IGameServer gameServer;

    @Inject
    public StartWithRiddlerBotCommand(IGameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public CommandResult execute(IPlayer user) {
         // Проверка на то что пользователь ожидает в очереди
        if(gameServer.playerQueue().hasUser(user)){
            return new CommandResult("You waiting for other user", null);
        }
        try{
            gameServer.sessionServer().createSessionWithRiddlerBot(user);
            return new CommandResult("You started session with bot. Please make your guess", null);
        }
        catch (SessionServerException e){
            return new CommandResult(e.getMessage(), null);
        }
    }

    @Override
    public String getName() {
        return "/startrb";
    }
}
