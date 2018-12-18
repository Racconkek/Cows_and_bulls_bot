//package tests.core;
//
//import core.player.IPlayer;
//import core.player.User;
//import core.primitives.UserGameRole;
//import core.queue.UserQueue;
//import exceptions.UserQueueException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.glassfish.grizzly.utils.Pair;
//
//import static junit.framework.TestCase.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//public class UserQueueUnitTest {
//
//    private UserQueue queue = new UserQueue();
//    private User first;
//    private User second;
//
//    @BeforeEach
//    void setUp(){
//        first = new User("TestUser1", "111", UserGameRole.WAITER);
//        second = new User("TestUser2", "222", UserGameRole.WAITER);
//    }
//
//    @Test
//    void enqueue_shouldAddPlayer(){
//        first = new User("TestUser", "111", UserGameRole.WAITER);
//
//        queue.enqueue(first);
//        var actual = queue.hasUser(first);
//
//        assertTrue(actual);
//    }
//
//    @Test
//    void hasPair_shouldReturnTrueValue(){
//        queue.enqueue(first);
//        queue.enqueue(second);
//
//        var actual = queue.hasPair();
//
//        assertTrue(actual);
//    }
//
//    @Test
//    void hasPair_shouldReturnFalseValue(){
//        queue.enqueue(first);
//
//        var actual = queue.hasPair();
//
//        assertFalse(actual);
//    }
//
//    @Test
//    void isEmpty_shouldReturnTrueValue(){
//        var actual = queue.isEmpty();
//
//        assertTrue(actual);
//    }
//
//    @Test
//    void hasUser_shouldReturnTrueValue(){
//        queue.enqueue(first);
//        var actual = queue.hasUser(first);
//
//        assertTrue(actual);
//    }
//
//    @Test
//    void hasUser_shouldReturnFalseValue(){
//        queue.enqueue(first);
//        var actual = queue.hasUser(second);
//
//        assertFalse(actual);
//    }
//
//    @Test
//    void size_shouldReturnRightValue(){
//        queue.enqueue(first);
//        var actual = queue.size();
//
//        assertEquals(1, actual);
//    }
//
//    @Test
//    void dequeuePair_shouldReturnRightValue(){
//        queue.enqueue(first);
//        queue.enqueue(second);
//        var expected = new Pair<>(first, second);
//        Pair<IPlayer, IPlayer> actual = null;
//        try {
//            actual = queue.dequeuePair();
//        }
//        catch (UserQueueException e){
//            assertEquals("There is no user to play with. Please wait.", e.getMessage());
//        }
//
//        assertEquals(expected.getFirst(), actual.getFirst());
//        assertEquals(expected.getSecond(), actual.getSecond());
//    }
//
//    @Test
//    void dequeuePairElseNull_shouldReturnRightValue(){
//        queue.enqueue(first);
//        queue.enqueue(second);
//        var expected = new Pair<>(first, second);
//
//        Pair<IPlayer, IPlayer> actual = queue.dequeuePairElseNull();
//
//        assertEquals(expected.getFirst(), actual.getFirst());
//        assertEquals(expected.getSecond(), actual.getSecond());
//    }
//}
