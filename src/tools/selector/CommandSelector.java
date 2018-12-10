package tools.selector;

import com.google.inject.Inject;
import core.commands.ICommand;

import java.util.HashMap;
import java.util.HashSet;

public class CommandSelector implements ICommandSelector {

    private HashMap<String, ICommand> commands;

    @Inject
    public CommandSelector(HashSet<ICommand> commands) {
        this.commands = new HashMap<>();
        for (var c: commands){
            this.commands.put(c.getName(), c);
        }
    }

    @Override
    public ICommand getCommand(String command) {
        return commands.get(command);
    }
}
