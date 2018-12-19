package tools.handler;

import com.google.inject.Inject;
import core.IGameServer;
import core.player.GuesserBot;
import core.player.IPlayer;
import core.player.RiddlerBot;
import core.primitives.HandlerAnswer;
import tools.selector.ICommandSelector;

public class MessageHandler implements IHandler{

    private final IGameServer gameServer;
    private final ICommandSelector commandSelector;

    @Inject
    public MessageHandler(IGameServer gameServer, ICommandSelector commandSelector) {
        this.gameServer = gameServer;
        this.commandSelector = commandSelector;
    }

    @Override
    public HandlerAnswer handleInput(String message, IPlayer user) {
        var command = commandSelector.getCommand(message);
        if (command != null){
            var commandResult = command.execute(user);
            return new HandlerAnswer(commandResult.getFirstMessage(), commandResult.getSecondMessage(), false);
        }
        if (gameServer.playerQueue().hasUser(user)){
            return new HandlerAnswer("You are waiting other user", null,false);
        }

        var session = gameServer.sessionServer().getSessionWithPlayerElseNull(user);
        if (session == null){
            return new HandlerAnswer("You don't have session. Please start one.", null,false);
        }

        var otherPlayer = session.getOther(user);
        if (otherPlayer instanceof RiddlerBot){
            return ((RiddlerBot)otherPlayer).getAnswer(message, user);
        }
        if (otherPlayer instanceof GuesserBot){
            return ((GuesserBot)otherPlayer).getAnswer(message, user);
        }
        return new HandlerAnswer(null, message, false);
    }
}
