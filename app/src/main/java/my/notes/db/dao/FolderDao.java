package my.notes.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import my.notes.db.entity.Folder;

import java.util.List;

@Dao
public interface FolderDao {

    @Insert
    void insert(Folder... folder);

    @Update
    void update(Folder folder);

    @Query("SELECT * FROM folder_table")
    LiveData<List<Folder>> getAllFolders();

    @Delete
    void delete(Folder... folders);
}
