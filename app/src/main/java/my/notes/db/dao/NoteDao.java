package my.notes.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import my.notes.db.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    public void insertNote(Note note);

    @Insert
    public void insertNotes(Note... notes);


    @Query("SELECT * FROM note_table WHERE folderId = :folderId")
    public LiveData<List<Note>> getNotes(int folderId);

    @Query("SELECT * FROM note_table")
    public LiveData<List<Note>> getNotes();

    @Update
    void update(Note note);

//    @Query("DELETE FROM note_table WHERE folderId = :folderId ")
//    void delete(Note note, int folderId);

    @Delete
    public void delete(Note note);

    @Query("DELETE FROM note_table WHERE id IN (:ids)")
    public void deleteAllNotes(List<Integer> ids);

    @Query("DELETE FROM note_table")
    public void deleteAllNotes();
}
