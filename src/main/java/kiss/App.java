package kiss;

import kiss.domain.user.User;
import kiss.infrastructure.ormHandler.UserRepositoryImpl;
import org.h2.tools.Server;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException, InterruptedException {
        System.out.println( "Hello World!" );
        // start the TCP Server
        Server server = Server.createTcpServer(args).start();

        TimeUnit.MINUTES.sleep(1);

        // stop the TCP Server
        server.stop();

        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        userRepository.useEditingMode();
        User user = userRepository.find("123");
        userRepository.store(user);
    }
}
