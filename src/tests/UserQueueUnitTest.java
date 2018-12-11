package tests;

import core.player.User;
import core.primitives.UserGameRole;
import core.queue.UserQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;

public class UserQueueUnitTest {

    @BeforeEach
    void setUp(){
        var queue = new UserQueue();
        var first = new User("TestUser1", "111", UserGameRole.WAITER);
        var second = new User("TestUser2", "222", UserGameRole.WAITER);
    }

    @Test
    void enqueue_shouldAddPlayer(){
        var queue = new UserQueue();
        var first = new User("TestUser", "111", UserGameRole.WAITER);

        queue.enqueue(first);
        var actual = queue.hasUser(first);

        assertTrue(actual);
    }

    @Test
    void hasPair_shouldReturnRightValue(){

    }
}
