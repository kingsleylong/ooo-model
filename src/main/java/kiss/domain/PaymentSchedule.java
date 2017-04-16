package kiss.domain;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by kiss on 2017/3/3.
 */
@Entity
public class PaymentSchedule {
    public void fill(Long amount) {

    }

    @Column
    private Boolean overdue;
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Collection<PaymentScheduleDetail> paymentScheduleDetails;

    @ManyToOne
    private Bill bill;

    @Id
    @GeneratedValue
    private String id;

}
