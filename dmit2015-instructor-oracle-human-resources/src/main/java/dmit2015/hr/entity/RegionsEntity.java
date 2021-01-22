package dmit2015.hr.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "REGIONS", schema = "HR", catalog = "")
public class RegionsEntity {
    private long regionId;
    private String regionName;

    @Id
    @Column(name = "REGION_ID", nullable = false, precision = 0)
    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "REGION_NAME", nullable = true, length = 25)
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionsEntity that = (RegionsEntity) o;
        return regionId == that.regionId && Objects.equals(regionName, that.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, regionName);
    }
}
