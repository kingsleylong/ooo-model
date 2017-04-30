package kiss.infrastructure.ormHandler;

import kiss.domain.user.User;
import kiss.domain.user.UserRepository;

/**
 * Created by kiss on 2017/4/24.
 */
public class UserRepositoryImpl extends AbstractDirtyCheckRepository<User> implements UserRepository{

    @Override
    public void add(User user) {

    }

    @Override
    public User find(String userId) {
        return null;
    }

    @Override
    public void store(User user) {

    }

    private void ensureEditMode() {
        if (!this.isEditingMode()) {
            throw new RuntimeException("Modification is only allowed in EDITING MODE!");
        }
    }

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
//        userRepository.add(args[0]);
    }
}
