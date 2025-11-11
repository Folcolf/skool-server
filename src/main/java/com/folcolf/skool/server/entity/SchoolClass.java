package com.folcolf.skool.server.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass extends PanacheEntity {
    private String name;
    @ManyToOne
    private Teacher teacher;
    @OneToMany(mappedBy = "schoolClass")
    @ToString.Exclude
    private List<Student> students;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SchoolClass schoolClass)) {
            return false;
        }
        return Objects.equals(id, schoolClass.id) &&
                Objects.equals(getName(), schoolClass.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName());
    }
}
