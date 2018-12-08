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
import exceptions.UserQueueException;
import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tools.Constants;
import core.handler.IHandler;

public class Server extends TelegramLongPollingBot {
    private IGameServer gameServer;

    @Inject
    public Server(DefaultBotOptions botOptions, IGameServer gameServer){
        super(botOptions);
        this.gameServer = gameServer;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage().getText();
        var name = update.getMessage().getChat().getUserName();
        var chatID = update.getMessage().getChatId().toString();
        var currentUser = gameServer.userDataBase().getUserElseNull(chatID);
        if (handleCommand(message, currentUser, name, chatID)){
            return;
        }
        if(currentUser == null){
            getHelp(chatID);
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

    private boolean handleCommand(String command, IPlayer user, String name, String chatID){
        switch (command){
            case "/startu":
                startWithUser(user, name, chatID);
                return true;
            case "/startb":
                startWithBot(user, name, chatID);
                return true;
            case "/help":
                getHelp(chatID);
                return true;
            case "/getnum":
                getHiddenNumber(user, name, chatID);
                return true;
        }
        return false;
    }

    private void getHiddenNumber(IPlayer user, String name, String chatID) {
        if (user != null && gameServer.userDataBase().hasUser(chatID) &&
                gameServer.sessionServer().hasSessionWithPlayer(user) &&
                gameServer.sessionServer().getSessionWithPlayerElseNull(user).getOther(user) instanceof RiddlerBot)
        {
            sendMsg(chatID, user.getStringCowsAndBullsNumber());
        }
    }

    private void getHelp(String chatID){
        var help = "help text";
        sendMsg(chatID, help);
    }

    private void startWithUser(IPlayer user, String name, String chatID){
        if (user == null){
            initUser(name, chatID);
            try{
                var users = gameServer.playerQueue().dequeuePair();
                gameServer.sessionServer().createSession(users.getFirst(), users.getSecond());
                users.getFirst().setRole(UserGameRole.RIDDLER);
                users.getSecond().setRole(UserGameRole.GUESSER);
                sendStartedSessionMsg(users);
            }
            catch (UserQueueException | SessionServerException e){
                sendMsg(chatID, e.getMessage());
            }
        }
        else if (gameServer.sessionServer().hasSessionWithPlayer(user)){
            sendMsg(user.getChatID(), "You already have session");
        }
        else{
            sendMsg(user.getChatID(), "You are waiting for other user");
        }
    }

    private void startWithBot(IPlayer user, String name, String chatID){
        if (user == null){
            gameServer.userDataBase().register(name, chatID, UserGameRole.GUESSER);
            try{
                gameServer.sessionServer().createAISessionForPlayer(gameServer.userDataBase().getUserElseNull(chatID));
                sendMsg(chatID, "You started session with bot. Please make your guess");
            }
            catch (SessionServerException e){
                sendMsg(chatID, e.getMessage());
            }
        }
        else if(gameServer.sessionServer().hasSessionWithPlayer(user)){
            sendMsg(user.getChatID(), "You already have session");
        }
        else{
            sendMsg(user.getChatID(), "You are waiting for other user");
        }
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
