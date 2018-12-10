package tools.handler;

import core.player.IPlayer;
import core.primitives.HandlerAnswer;

public interface IHandler {
    HandlerAnswer handleInput(String str, IPlayer user);
}
