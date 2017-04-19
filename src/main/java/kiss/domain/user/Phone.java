package kiss.domain.user;

import kiss.domain.shared.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by kiss on 2017/4/16.
 */
public class Phone extends Entity{
    private String number;
    private String band;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBand() {
        return band;
    }

    public Phone(String id, String number, String band) {
        super(id);
        this.number = number;
        this.band = band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        return new EqualsBuilder()
                .append(number, phone.number)
                .append(band, phone.band)
                .append(getId(), phone.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(number)
                .append(band)
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                ", band='" + band + '\'' +
                ", id='" + getId() + '\'' +
                '}';
    }
}
