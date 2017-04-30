package kiss.infrastructure.user;

import kiss.domain.user.User;
import kiss.domain.user.UserRepository;
import kiss.infrastructure.ormHandler.AbstractDirtyCheckRepository;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by kiss on 2017/4/30.
 */
public class UserRepositoryInMem extends AbstractDirtyCheckRepository<User> implements UserRepository {
    @Override
    public User find(String userId) {
        User user = new User(userId, null, null, null);
        addEditCopy(user);
        return user;
    }

    @Override
    public void store(User user) {
        User copy = getEditCopy();
        try {
            persistSaveOrUpdate(copy, user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(User user) {
    }
}
