package dmit2015.hr.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DEPARTMENTS", schema = "HR", catalog = "")
public class DepartmentsEntity {
    private long departmentId;
    private String departmentName;

    @Id
    @Column(name = "DEPARTMENT_ID", nullable = false, precision = 0)
    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "DEPARTMENT_NAME", nullable = false, length = 30)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentsEntity that = (DepartmentsEntity) o;
        return departmentId == that.departmentId && Objects.equals(departmentName, that.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, departmentName);
    }
}
