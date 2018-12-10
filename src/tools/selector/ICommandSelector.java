package tools.selector;

import core.commands.ICommand;

import java.util.HashMap;

public interface ICommandSelector {
    ICommand getCommand(String command);
}
