package com.folcolf.skool.server.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

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
}
