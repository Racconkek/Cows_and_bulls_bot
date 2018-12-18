package tests.core;

import core.player.User;
import core.primitives.UserGameRole;
import core.queue.UserQueue;
import exceptions.UserQueueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class UserQueueUnitTest {

    private UserQueue queue = new UserQueue();
    private User first;
    private User second;

    @BeforeEach
    void setUp(){
        first = new User("TestUser1", "111", UserGameRole.WAITER);
        second = new User("TestUser2", "222", UserGameRole.WAITER);
    }

    @Test
    void enqueue_shouldThrowException() throws UserQueueException {
       assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null));
    }

    @Test
    void enqueue_shouldReturnTrueValue(){
        first = new User("TestUser", "111", UserGameRole.WAITER);

        var actual = queue.enqueue(first);

        assertTrue(actual);
    }

    @Test
    void enqueue_shouldReturnFalseValue(){
        first = new User("TestUser", "111", UserGameRole.WAITER);
        queue.enqueue(first);

        var actual = queue.enqueue(second);

        assertFalse(actual);
    }

    @Test
    void isEmpty_shouldReturnTrueValue(){
        var actual = queue.isEmpty();

        assertTrue(actual);
    }

    @Test
    void isEmpty_shouldReturnFalseValue(){
        queue.enqueue(first);
        var actual = queue.isEmpty();

        assertFalse(actual);
    }

    @Test
    void hasUser_shouldReturnTrueValue(){
        queue.enqueue(first);
        var actual = queue.hasUser(first);

        assertTrue(actual);
    }

    @Test
    void hasUser_shouldReturnFalseValue(){
        queue.enqueue(first);
        var actual = queue.hasUser(second);

        assertFalse(actual);
    }

    @Test
    void size_shouldReturnRightValue(){
        queue.enqueue(first);
        var actual = queue.size();

        assertEquals(1, actual);
    }

    @Test
    void size_shouldReturnZeroValue(){
        var actual = queue.size();

        assertEquals(0, actual);
    }
}
