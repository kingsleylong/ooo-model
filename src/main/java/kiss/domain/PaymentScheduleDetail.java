package kiss.domain;

import javax.persistence.*;

/**
 * Created by kiss on 2017/3/3.
 */
@Entity
public class PaymentScheduleDetail {
    @Column
    private String subject;

    @Column
    private Long amount;

    @Id
    @GeneratedValue
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
