package core.userdb;

import com.google.inject.Inject;
import core.player.User;
import core.primitives.UserGameRole;
import exceptions.UserDataBaseException;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserDataBase implements IUserDataBase {

  private final ConcurrentMap<String, User> idToUser;

  @Inject
  public UserDataBase() {
    idToUser = new ConcurrentHashMap<>();
  }

  @Override
  public User getUserOrRegister(String name, String chatID, UserGameRole role) {
    return idToUser.computeIfAbsent(chatID, (id) -> new User(name, id, role));
  }

  @Override
  public boolean delete(String chatID) throws UserDataBaseException {
    if (!hasUser(chatID))
      throw new UserDataBaseException(String.format("No that user with chatID: %s", chatID));
    idToUser.remove(chatID, getUser(chatID));
    return true;
  }

  @Override
  public User getUser(String chatID) throws UserDataBaseException {
    var user = getUserElseNull(chatID);

    if (user == null)
      throw new UserDataBaseException(String.format("No such user: %s", chatID));

    return user;
  }

  @Override
  public User getUserElseNull(String chatID) { return  idToUser.get(chatID); }

  @Override
  public Set<User> getUsers() { return new HashSet<>(idToUser.values()); }

  @Override
  public boolean hasUser(String chatID) { return idToUser.containsKey(chatID); }
}
