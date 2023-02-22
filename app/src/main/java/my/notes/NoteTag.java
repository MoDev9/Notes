package my.notes;

import my.notes.db.entity.Note;
import my.notes.db.entity.Tag;

import java.util.ArrayList;
import java.util.List;

public class NoteTag {
    private Note note;

    private List<String> tagLabels;

    public NoteTag(List<Tag> tags, Note note){
        this.note = note;
        if(tags!=null) {
            tagLabels = new ArrayList<>(tags.size());
            for (Tag tag : tags) {
                tagLabels.add(tag.getTitle());
            }
        }
    }

    private NoteTag(Note note){
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public List<String> getTagLabels() {
        return tagLabels;
    }
}
