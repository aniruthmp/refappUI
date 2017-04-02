package com.carnival.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by a.c.parthasarathy
 */
@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
        this.createdDate = createdAt.getTime();
    }

    public Timestamp getCreatedTime() {
        return new Timestamp(this.createdDate);
    }

    public Timestamp getModifiedTime() {
        return new Timestamp(this.modifiedDate);
    }

    public Reservation(String name){
        this.setFirstName(name);
        this.setLastName(name);
    }
}
