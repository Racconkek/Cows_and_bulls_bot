package core.primitives;

public class CommandResult {
    private final String firstMessage;
    private final String secondMessage;

    public CommandResult(String firstMessage,
                  String secondMessage) {
        this.firstMessage = firstMessage;
        this.secondMessage = secondMessage;
    }

    public String getFirstMessage() {
        return firstMessage;
    }

    public String getSecondMessage() {
        return secondMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CommandResult){
            var other = (CommandResult)obj;
            if(this.firstMessage == null)
                return secondMessage.equals(other.secondMessage) &&
                    other.firstMessage == null;
            if(this.secondMessage == null)
                return firstMessage.equals(other.firstMessage) &&
                    other.secondMessage == null;
            return firstMessage.equals(other.firstMessage)
                && secondMessage.equals(other.secondMessage);
        }
        return false;
    }
}
