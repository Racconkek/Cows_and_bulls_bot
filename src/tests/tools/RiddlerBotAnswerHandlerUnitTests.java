package tools;

import com.google.inject.Guice;
import core.BasicModule;
import core.player.User;
import core.primitives.HandlerAnswer;
import core.primitives.UserGameRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.handler.RiddlerBotAnswerHandler;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RiddlerBotAnswerHandlerUnitTests {

    private RiddlerBotAnswerHandler handler;
    private User user;

    @BeforeEach
    void setUp(){
        var injector = Guice.createInjector(new BasicModule());
        handler = injector.getInstance(RiddlerBotAnswerHandler.class);
        user = new User("TestUser1", "111", UserGameRole.WAITER);
    }


    @Test
    void handleInput_WrongCountOfDigits_shouldReturnRightValue(){
        var expected = new HandlerAnswer("Wrong count of digits", null, false);
        var actual = handler.handleInput("111111", user);

        assertEquals(expected.getFirstAnswer(), actual.getFirstAnswer());
    }

    @Test
    void handleInput_RepetitionsInNumber_shouldReturnRightValue(){
        var expected = new HandlerAnswer("There are repetitions in number", null, false);
        var actual = handler.handleInput("1111", user);

        assertEquals(expected.getFirstAnswer(), actual.getFirstAnswer());
    }

    @Test
    void handleInput_IncorrectInput_shouldReturnRightValue(){
        var expected = new HandlerAnswer("Incorrect Input", null, false);
        var actual = handler.handleInput("aaa", user);

        assertEquals(expected.getFirstAnswer(), actual.getFirstAnswer());
    }

    @Test
    void handleInput_CorrectInputWin_shouldReturnRightValue() {

        user.setHiddenNumber(Arrays.asList(1, 2, 3, 4));

        var expected = "Congratulations! You win! \nAmount of tries 1 \n" + user.getStringCowsAndBullsNumber();
        var actual =  handler.handleInput("1234", user);

        assertEquals(expected, actual.getFirstAnswer());
    }

    @Test
    void handleInput_CorrectInput_shouldReturnRightValue() {

        user.setHiddenNumber(Arrays.asList(1, 2, 3, 4));

        var expected = "Cows: 2, Bulls: 2.";
        var actual =  handler.handleInput("2134", user);

        assertEquals(expected, actual.getFirstAnswer());
    }
}
