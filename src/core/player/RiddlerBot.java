package core.player;

import com.google.inject.Inject;
import tools.handler.RiddleBotAnswerHandler;
import core.primitives.HandlerAnswer;
import core.primitives.UserGameRole;
import tools.HiddenNumberGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class RiddlerBot implements IPlayer {

    private final String name = "RiddlerBot";
    private Integer tries;
    private List<Integer> hiddenNumber;
    private String chatID;
    private UserGameRole role = UserGameRole.RIDDLER;
    private RiddleBotAnswerHandler handler;

    @Inject
    public RiddlerBot(String chatID, RiddleBotAnswerHandler handler){
        this.tries = 0;
        this.chatID = chatID;
        this.hiddenNumber = HiddenNumberGenerator.createHiddenNumber();
        this.handler = handler;
    }

    public HandlerAnswer getAnswer(String message, IPlayer user){
        return handler.handleInput(message, user);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getChatID() {
        return this.chatID;
    }

    @Override
    public Integer getTries() {
        return this.tries;
    }

    @Override
    public UserGameRole getRole() {
        return this.role;
    }

    @Override
    public String getStringCowsAndBullsNumber() {
        return String
                .join("", hiddenNumber.stream().map(Object::toString).collect(Collectors.toList()));
    }

    @Override
    public void setRole(UserGameRole role) {
        this.role = role;
    }

    @Override
    public List<Integer> getHiddenNumber() {
        return this.hiddenNumber;
    }

    @Override
    public void increaseTries() {
        tries++;
    }
}
