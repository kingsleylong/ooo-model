package kiss.infrastructure.ormHandler;

/**
 * Created by kiss on 2017/4/25.
 */
public abstract class RepositoryCallback<T> {
    void doInsert(T entity) {

    }

    void doUpdate(T entity) {

    }

    void doDelete(T entity) {

    }
}
