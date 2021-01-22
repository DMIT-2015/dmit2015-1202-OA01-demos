package dmit2015.hr.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "COUNTRIES", schema = "HR", catalog = "")
public class CountriesEntity {
    private String countryId;
    private String countryName;

    @Id
    @Column(name = "COUNTRY_ID", nullable = false, length = 2)
    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "COUNTRY_NAME", nullable = true, length = 40)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountriesEntity that = (CountriesEntity) o;
        return Objects.equals(countryId, that.countryId) && Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, countryName);
    }
}
