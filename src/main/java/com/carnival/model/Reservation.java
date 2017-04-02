package com.carnival.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by a.c.parthasarathy
 */
@Data
@EqualsAndHashCode
public class Reservation implements Serializable {

    private Long id;

    @NotNull(message = "First Name is required")
    @Size(min = 3, max = 50, message = "name must be longer than 3 and less than 40 characters")
    private String firstName;

    @NotNull(message = "First Name is required")
    @Size(min = 3, max = 50, message = "name must be longer than 3 and less than 40 characters")
    private String lastName;

    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Must be valid email")
    private String email;
    private String gender;

    private long createdDate;
    private long modifiedDate;

//    private String isActive;
//    private String createdBy;
//    private String modifiedBy;

    public Timestamp getCreatedTime() {
        return new Timestamp(this.createdDate);
    }

    public Timestamp getModifiedTime() {
        return new Timestamp(this.modifiedDate);
    }
}
