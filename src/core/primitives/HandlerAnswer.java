package core.primitives;

public class HandlerAnswer {
    private String answer;
    private boolean endSession;

    public HandlerAnswer(String answer, boolean endSession){
        this.answer = answer;
        this.endSession = endSession;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isEndSession() {
        return endSession;
    }
}
