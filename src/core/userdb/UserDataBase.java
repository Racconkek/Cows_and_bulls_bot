package core.userdb;

import com.google.inject.Inject;
import core.primitives.User;
import core.primitives.UserGameRole;
import exceptions.UserDataBaseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserDataBase implements IUserDataBase {

  private final Map<String, User> nameToUser;
  private final Map<String, User> idToUser;

  @Inject
  public UserDataBase() {
    nameToUser = new HashMap<>();
    idToUser = new HashMap<>();
  }

  @Override
  public boolean register(String name, String chatID, UserGameRole role) {
//    if (hasUser(name))
//      throw new UserDataBaseException(String.format("User with that name: %s already exists", name));
    var user = new User(name, chatID, role);
    nameToUser.put(name, user);
    idToUser.put(chatID, user);
    return true;
  }

  @Override
  public boolean delete(String name) throws UserDataBaseException {
    if (!hasUser(name))
      throw new UserDataBaseException(String.format("No that user with name: %s", name));
    var id = getUser(name).getChatID();
    nameToUser.remove(name);
    idToUser.remove(id);
    return true;
  }

  @Override
  public User getUser(String name) throws UserDataBaseException {
    var user = getUserElseNull(name);

    if (user == null)
      throw new UserDataBaseException(String.format("No such user: %s", name));

    return user;
  }

  @Override
  public User getUserByID(String chatID) throws UserDataBaseException {
    var user = getUserByIDElseNull(chatID);

    if (user == null)
      throw new UserDataBaseException(String.format("No such user: %s", chatID));

    return user;
  }

  @Override
  public User getUserElseNull(String name) { return nameToUser.get(name); }

  public User getUserByIDElseNull(String chatID) { return  idToUser.get(chatID); }

  @Override
  public Set<User> getUsers() { return new HashSet<>(nameToUser.values()); }

  @Override
  public boolean hasUser(String name) { return nameToUser.containsKey(name); }
}
