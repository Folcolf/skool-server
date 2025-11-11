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
public class Subject extends PanacheEntity {
    private String name;
    private Double coefficient;
    @ManyToOne
    @ToString.Exclude
    private Teacher teacher;
    @OneToMany(mappedBy = "subject")
    @ToString.Exclude
    private List<Grade> grades;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Subject subject))
            return false;
        return Objects.equals(id, subject.id) && Objects.equals(getName(), subject.getName()) && Objects.equals(getCoefficient(), subject.getCoefficient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), getCoefficient());
    }
}
