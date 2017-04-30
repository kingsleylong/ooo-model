package kiss.infrasturcture.ormHandler;

import kiss.domain.user.Phone;
import kiss.domain.user.User;
import kiss.infrastructure.user.UserRepositoryInMem;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kiss on 2017/4/30.
 */
public class DirtyCheckRepositoryTest {
    @Test
    public void testPersistSaveOrUpdate() {
        UserRepositoryInMem userRepository = new UserRepositoryInMem();
        User user = userRepository.find("123");
        user.setName("name added");

        Set<Phone> phones = new HashSet<>();
        phones.add(new Phone("180", "18017882423", "ChinaTelecom"));
        user.setPhones(phones);

        userRepository.store(user);
    }
}
