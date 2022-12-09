package entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Groups {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_group", nullable = false)
    private int idGroup;
    @Basic
    @Column(name = "nameOfGroup")
    private String nameOfGroup;
    @Basic
    @Column(name = "maxStudents")
    private int maxStudents;
    @OneToMany(mappedBy = "groupsByIdGroup")
    private Collection<Students> studentsByIdGroup;

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return idGroup == groups.idGroup && Objects.equals(nameOfGroup, groups.nameOfGroup) && Objects.equals(maxStudents, groups.maxStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGroup, nameOfGroup, maxStudents);
    }

    public Collection<Students> getStudentsByIdGroup() {
        return studentsByIdGroup;
    }

    public void setStudentsByIdGroup(Collection<Students> studentsByIdGroup) {
        this.studentsByIdGroup = studentsByIdGroup;
    }
}
