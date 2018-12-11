package core.commands;

import com.google.inject.Inject;
import core.IGameServer;
import core.player.IPlayer;
import core.primitives.CommandResult;
import exceptions.SessionServerException;

public class StartWithGuesserBotCommand implements ICommand {

    private final IGameServer gameServer;

    @Inject
    public StartWithGuesserBotCommand(IGameServer gameServer) {
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
            gameServer.sessionServer().createSessionWithGuesserBot(user);
            //var firstGuess = gameServer.sessionServer().getSessionWithPlayer(user).getOther(user).getAnswer();
            return new CommandResult("You started session with guesser bot.\n First guess: 1234",
                    null, false);
        }
        catch (SessionServerException e){
            return new CommandResult(e.getMessage(), null, false);
        }
    }

    @Override
    public String getName() {
        return "/startgb";
    }
}
