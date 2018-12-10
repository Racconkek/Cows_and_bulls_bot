//import com.google.inject.Inject;
//import core.player.User;
//import core.primitives.UserGameRole;
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//import tools.Constants;
//import tools.handler.IHandler;
//
//public class TelegBot extends TelegramLongPollingBot {
//    private Map<String, User> users = new HashMap<>();
//    private IHandler handler;
//
//    @Inject
//    public TelegBot(DefaultBotOptions botOptions, IHandler handler){
//        super(botOptions);
//        this.handler = handler;
//    }
//
//    private String getHighScores() {
//        StringBuilder text = new StringBuilder();
//        try (
//            var reader = new FileReader("src/main/java/HighScore.txt")) {
//            var scan = new Scanner(reader);
//
//            while (scan.hasNextLine()) {
//                text.append(scan.nextLine());
//                text.append("\n");
//            }
//        } catch (
//                IOException e) {
//            System.out.println(e.getMessage());
//        }
//        if (text.length() > 0)
//            return text.toString().replace("_", "");
//        else
//            return "There are no scores yet";
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        var message = update.getMessage().getText();
//        var name = update.getMessage().getChat().getUserName();
//        var ID = update.getMessage().getChatId().toString();
//        var currentUser = users.get(ID);
//        String answer;
//
//        if (message.equals("/hs")) {
//            String scores = getHighScores();
//            sendMsg(ID, scores);
//        }
//        else if (message.equals("/start") && currentUser == null){
//            initUser(ID, name);
//            sendMsg(ID, "Let's play Cows and Bulls!\n Make your guess");
//        }
//        else if (currentUser == null){
//            sendMsg(ID, "To start a new game write /start");
//        }
//        else{
//            answer = handler.handleInput(message, users.get(ID));
//            sendMsg(ID, answer);
//            if (answer.charAt(2) == 'n')
//                users.remove(ID);
//        }
//    }
//
//    private void initUser(String ID, String name){
//        users.put(ID, new User(name, ID, UserGameRole.WAITER));
//    }
//
//    @Override
//    public String getBotUsername() {
//        return Constants.NAME;
//    }
//
//    @Override
//    public String getBotToken() {
//        return Constants.TOKEN;
//    }
//
//    private synchronized void sendMsg(String chatId, String s) {
//        var sendMessage = new SendMessage();
//
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(chatId);
//        sendMessage.setText(s);
//
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//}
