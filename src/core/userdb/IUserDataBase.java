package core.userdb;

import core.player.User;
import core.primitives.UserGameRole;
import exceptions.UserDataBaseException;
import java.util.Set;

public interface IUserDataBase {
  boolean register(String name, String chatID, UserGameRole role);
  boolean delete(String chatID) throws UserDataBaseException;
  User getUser(String chatID) throws UserDataBaseException;
  User getUserElseNull(String chatID);
  Set<User> getUsers();
  boolean hasUser(String chatID);
}