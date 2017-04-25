package kiss.infrastructure.ormHandler;

/**
 * Created by kiss on 2017/4/23.
 */
public abstract class DirtyCheckRepository<T> {
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

    protected void persist(RepositoryCallback<T> callback) {
//        switch (mode) {
//            case READING:
//                this.entityCopy.set(callback.doInTemplate(null));
//            case EDITING:
//                callback.doInTemplate(null);
////                this.entityCopy.set();
//        }
    }
}
