package tools.selector;

import com.google.inject.Inject;
import core.commands.ICommand;

import java.util.HashMap;
import java.util.Set;

public class CommandSelector implements ICommandSelector {

    private HashMap<String, ICommand> commands;

    @Inject
    public CommandSelector(Set<ICommand> commands) {
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
