package fr.univ_nantes.bigfollow.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;

@Table(name = "Sheets")
public class ProjectSheet extends Model {
    @Column(name = "Name")
    private String name;
    @Column(name = "Type")
    private String type;
    @Column(name = "Range")
    private String range;
    @Column(name = "UpdatedAt")
    private Date updateAt;
    @Column(name = "Project")
    private Project project;

    public static ProjectSheet getById(long idSheet) {
        return new Select()
                .from(ProjectSheet.class)
                .where("id = ?", idSheet)
                .executeSingle();
    }

    public static ProjectSheet getByIdProjectAndType(long idProject, int type) {
        return new Select()
                .from(ProjectSheet.class)
                .where("Project = ?", idProject)
                .and("Type = ?", type)
                .executeSingle();
    }

    public ProjectSheet() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
