package tools;

import com.google.inject.Guice;
import core.BasicModule;
import core.player.User;
import core.primitives.HandlerAnswer;
import core.primitives.UserGameRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.handler.RiddlerBotAnswerHandler;

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

//    @Test
//    void handleInput_WrongCountOfDigits_shouldReturnRightValue(){
//        var expected = new HandlerAnswer("Wrong count of digits", null, false);
//        var actual = handler.handleInput("111111", user);
//
//        assertEquals(expected.getFirstAnswer(), actual.getFirstAnswer());
//    }
}
