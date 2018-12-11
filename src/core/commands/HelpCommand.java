package core.commands;

import core.player.IPlayer;
import core.primitives.CommandResult;
import tools.Constants;

public class HelpCommand implements ICommand {

    @Override
    public CommandResult execute(IPlayer user) {
        return new CommandResult(Constants.HELP_TEXT, null);
    }

    @Override
    public String getName() {
        return "/help";
    }
}
