package tests.core;

import core.player.User;
import core.primitives.UserGameRole;
import core.userdb.UserDataBase;
import exceptions.UserDataBaseException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataBaseUnitTest {
//
//    @Test
//    void register_shouldReturnRightValue(){
//        var base = new UserDataBase();
//        var actual = base.register("TestName", "111", UserGameRole.WAITER);
//        assertTrue(actual);
//    }
//
//    @Test
//    void delete_shouldReturnRightValue(){
//        var base = new UserDataBase();
//        base.register("TestName", "111", UserGameRole.WAITER);
//        boolean actual = false;
//        try {
//            actual = base.delete("111");
//        }
//        catch (UserDataBaseException e){
//            assertEquals("No that user with name: TestName", e.getMessage());
//        }
//        assertTrue(actual);
//    }
//
//    @Test
//    void delete_shouldThrowException(){
//        var base = new UserDataBase();
//        base.register("TestName", "111", UserGameRole.WAITER);
//        try {
//            base.delete("222");
//        }
//        catch (UserDataBaseException e){
//            assertEquals("No that user with chatID: 222", e.getMessage());
//        }
//    }
//
//    @Test
//    void getUser_shouldReturnRightValue(){
//        var base = new UserDataBase();
//        var expected = new User("TestName", "111", UserGameRole.WAITER);
//        base.register("TestName", "111", UserGameRole.WAITER);
//        User actual = null;
//        try {
//            actual = base.getUser("111");
//        }
//        catch (UserDataBaseException e){
//            assertEquals("No such user: 111", e.getMessage());
//        }
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void getUser_shouldThrowException(){
//        var base = new UserDataBase();
//        base.register("TestName", "111", UserGameRole.WAITER);
//        try {
//            base.getUser("222");
//        }
//        catch (UserDataBaseException e){
//            assertEquals("No such user: 222", e.getMessage());
//        }
//    }
//
//    @Test
//    void getUserElseNull_shouldReturnRightValue(){
//        var base = new UserDataBase();
//        var expected = new User("TestName", "111", UserGameRole.WAITER);
//        base.register("TestName", "111", UserGameRole.WAITER);
//        var actual = base.getUserElseNull("111");
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void getUserElseNull_shouldReturnNullValue(){
//        var base = new UserDataBase();
//        base.register("TestName", "111", UserGameRole.WAITER);
//        var actual = base.getUserElseNull("222");
//        assertNull(actual);
//    }
//
//    @Test
//    void getUsers_shouldReturnRightValue(){
//        var base = new UserDataBase();
//        var expected = new HashSet<>();
//        expected.add( new User("TestName", "111", UserGameRole.WAITER));
//        expected.add( new User("TestName1", "222", UserGameRole.WAITER));
//
//        base.register("TestName", "111", UserGameRole.WAITER);
//        base.register("TestName1", "222", UserGameRole.WAITER);
//        var actual = base.getUsers();
//
//
//        assertEquals(expected.size(), actual.size());
//    }
//
//    @Test
//    void hasUser_shouldReturnTrueValue(){
//        var base = new UserDataBase();
//        base.register("TestName", "111", UserGameRole.WAITER);
//        assertTrue(base.hasUser("111"));
//    }
//
//    @Test
//    void hasUser_shouldReturnFalseValue(){
//        var base = new UserDataBase();
//        base.register("TestName", "111", UserGameRole.WAITER);
//        assertFalse(base.hasUser("222"));
//    }
}
