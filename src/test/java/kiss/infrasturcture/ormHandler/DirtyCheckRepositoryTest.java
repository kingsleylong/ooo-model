package kiss.infrasturcture.ormHandler;

import kiss.domain.user.User;
import kiss.infrastructure.ormHandler.AbstractDirtyCheckRepository;
import org.junit.Test;

/**
 * Created by kiss on 2017/4/30.
 */
public class DirtyCheckRepositoryTest {
    @Test
    public void test() {
        AbstractDirtyCheckRepository<User> repository = new AbstractDirtyCheckRepository<User>(){

        };

    }
}
