package nz.govt.linz.landonline.step.landonlite.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Title implements Serializable {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String description;
    private String ownerName;

    @OneToMany(mappedBy = "title",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<TitleHistory> titleHistoryList;

    public Title() {

    }

    public Title( String description, String ownerName) {
        this.description = description;
        this.ownerName = ownerName;

    }

    public long getId() {
        return id;
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

    public List<TitleHistory> getTitleHistoryList() {
        return titleHistoryList;
    }

    public void setTitleHistoryList(List<TitleHistory> titleHistoryList) {
        this.titleHistoryList = titleHistoryList;
    }

    @Override
    public String toString() {

        return "Title{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }


}
