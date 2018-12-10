package core.commands;

import core.player.IPlayer;
import core.primitives.CommandResult;

public interface ICommand {
    CommandResult execute(IPlayer user);
    String getName();
}
