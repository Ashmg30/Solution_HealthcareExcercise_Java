package com.test.healthcare.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEPENDENT")
public class Dependent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DEPENDENT_ID")
    private long dependentId;

    @Size(max = 100)
    @NotEmpty
    @NotNull(message = "Name can not be blank")
    @Column(name = "NAME")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    @NotNull(message = "BirthDate can not be blank")
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "ENROLLEE_ID")
    private Enrollee enrollee;
}
