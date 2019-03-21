package nz.govt.linz.landonline.step.landonlite.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TitleHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "titleReference", referencedColumnName = "Id")
    private Title title;

    private String description;
    private String ownerName;
    private Instant updatedTime;

    public TitleHistory() {

    }

    public TitleHistory(long id, Title title, String description, String ownerName, Instant updatedTime) {
        this.id = id;
        this.description = description;
        this.ownerName = ownerName;
        this.updatedTime = updatedTime;
        this.title = title;


    }

    public TitleHistory(Title title, Instant updatedTime) {
        this.description = title.getDescription();
        this.ownerName = title.getOwnerName();
        this.title = title;
        this.updatedTime = updatedTime;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }


    @Override
    public String toString() {
        return "TitleHistory{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                '}';
    }


}
