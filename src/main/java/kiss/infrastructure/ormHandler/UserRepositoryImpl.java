package kiss.infrastructure.ormHandler;

import kiss.domain.user.User;

/**
 * Created by kiss on 2017/4/24.
 */
public class UserRepositoryImpl extends DirtyCheckRepository<User>{
//    @Override
//    public User find(String id) {
//        User user = null;
//
//        if (this.isEditingMode()) {
//            this.addEditCopy(user);
//        }
//        return user;
//    }

    public User find(String id) {
        execute(new RepositoryCallback<User>() {
            @Override
            public User doInTemplate() {
                return new User("dw");
            }
        });

        return null;
    }

    @Override
    public void add(User user) {
        ensureEditMode();

        // TODO Persist user

        this.addEditCopy(user);
    }

    @Override
    public void store(User user) {
        ensureEditMode();

        User editCopy = this.getEditCopy();

        // TODO Persist user

        this.addEditCopy(user);
    }

    @Override
    public void remove(User user) {

    }

    private void ensureEditMode() {
        if (!this.isEditingMode()) {
            throw new RuntimeException("Modification is only allowed in EDITING MODE!");
        }
    }
}
