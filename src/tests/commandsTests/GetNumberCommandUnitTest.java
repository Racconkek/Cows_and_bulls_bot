package tests.commandsTests;

import com.google.inject.Guice;
import core.BasicModule;
import core.GameServer;
import core.IGameServer;
import core.commands.GetNumberCommand;
import core.player.IPlayer;
import core.player.User;
import core.primitives.CommandResult;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetNumberCommandUnitTest {

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
        var command = new GetNumberCommand(gameServer);
        var expected = "/getnum";

        var actual = command.getName();
        assertEquals(expected, actual);
    }

    @Test
    void executeCanUse_shouldReturnRightValue(){
        var command = new GetNumberCommand(gameServer);
        var expected = new CommandResult(first.getStringCowsAndBullsNumber(), null);
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
    void executeCantUse_shouldReturnRightValue(){
        var command = new GetNumberCommand(gameServer);
        var expected = new CommandResult("You can't use this command", null);

        CommandResult actual = command.execute(first);

        assertEquals(expected, actual);
    }
}
