package entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Students {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_student")
    private int idStudent;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "lastName")
    private String lastName;
    @Basic
    @Column(name = "SCondition")
    private String sCondition;
    @Basic
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Basic
    @Column(name = "points")
    private int points;
    @Basic
    @Column(insertable = false, updatable = false, name = "id_g")
    private int idGroup;
    @OneToMany(mappedBy = "studentsByIdStudent")
    private Collection<Rating> ratingsByIdStudent;
    @ManyToOne
    @JoinColumn(name = "id_g", referencedColumnName = "id_group")
    private Groups groupsByIdGroup;

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getsCondition() {
        return sCondition;
    }

    public void setsCondition(String sCondition) {
        this.sCondition = sCondition;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public int getIdGroup(){return idGroup;}
//    public Integer getIdsGroup() {
//        return idGroup;
//    }

//    public void setIdsGroup(Integer idGroup) {
//        this.idGroup = idGroup;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Students students = (Students) o;
        return idStudent == students.idStudent && Objects.equals(name, students.name) && Objects.equals(lastName, students.lastName) && Objects.equals(sCondition, students.sCondition) && Objects.equals(dateOfBirth, students.dateOfBirth) && Objects.equals(points, students.points) && Objects.equals(idGroup, students.idGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStudent, name, lastName, sCondition, dateOfBirth, points, idGroup);
    }

    public Collection<Rating> getRatingsByIdStudent() {
        return ratingsByIdStudent;
    }

    public void setRatingsByIdStudent(Collection<Rating> ratingsByIdStudent) {
        this.ratingsByIdStudent = ratingsByIdStudent;
    }

    public Groups getGroupsByIdGroup() {
        return groupsByIdGroup;
    }

    public void setGroupsByIdGroup(Groups groupsByIdGroup) {
        this.groupsByIdGroup = groupsByIdGroup;
    }
}
