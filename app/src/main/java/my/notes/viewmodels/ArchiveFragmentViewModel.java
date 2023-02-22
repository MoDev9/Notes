package my.notes.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import my.notes.db.datasource.NoteRepository;
import my.notes.db.entity.Note;

import java.util.List;

public class ArchiveFragmentViewModel extends AndroidViewModel {
    public static final String TAG = "ArchiveFragViewModel";
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public ArchiveFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = NoteRepository.getInstance(application);
    }

    public void update(Note note) {
        repository.update(note);
    }

    /*
    public void delete(Note note) {
        Log.e("NoteViewModel","delete()");
        if(note!=null) {
            Log.e("NoteViewModel", "About to delete note with title " + note.getTitle() + " and id " + note.getId());
            repository.delete(note);
        }
        else {
            Log.e("NoteViewModel", "note is null. won't delete");
        }
    }
    */

    /*
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    */

    public LiveData<List<Note>> getAllNotes() {
        if(allNotes==null)
            allNotes = repository.getAllNotes(0);
//            allNotes = repository.getArchived();
        return allNotes;
    }
}
