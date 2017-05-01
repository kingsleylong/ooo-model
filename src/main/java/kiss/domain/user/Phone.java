package kiss.domain.user;

import kiss.domain.shared.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by kiss on 2017/4/16.
 */
@javax.persistence.Entity
@Data
@ToString(exclude = "user")
@AllArgsConstructor
@EqualsAndHashCode(exclude = "user")
public class Phone implements Serializable{
    @Id
    private String id;
    private String number;
    private String band;

    @ManyToOne
    private User user;

    public boolean equalTo(Object o) {
        return this.equals(o);
    }
}
