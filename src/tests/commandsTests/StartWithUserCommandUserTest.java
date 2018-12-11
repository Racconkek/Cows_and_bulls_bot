package tests.commandsTests;

import com.google.inject.Guice;
import core.BasicModule;
import core.GameServer;
import core.IGameServer;
import core.commands.StartWithUserCommand;
import core.player.IPlayer;
import core.player.User;
import core.primitives.CommandResult;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartWithUserCommandUserTest {

    private IGameServer gameServer;
    private IPlayer first;
    private IPlayer second;

    @BeforeEach
    void setUp(){
        var injector = Guice.createInjector(new BasicModule());
        gameServer = injector.getInstance(GameServer.class);
        first = new User("TestUser1", "111", UserGameRole.WAITER);
        second = new User("TestUser2", "222", UserGameRole.WAITER);
    }


    @Test
    void getName_shouldReturnRightValue(){
        var command = new StartWithUserCommand(gameServer);
        var expected = "/startu";

        var actual = command.getName();
        assertEquals(expected, actual);
    }

    @Test
    void executeIfHasSession_shouldReturnRightValue(){
        var command = new StartWithUserCommand(gameServer);
        var expected = new CommandResult("You already have session", null);
        CommandResult actual = null;

        try {
            gameServer.sessionServer().createSessionWithRiddlerBot(first);
            actual = command.execute(first);
        }
        catch (SessionServerException e){
            assertEquals("User TestUser1 already have session", e.getMessage());
        }

        assertEquals(expected, actual);
    }

    @Test
    void executeCreateSession_shouldReturnRightValue(){
        var command = new StartWithUserCommand(gameServer);
        gameServer.playerQueue().enqueue(first);
        gameServer.playerQueue().enqueue(second);

        var expected = new CommandResult("You are riddler", "You are guesser");
        var actual = command.execute(first);

        assertEquals(expected, actual);
    }

    @Test
    void executeCantCreateSession_shouldReturnRightValue(){
        var command = new StartWithUserCommand(gameServer);
        gameServer.playerQueue().enqueue(first);

        var expected = new CommandResult("There is no user to play with. Please wait.", null);
        var actual = command.execute(first);

        assertEquals(expected, actual);
    }
}
