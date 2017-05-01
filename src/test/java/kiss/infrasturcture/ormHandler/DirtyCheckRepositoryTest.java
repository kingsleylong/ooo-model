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
    public void testStore() {
        UserRepositoryInMem userRepository = new UserRepositoryInMem();
        userRepository.useEditingMode();
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

    @Test
    public void testAdd() {
        UserRepositoryInMem userRepository = new UserRepositoryInMem();
        Set<Phone> phones = new HashSet<>();
        User user = new User(null, "1", phones, null);
        phones.add(new Phone("180", null, "ChinaTelecom", user));

        userRepository.add(user);
    }
}
