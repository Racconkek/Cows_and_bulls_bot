package tests.commandsTests;

import core.commands.HelpCommand;
import core.player.User;
import core.primitives.CommandResult;
import core.primitives.UserGameRole;
import org.junit.jupiter.api.Test;
import tools.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandUnitTest {

    @Test
    void execute_shouldReturnRightValue(){
        var user = new User("TestUser", "111", UserGameRole.WAITER);
        var command = new HelpCommand();
        var expected = new CommandResult(Constants.HELP_TEXT, null, true);

        var actual = command.execute(user);
        assertEquals(expected, actual);
    }

    @Test
    void getName_shouldReturnRightValue(){
        var command = new HelpCommand();
        var expected = "/help";

        var actual = command.getName();
        assertEquals(expected, actual);
    }

}
