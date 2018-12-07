package core.player;

import core.primitives.UserGameRole;

import java.util.List;

public interface IPlayer {
    String getName();
    String getChatID();
    Integer getTries();
    UserGameRole getRole();
    void setRole(UserGameRole role);
    List<Integer> getHiddenNumber();
}
