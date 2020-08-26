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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ENROLLEE")
public class Enrollee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENROLLEE_ID")
    private Long enrolleeId;

    @Size(max = 100)
    @NotEmpty(message = "Please enter your name")
    @NotNull(message = "Name can not be blank")
    @Column(name = "NAME")
    private String name;

    @Column(name = "ACT_STATUS")
    @NotNull(message = "Activation status must be true or false")
    private Boolean activationStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    @NotNull
    @NotNull(message = "BirthDate can not be blank")
    private Date birthDate;

    @Column(name = "PHONE_NUMBER", length = 10)
    private Long phoneNumber;

    @OneToMany
    @JoinColumn(name = "ENROLLEE_ID")
    private List<Dependent> dependents;

}
