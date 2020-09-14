package com.urna.urnacare.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctor")
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
public class Doctor extends User {
    @Column(nullable = false, length = 100)
    private String primarySpeciality;
    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private List<String> otherSpecialities;
    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private List<String> qualifications;
    @Column
    private String practice;
    @Column(nullable = false)
    private String registrationNumber;
}
