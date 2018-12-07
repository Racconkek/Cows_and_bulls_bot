package core.player;

import core.primitives.UserGameRole;
import tools.HiddenNumberGenerator;

import java.util.List;

public class RiddlerBot implements IPlayer {

    private final String name = "RiddlerBot";
    private Integer tries;
    private List<Integer> hiddenNumber;
    private String chatID;
    private UserGameRole role = UserGameRole.RIDDLER;

    public RiddlerBot(String chatID){
        this.tries = 0;
        this.chatID = chatID;
        this.hiddenNumber = HiddenNumberGenerator.createHiddenNumber();
    }

    public String getAnswer(){
        return "";
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
    public void setRole(UserGameRole role) {
        this.role = role;
    }

    @Override
    public List<Integer> getHiddenNumber() {
        return this.hiddenNumber;
    }
}
