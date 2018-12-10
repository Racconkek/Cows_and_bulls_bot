package core.userdb;

import com.google.inject.Inject;
import core.player.User;
import core.primitives.UserGameRole;
import exceptions.UserDataBaseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserDataBase implements IUserDataBase {

  private final Map<String, User> idToUser;

  @Inject
  public UserDataBase() {
    idToUser = new HashMap<>();
  }

  @Override
  public boolean register(String name, String chatID, UserGameRole role) {
    var user = new User(name, chatID, role);
    idToUser.put(chatID, user);
    return true;
  }

  @Override
  public boolean delete(String chatID) throws UserDataBaseException {
    if (!hasUser(chatID))
      throw new UserDataBaseException(String.format("No that user with chatID: %s", chatID));
    var id = getUser(chatID).getChatID();
    idToUser.remove(id);
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
