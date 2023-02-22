package my.notes.db.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "folder_table",
        indices = {@Index(value = {"folderName"}, unique = true)})
public class Folder {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String folderName;

    public Folder(String folderName) {
        this.folderName = folderName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
