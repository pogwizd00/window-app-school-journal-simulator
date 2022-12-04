package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "_rating_", schema = "school-journal-simulator", catalog = "")
public class Rating {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_rate")
    private int idRate;
    @Basic
    @Column(name = "rating")
    private int rating;
    @Basic
    @Column(name = "subject")
    private String subject;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(nullable = false, insertable = false)
    private int idStudent;
    @ManyToOne
    @JoinColumn(name = "id_s", referencedColumnName = "id_student")
    private Students studentsByIdStudent;

    public int getIdRate() {
        return idRate;
    }

    public void setIdRate(int idRate) {
        this.idRate = idRate;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating1 = (Rating) o;
        return idRate == rating1.idRate && Objects.equals(rating, rating1.rating) && Objects.equals(subject, rating1.subject) && Objects.equals(comment, rating1.comment) && Objects.equals(idStudent, rating1.idStudent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRate, rating, subject, comment, idStudent);
    }

    public Students getStudentsByIdStudent() {
        return studentsByIdStudent;
    }

    public void setStudentsByIdStudent(Students studentsByIdStudent) {
        this.studentsByIdStudent = studentsByIdStudent;
    }
}
