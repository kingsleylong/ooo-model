package kiss.domain.user;

/**
 * Created by kiss on 2017/4/16.
 */
public class Phone {
    private String id;
    private String number;
    private String band;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBand() {
        return band;
    }

    public Phone() {
    }

    public Phone(String id, String number, String band) {
        this.id = id;
        this.number = number;
        this.band = band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", band='" + band + '\'' +
                '}';
    }
}
