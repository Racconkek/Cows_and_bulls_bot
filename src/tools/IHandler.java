package tools;

import core.player.User;

public interface IHandler {
    String handleInput(String str, User user);
}
