package my.notes.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "note_table",
        foreignKeys = {@ForeignKey(entity = Folder.class, parentColumns = {"id"}, childColumns = "folderId", onDelete = CASCADE)})
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int folderId;

    private List<String> tagLabels;

    private String title;

    private String description;

    private int priority;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getTagLabels() {
        return tagLabels;
    }

    public void setTagLabels(List<String> tagLabels) {
        this.tagLabels = tagLabels;
    }
}
