package kiss.infrasturcture.ormHandler;

import kiss.domain.user.Phone;
import kiss.domain.user.User;
import kiss.infrastructure.user.UserRepositoryInMem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kiss on 2017/4/30.
 */
@Slf4j
public class DirtyCheckRepositoryTest {
    @Test
    public void testPersistSaveOrUpdate() {
        UserRepositoryInMem userRepository = new UserRepositoryInMem();
        User user = userRepository.find("123");

        user.setName("name added");

        log.info(String.valueOf(user.getPhones().size()));
        for (Phone phone :
                user.getPhones()) {
            phone.setNumber("testing number");
        }

        user.getPhones().add(new Phone(null, "13678435235", "ChinaUnion", user));

        userRepository.store(user);
    }
}
