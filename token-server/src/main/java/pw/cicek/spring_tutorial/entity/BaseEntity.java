package pw.cicek.spring_tutorial.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@MappedSuperclass
@JsonIgnoreProperties({"id"})
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "created_at")
    private final Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    public BaseEntity() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
