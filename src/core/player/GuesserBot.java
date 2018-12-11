package core.player;

import core.primitives.HandlerAnswer;
import core.primitives.UserGameRole;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class GuesserBot implements IPlayer {

  private String name = "GuesserBot";
  private String chatID;
  private Integer tries;
  private final UserGameRole role = UserGameRole.GUESSER;


  public GuesserBot(String chatID) {
    this.chatID = chatID;
    tries = 0;
  }

  public HandlerAnswer getAnswer(String message, IPlayer user){
    return null;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getChatID() {
    return chatID;
  }

  @Override
  public Integer getTries() {
    return tries;
  }

  @Override
  public UserGameRole getRole() {
    return role;
  }

  @Override
  public String getStringCowsAndBullsNumber() {
    return null;
  }

  @Override
  public void setRole(UserGameRole role) {
  }

  @Override
  public List<Integer> getHiddenNumber() {
    return null;
  }

  @Override
  public void increaseTries() {
    tries++;
  }
}
