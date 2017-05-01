package kiss.infrastructure.user;

import kiss.domain.user.Phone;
import kiss.domain.user.User;
import kiss.domain.user.UserRepository;
import kiss.infrastructure.ormHandler.AbstractDirtyCheckRepository;
import kiss.infrastructure.ormHandler.MyBatisDirtyCheckRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kiss on 2017/4/30.
 */
public class UserRepositoryInMem extends MyBatisDirtyCheckRepository<User> implements UserRepository {
    @Override
    public User find(String userId) {
        User user = new User(userId, null, null, null);
        Set<Phone> phones = new HashSet<>();
        phones.add(new Phone("180", null, "ChinaTelecom", user));
        user.setPhones(phones);

        if (this.isEditingMode()) {
            addEditCopy(user);
        }

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
        try {
            persistSaveOrUpdate(null, user);
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
}
