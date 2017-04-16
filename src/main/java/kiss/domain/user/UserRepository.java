package kiss.domain.user;

import org.springframework.stereotype.Repository;

/**
 * Created by kiss on 2017/4/16.
 */
public interface UserRepository {
    void editingMode();

    User find(String userId);

    void store(User user);
}
