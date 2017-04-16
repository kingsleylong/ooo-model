package kiss.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by kiss on 2017/3/3.
 */
@Entity
@Table(name = "BILL")
public class Bill {
    public void book(Long amount) {
        PaymentSchedule paymentSchedule = new PaymentSchedule();
        paymentSchedules = new ArrayList<PaymentSchedule>();
        paymentSchedules.add(paymentSchedule);
    }

    @Id
    @GeneratedValue
    protected String id;

    @Column(name = "NEW_BALANCE")
    protected Long newBalance;

    @Column(name = "DUE_PAYMENT_DATE")
    protected Date duePaymentDate;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Collection<PaymentSchedule> paymentSchedules;

    public Bill() {
    }


}
