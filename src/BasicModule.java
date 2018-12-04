import com.google.inject.AbstractModule;
//import org.glassfish.hk2.utilities.reflection.tools.Constants;
import core.GameServer;
import core.IGameServer;
import core.queue.IUserQueue;
import core.queue.UserQueue;
import core.session.ISessionServer;
import core.session.SessionServer;
import core.userdb.IUserDataBase;
import core.userdb.UserDataBase;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import tools.Constants;
import tools.Handler;
import tools.IHandler;


public class BasicModule extends AbstractModule {

    @Override
    public void configure(){
        bind(IHandler.class).to(Handler.class);
        bind(IGameServer.class).to(GameServer.class);
        bind(IUserQueue.class).to(UserQueue.class);
        bind(ISessionServer.class).to(SessionServer.class);
        bind(IUserDataBase.class).to(UserDataBase.class);
        bind(DefaultBotOptions.class).toInstance(defBotOpt());
    }

    public DefaultBotOptions defBotOpt(){
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost(Constants.PROXY_HOST);
        botOptions.setProxyPort(Constants.PROXY_PORT);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return botOptions;
    }
}
