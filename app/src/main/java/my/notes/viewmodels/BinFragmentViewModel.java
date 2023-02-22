package my.notes.viewmodels;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import my.notes.db.datasource.NoteRepository;
import my.notes.db.entity.Note;
import my.notes.db.entity.Tag;

import java.util.List;

public class BinFragmentViewModel extends AndroidViewModel {

    public static final String TAG = "BinFragmentViewModel";
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Tag>> tags;

    public BinFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = NoteRepository.getInstance(application);
    }

//    public LiveData<List<Tag>> getTagsForNote(int noteId){
//        tags = repository.getTagsForNote(noteId);
//        if(tags==null){
//            Log.e("NoteTagJoinViewModel", "null tags");
//        }
//        return tags;
//    }

    /*
    public void delete(Note note) {
        Log.e("BinFragmentViewModel","delete()");
        if(note!=null) {
            Log.e("BinFragmentViewModel", "About to delete note with title " + note.getTitle() + " and id " + note.getId());
            repository.delete(note);
        }
        else {
            Log.e("BinFragmentViewModel", "note is null. won't delete");
        }
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    */

    public LiveData<List<Note>> getAllNotes() {
        Log.e("BinFragmentViewModel","getAllNotes()");
        if(allNotes==null)
            allNotes = repository.getAllNotes(0);
//            allNotes = repository.getDeleted();
        return allNotes;
    }
}
