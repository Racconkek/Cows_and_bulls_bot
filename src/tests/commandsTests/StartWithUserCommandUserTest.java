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
    void executeCreateSession_shouldReturnRightValue(){
        var command = new StartWithUserCommand(gameServer);
        gameServer.playerQueue().enqueue(first);

        var expected = new CommandResult("You are riddler", "You are guesser");
        var actual = command.execute(second);

        assertEquals(expected, actual);
    }

    @Test
    void executeCantCreateSession_shouldReturnRightValue(){
        var command = new StartWithUserCommand(gameServer);
        gameServer.playerQueue().enqueue(first);

        var expected = new CommandResult("You are waiting for other user", null);
        var actual = command.execute(first);

        assertEquals(expected, actual);
    }

    @Test
    void executeNoUserToPlayWith_shouldReturnRightValue(){
        var command = new StartWithUserCommand(gameServer);

        var expected = new CommandResult("No user to play with.", null);
        var actual = command.execute(first);

        assertEquals(expected, actual);
    }

    @Test
    void executeUserHasSession_shouldReturnRightValue(){
        var command = new StartWithUserCommand(gameServer);

        try {
            gameServer.playerQueue().enqueue(first);
            gameServer.sessionServer().createSessionWithRiddlerBot(first);

            var expected = new CommandResult("User TestUser1 already have session", null);
            var actual = command.execute(second);

            assertEquals(expected, actual);
        }
        catch (SessionServerException e){
            assertEquals("User TestUser1 already have session", e.getMessage());
        }

    }

}
