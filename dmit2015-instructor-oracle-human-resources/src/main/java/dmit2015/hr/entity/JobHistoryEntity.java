package dmit2015.hr.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "JOB_HISTORY", schema = "HR", catalog = "")
@IdClass(JobHistoryEntityPK.class)
public class JobHistoryEntity {
    private long employeeId;
    private Time startDate;
    private Time endDate;

    @Id
    @Column(name = "EMPLOYEE_ID", nullable = false, precision = 0)
    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    @Id
    @Column(name = "START_DATE", nullable = false)
    public Time getStartDate() {
        return startDate;
    }

    public void setStartDate(Time startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "END_DATE", nullable = false)
    public Time getEndDate() {
        return endDate;
    }

    public void setEndDate(Time endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobHistoryEntity that = (JobHistoryEntity) o;
        return employeeId == that.employeeId && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, startDate, endDate);
    }
}
