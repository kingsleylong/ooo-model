package kiss.infrastructure.ormHandler;

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

    @Override
    public T find(String id) {
        T t = processFind(id);

        return t;
    }

    @Override
    public void store(T t) {
        if (!this.isEditingMode()) {
            throw new RuntimeException("");
        }

        processStore(t);
    }

    protected abstract void processStore(T t);

    protected abstract T processFind(String id);

}
