package my.notes.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import my.notes.db.entity.Tag;

import java.util.List;

@Dao
public interface TagDao {
    @Insert
    void insertTag(Tag tag);

    @Query("SELECT * FROM tag_table")
    public LiveData<List<Tag>> getAllTags();

    @Update
    void update(Tag tag);

    @Delete
    void delete(Tag tag);

    @Query("DELETE FROM tag_table")
    public void deleteAllTags();
}
