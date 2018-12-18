package commandsTests;

import com.google.inject.Guice;
import core.BasicModule;
import core.GameServer;
import core.IGameServer;
import core.commands.EndSessionCommand;
import core.player.IPlayer;
import core.player.User;
import core.primitives.CommandResult;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndSessionCommandUnitTest {

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
        var command = new EndSessionCommand(gameServer);
        var expected = "/ends";

        var actual = command.getName();
        assertEquals(expected, actual);
    }

    @Test
    void executeNoSessionWithUser_shouldReturnRightValue(){
        var command = new EndSessionCommand(gameServer);

        var expected = new CommandResult("No session with user: TestUser1", null);
        var actual = command.execute(first);

        assertEquals(expected, actual);
    }

    @Test
    void executeHasSessionWithOtherUser_shouldReturnRightValue(){
        var command = new EndSessionCommand(gameServer);
        gameServer.userDataBase().getUserOrRegister(first.getName(), first.getChatID(), first.getRole());
        gameServer.userDataBase().getUserOrRegister(second.getName(), second.getChatID(), second.getRole());

        try {
            gameServer.sessionServer().createSession(first, second);

            var expected = new CommandResult("Your session has ended", null);
            var actual = command.execute(first);

            assertEquals(expected, actual);
        }
        catch (SessionServerException e){
            assertEquals("User TestUser1 already have session", e.getMessage());
        }
    }


    @Test
    void executeHasSessionWithAI_shouldReturnRightValue(){
        var command = new EndSessionCommand(gameServer);
        gameServer.userDataBase().getUserOrRegister(first.getName(), first.getChatID(), first.getRole());

        try {
            gameServer.sessionServer().createSessionWithRiddlerBot(first);

            var expected = new CommandResult("Your session has ended", null);
            var actual = command.execute(first);

            assertEquals(expected, actual);
        }
        catch (SessionServerException e){
            assertEquals("User TestUser1 already have session", e.getMessage());
        }
    }

    @Test
    void executeNoUserInDataBase_shouldReturnRightValue(){
        var command = new EndSessionCommand(gameServer);
        try {
            gameServer.sessionServer().createSessionWithRiddlerBot(first);

            var expected = new CommandResult("No that user with chatID: 111", null);
            var actual = command.execute(first);

            assertEquals(expected, actual);
        }
        catch (SessionServerException e){
            assertEquals("User TestUser1 already have session", e.getMessage());
        }
    }
}
