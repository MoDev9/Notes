package my.notes.ui.note;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import my.notes.db.datasource.NoteRepository;
import my.notes.db.datasource.TagRepository;
import my.notes.db.entity.Note;

import java.util.List;

public class NoteViewModel extends ViewModel {
    private NoteRepository noteRepository;
    private TagRepository tagRepository;
    private LiveData<List<Note>> notes;

    public NoteViewModel(NoteRepository noteRepository, TagRepository tagRepository) {
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
    }

    public void init(int folderId) {
        if(notes!=null)
            return;
        notes = noteRepository.getAllNotes(folderId);
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void insertNotes(Note... notes){
        noteRepository.insertNotes(notes);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }

    public void delete(Note note, int folderId) {
        Log.e("NoteViewModel","delete()");
        if(note!=null) {
            Log.e("NoteViewModel", "About to delete note with title " + note.getTitle() + " and id " + note.getId());
            noteRepository.delete(note, folderId);
        }
        else {
            Log.e("NoteViewModel", "note is null. won't delete");
        }
    }

    public void deleteAllNotes(List<Integer> ids) {
        noteRepository.deleteAllNotes(ids);
    }

    public LiveData<List<Note>> getNotes(int folderId) {
        return notes;
    }
}
