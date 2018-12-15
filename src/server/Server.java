package server;

import com.google.inject.Inject;
import core.IGameServer;
import core.player.IPlayer;
import core.player.User;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;
import exceptions.UserDataBaseException;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tools.Constants;
import tools.handler.IHandler;
import tools.selector.ICommandSelector;

import javax.inject.Named;

public class Server extends TelegramLongPollingBot {
    private IGameServer gameServer;
    private IHandler messageHandler;


    @Inject
    public Server(DefaultBotOptions botOptions,
                  IGameServer gameServer,
                  @Named("MessageHandler") IHandler messageHandler1){
        super(botOptions);
        this.gameServer = gameServer;
        this.messageHandler = messageHandler1;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage().getText();
        var name = update.getMessage().getChat().getUserName();
        var chatID = update.getMessage().getChatId().toString();
        var currentUser = gameServer.userDataBase().getUserOrRegister(name, chatID, UserGameRole.WAITER);
        var resultMessage = messageHandler.handleInput(message, currentUser);
        if (resultMessage.getFirstAnswer() != null)
            sendMsg(chatID, resultMessage.getFirstAnswer());
        if (resultMessage.getSecondAnswer() != null)
            sendMsg(gameServer.sessionServer().getSessionWithPlayerElseNull(currentUser)
                                .getOther(currentUser).getChatID(),
                        resultMessage.getSecondAnswer());
        if (resultMessage.isEndSession()){
            endSession(currentUser);
        }
    }

    private void endSession(IPlayer user){
        var session = gameServer.sessionServer().getSessionWithPlayerElseNull(user);
        var otherPlayer = session.getOther(user);
        try {
            if (otherPlayer instanceof User) {
                sendMsg(otherPlayer.getChatID(), "Your session has ended");
                gameServer.userDataBase().delete(otherPlayer.getChatID());
            }
            sendMsg(user.getChatID(), "Your session has ended");
            gameServer.userDataBase().delete(user.getChatID());
            gameServer.sessionServer().endSession(session);
        }
        catch (SessionServerException | UserDataBaseException e){
            sendMsg(user.getChatID(), "Your session can't be ended");
        }
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
