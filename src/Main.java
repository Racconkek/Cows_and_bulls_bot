import com.google.inject.Guice;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import server.Server;
import tools.Constants;

public class Main{

    public static void main(String[] args) {
        try {
            ApiContextInitializer.init();
            var botsApi = new TelegramBotsApi();

            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Constants.PROXY_USER, Constants.PROXY_PASS.toCharArray());
                }
            });

            var injector = Guice.createInjector(new BasicModule());
            var myBot = injector.getInstance(Server.class);

            botsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}