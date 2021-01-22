package dmit2015.hr.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "LOCATIONS", schema = "HR", catalog = "")
public class LocationsEntity {
    private long locationId;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;

    @Id
    @Column(name = "LOCATION_ID", nullable = false, precision = 0)
    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "STREET_ADDRESS", nullable = true, length = 40)
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Basic
    @Column(name = "POSTAL_CODE", nullable = true, length = 12)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "CITY", nullable = false, length = 30)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "STATE_PROVINCE", nullable = true, length = 25)
    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationsEntity that = (LocationsEntity) o;
        return locationId == that.locationId && Objects.equals(streetAddress, that.streetAddress) && Objects.equals(postalCode, that.postalCode) && Objects.equals(city, that.city) && Objects.equals(stateProvince, that.stateProvince);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, streetAddress, postalCode, city, stateProvince);
    }
}
