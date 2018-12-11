package tests.commandsTests;

import com.google.inject.Guice;
import core.BasicModule;
import core.GameServer;
import core.IGameServer;
import core.commands.StartWithGuesserBotCommand;
import core.player.IPlayer;
import core.player.User;
import core.primitives.CommandResult;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartWithGuesserBotCommandUnitTest {

    private IGameServer gameServer;
    private IPlayer first;

    @BeforeEach
    void setUp(){
        var injector = Guice.createInjector(new BasicModule());
        gameServer = injector.getInstance(GameServer.class);
        first = new User("TestUser1", "111", UserGameRole.WAITER);
    }


    @Test
    void getName_shouldReturnRightValue(){
        var command = new StartWithGuesserBotCommand(gameServer);
        var expected = "/startgb";

        var actual = command.getName();
        assertEquals(expected, actual);
    }

    @Test
    void executeIfHasSession_shouldReturnRightValue(){
        var command = new StartWithGuesserBotCommand(gameServer);
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
        var command = new StartWithGuesserBotCommand(gameServer);

        var expected = new CommandResult("You started session with guesser bot.\n First guess: 1234", null);
        var actual = command.execute(first);

        assertEquals(expected, actual);
        assertTrue(gameServer.sessionServer().hasSessionWithPlayer(first));
    }

    @Test
    void executeIfUserInQueue__shouldReturnRightValue(){
        var command = new StartWithGuesserBotCommand(gameServer);
        gameServer.playerQueue().enqueue(first);

        var expected = new CommandResult("You waiting for other user", null);
        var actual = command.execute(first);

        assertEquals(expected, actual);
    }
}
