package core.primitives;

public class HandlerAnswer {
    private String firstAnswer;
    private String secondAnswer;
    private boolean endSession;

    public HandlerAnswer(String firstAnswer, String secondAnswer, boolean endSession){
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.endSession = endSession;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public boolean isEndSession() {
        return endSession;
    }
}
