package server;

import com.google.inject.Inject;
import core.IGameServer;
import core.player.IPlayer;
import core.player.RiddlerBot;
import core.player.User;
import core.primitives.HandlerAnswer;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;
import exceptions.UserDataBaseException;
import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tools.Constants;
import tools.selector.ICommandSelector;

public class Server extends TelegramLongPollingBot {
    private IGameServer gameServer;
    private ICommandSelector commandSelector;


    @Inject
    public Server(DefaultBotOptions botOptions, IGameServer gameServer, ICommandSelector commandSelector){
        super(botOptions);
        this.gameServer = gameServer;
        this.commandSelector = commandSelector;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage().getText();
        var name = update.getMessage().getChat().getUserName();
        var chatID = update.getMessage().getChatId().toString();
        var currentUser = gameServer.userDataBase().getUserElseNull(chatID);
        if(currentUser == null){
            initUser(name, chatID);
        }
        var command = commandSelector.getCommand(message);
        if (command != null){
            var commandResult = command.execute(currentUser);
            if (commandResult.getFirstMessage() != null)
                sendMsg(chatID, commandResult.getFirstMessage());
            if (commandResult.getSecondMessage() != null)
                sendMsg(gameServer.sessionServer().getSessionWithPlayerElseNull(currentUser)
                                .getOther(currentUser).getChatID(),
                        commandResult.getSecondMessage());
        }
        else if(!gameServer.sessionServer().hasSessionWithPlayer(currentUser) &&
                currentUser.getRole() == UserGameRole.WAITER){
            sendMsg(chatID, "You are waiting another user");
        }
        else{
            var answer = handleMessage(message, currentUser);
            if (answer.isEndSession()){
                try{
                    var session = gameServer.sessionServer().getSessionWithPlayerElseNull(currentUser);
                    gameServer.sessionServer().endSession(session.getId());
                    gameServer.userDataBase().delete(chatID);
                    sendMsg(chatID, answer.getAnswer());
                }
                catch (SessionServerException | UserDataBaseException e){
                    sendMsg(chatID, e.getMessage());
                }
            }
            else{
                var otherId = gameServer.sessionServer().
                    getSessionWithPlayerElseNull(currentUser).
                    getOther(currentUser).
                    getChatID();
                sendMsg(otherId, answer.getAnswer());
            }
        }
    }

    private HandlerAnswer handleMessage(String message, IPlayer user){
        var otherPlayer = gameServer.sessionServer().getSessionWithPlayerElseNull(user).getOther(user);
        if (otherPlayer instanceof RiddlerBot){
            return ((RiddlerBot) otherPlayer).getAnswer(message, user);
        }
        return new HandlerAnswer(message, false);
    }

    private void sendStartedSessionMsg(Pair<User, User> users){
        sendMsg(users.getFirst().getChatID(), "You are riddling");
        sendMsg(users.getSecond().getChatID(), "You have to guess the number");
    }

    private void initUser(String name, String chatID) {
        gameServer.userDataBase().register(name, chatID, UserGameRole.WAITER);
        gameServer.playerQueue().enqueue(gameServer.userDataBase().getUserElseNull(chatID));
    }

    private synchronized void sendMsg(String chatId, String s) {
        var sendMessage = new SendMessage();

        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return Constants.NAME;
    }

    @Override
    public String getBotToken() {
        return Constants.TOKEN;
    }
}
