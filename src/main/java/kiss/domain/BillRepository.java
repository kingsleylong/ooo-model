package kiss.domain;

/**
 * Created by kiss on 2017/4/16.
 */
public interface BillRepository {
    void find(String billId);

    void add(Bill bill);

    void remove(Bill bill);

    void store(Bill bill);
}
