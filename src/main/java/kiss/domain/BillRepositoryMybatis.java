package kiss.domain;

import java.util.Date;

/**
 * Created by kiss on 2017/4/16.
 */
public class BillRepositoryMybatis implements BillRepository {
    public void find(String billId) {
        Bill bill = new Bill();
        bill.id = "id01234";
        bill.duePaymentDate = new Date();
    }

    public void add(Bill bill) {

    }

    public void remove(Bill bill) {

    }

    public void store(Bill bill) {

    }
}
