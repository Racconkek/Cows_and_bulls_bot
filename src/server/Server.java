package server;

import com.google.inject.Inject;
import core.IGameServer;
import core.primitives.User;
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
import tools.IHandler;

public class Server extends TelegramLongPollingBot {
    private IHandler handler;
    private IGameServer gameServer;

    @Inject
    public Server(DefaultBotOptions botOptions, IHandler handler, IGameServer gameServer){
        super(botOptions);
        this.handler = handler;
        this.gameServer = gameServer;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage().getText();
        var name = update.getMessage().getChat().getUserName();
        var ID = update.getMessage().getChatId().toString();
        var currentUser = gameServer.userDataBase().getUserElseNull(name);
        if (message.equalsIgnoreCase("/start") && currentUser == null ){
            initUser(name, ID);
            try{
                var users = gameServer.playerQueue().dequeuePair();
                gameServer.sessionServer().createSession(users.getFirst(), users.getSecond());
                users.getFirst().setRole(UserGameRole.RIDDLER);
                users.getSecond().setRole(UserGameRole.GUESSER);
                sendStartedSessionMsg(users);
            }
            catch (UserQueueException | SessionServerException e){
                sendMsg(ID, e.getMessage());
            }
        }
        else if (currentUser == null){
            sendMsg(ID, "To start a new game write /start");
        }
        else{
            var otherId = gameServer.sessionServer().
                    getSessionWithUserElseNull(currentUser).
                    getOther(currentUser).
                    getChatID();
            sendMsg(otherId, message);
        }
    }

    private void sendStartedSessionMsg(Pair<User, User> users){
        sendMsg(users.getFirst().getChatID(), "You are riddling");
        sendMsg(users.getSecond().getChatID(), "You have to guess the number");
    }

    private void initUser(String name, String chatID) {
        gameServer.userDataBase().register(name, chatID, UserGameRole.WAITER);
        gameServer.playerQueue().enqueue(gameServer.userDataBase().getUserElseNull(name));
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
