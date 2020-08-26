package com.test.healthcare.stub;

import com.test.healthcare.model.Dependent;
import com.test.healthcare.model.Enrollee;

import java.util.Date;

public class HealthCareStub {

    public static Enrollee generateEnrollee(){
        return Enrollee.builder()
                .name("TestEnrollee")
                .birthDate(new Date())
                .activationStatus(true)
                .phoneNumber(9827177211L)
                .build();
    }


    public static Enrollee generateEnrolleeWithoutPhone(){
        return Enrollee.builder()
                .name("TestEnrollee")
                .birthDate(new Date())
                .activationStatus(true)
                .build();
    }

    public static Enrollee generateEnrolleeToUpdate(){
        return Enrollee.builder()
                .enrolleeId(1L)
                .name("UpdatedEnrollee")
                .birthDate(new Date())
                .activationStatus(true)
                .phoneNumber(9811727211L)
                .build();
    }

    public static Dependent generateDependent(){
        return Dependent.builder()
                .name("TestDependent")
                .birthDate(new Date())
                .build();
    }

    public static Dependent generateDependentForUpdate(){
        return Dependent.builder()
                .name("UpdatedDependent")
                .birthDate(new Date())
                .build();
    }
}
