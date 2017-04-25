package kiss.infrastructure.ormHandler;

import kiss.domain.user.User;
import kiss.domain.user.UserRepository;

/**
 * Created by kiss on 2017/4/24.
 */
public class UserRepositoryImpl extends DirtyCheckRepository<User> implements UserRepository{

    @Override
    public void add(User user) {
        persist(new RepositoryCallback<User>() {
            @Override
            public void doInsert(User entity) {

            }
        });
    }

    @Override
    public User find(String userId) {
        return null;
    }

    @Override
    public void store(final User user) {
        persist(new RepositoryCallback<User>() {
            @Override
            public void doInsert(User entity) {

            }

            @Override
            public void doUpdate(User entity) {

            }

            @Override
            public void doDelete(User entity) {

            }
        });
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
