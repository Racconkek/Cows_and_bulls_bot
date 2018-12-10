package core.primitives;

public class CommandResult {
    private final String firstMessage;
    private final String secondMessage;
    private final boolean isCorrect;

    public CommandResult(String firstMessage,
                  String secondMessage,
                  boolean isCorrect) {
        this.firstMessage = firstMessage;
        this.secondMessage = secondMessage;
        this.isCorrect = isCorrect;
    }

    public String getFirstMessage() {
        return firstMessage;
    }

    public String getSecondMessage() {
        return secondMessage;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
