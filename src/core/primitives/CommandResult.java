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
            if(this.firstMessage != null)
                return this.firstMessage.equals(other.firstMessage);
            if(this.secondMessage != null)
                return this.secondMessage.equals(other.secondMessage);
        }
        return false;
    }
}
