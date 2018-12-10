package core.commands;

import core.player.IPlayer;
import core.primitives.CommandResult;

public class HelpCommand implements ICommand {

    private final String helpText = "help text";

    @Override
    public CommandResult execute(IPlayer user) {
        return new CommandResult(helpText, null, true);
    }

    @Override
    public String getName() {
        return "/help";
    }
}
