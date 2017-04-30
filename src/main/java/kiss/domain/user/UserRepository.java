package kiss.domain.user;

import kiss.infrastructure.ormHandler.Repository;

/**
 * Created by kiss on 2017/4/16.
 */
public interface UserRepository extends Repository<User> {
    User find(String userId);

    void store(User user);

    void add(User user);
}
