package kiss.infrastructure.ormHandler;

/**
 * Created by kiss on 2017/4/24.
 */
public interface Repository<T> {
    T find(String id);

    void add(T t);

    void store(T t);

    void remove(T t);

}
