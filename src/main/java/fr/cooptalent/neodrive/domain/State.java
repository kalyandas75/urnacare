package fr.cooptalent.neodrive.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "state")
public class State implements Serializable {
    @Id
    @Column(length = 50)
    private String code;

    @Column(length = 100)
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "State{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
