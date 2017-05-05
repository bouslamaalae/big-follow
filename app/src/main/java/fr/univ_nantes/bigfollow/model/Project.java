package fr.univ_nantes.bigfollow.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Projects")
public class Project extends Model {

    @Column(name = "Name")
    private String name;
    @Column(name = "SpreadsheetId")
    private String spreadsheetId;

    public Project() {
        super();
    }

    public static Project getById(int id) {
        return new Select()
                .from(Project.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static List<Project> getAll() {
        return new Select()
                .from(Project.class)
                .orderBy("Name ASC")
                .execute();
    }

    public List<ProjectSheet> getSheets() {
        return getMany(ProjectSheet.class, "Project");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
    }
}
