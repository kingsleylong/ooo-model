package kiss.infrastructure.ormHandler;

import kiss.domain.user.User;

/**
 * Created by kiss on 2017/4/23.
 */
public abstract class DirtyCheckRepository<T> implements Repository<T>{
    private ThreadLocal<T> entityCopy;

    private Mode mode = Mode.READING;

    public void useEditingMode() {
        this.mode = Mode.EDITING;
    }

    Boolean isEditingMode() {
        return this.mode.equals(Mode.EDITING);
    }

    void addEditCopy(T t) {
        this.entityCopy.set(t);
    }

    T getEditCopy() {
        return this.entityCopy.get();
    }

    enum Mode {
        READING,
        EDITING
    }

    public void execute(RepositoryCallback<T> callback) {
        if (!this.isEditingMode()) {
            throw new RuntimeException("");
        }

        T t = callback.doInTemplate();

        this.entityCopy.set(t);

    }
}
