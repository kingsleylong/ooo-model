package kiss.infrastructure.ormHandler;

/**
 * Created by kiss on 2017/4/25.
 */
public interface RepositoryCallback<T> {
    T doInTemplate();
}
