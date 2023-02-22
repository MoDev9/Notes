package my.notes.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "note_tag_join",
        primaryKeys = {  "noteId", "tagId"  },
        foreignKeys = {
                @ForeignKey(entity = Note.class,
                            parentColumns = "id",
                            childColumns = "noteId"),
                @ForeignKey(entity = Tag.class,
                        parentColumns = "id",
                        childColumns = "tagId")
        })
public class NoteTagJoin {
    private int noteId;
    private int tagId;

    public NoteTagJoin(int noteId, int tagId) {
        this.noteId = noteId;
        this.tagId = tagId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
