package my.notes.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import my.notes.db.entity.Note;
import my.notes.db.entity.NoteTagJoin;
import my.notes.db.entity.Tag;

import java.util.List;

@Dao
public interface NoteTagJoinDao {
    @Insert
    void insert(NoteTagJoin noteTagJoin);

    @Query("SELECT * FROM tag_table " +
            "INNER JOIN note_tag_join " +
            "ON tag_table.id=note_tag_join.tagId " +
            "WHERE note_tag_join.noteId=:noteId")
    LiveData<List<Tag>>getTagsForNote(final int noteId);

    @Query("SELECT * FROM note_table " +
            "INNER JOIN note_tag_join " +
            "ON note_table.id=note_tag_join.noteId " +
            "WHERE note_tag_join.tagId=:tagId")
    LiveData<List<Note>> getNotesForTag(final int tagId);
}
