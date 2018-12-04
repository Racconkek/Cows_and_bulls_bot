package core.userdb;

import core.primitives.User;
import core.primitives.UserGameRole;
import exceptions.UserDataBaseException;
import java.util.Set;

public interface IUserDataBase {
  boolean register(String name, String chatID, UserGameRole role);
  boolean delete(String name) throws UserDataBaseException;
  User getUser(String name) throws UserDataBaseException;
  User getUserByID(String chatID) throws UserDataBaseException;
  User getUserElseNull(String name);
  Set<User> getUsers();
  boolean hasUser(String name);
}