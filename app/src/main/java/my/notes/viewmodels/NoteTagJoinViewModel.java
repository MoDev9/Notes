package my.notes.viewmodels;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import my.notes.db.datasource.NoteTagJoinRepository;
import my.notes.db.entity.Note;
import my.notes.db.entity.Tag;

import java.util.List;

public class NoteTagJoinViewModel extends AndroidViewModel {
    private NoteTagJoinRepository repository;
    private LiveData<List<Tag>> tags;
    private LiveData<List<Note>> notes;

    public NoteTagJoinViewModel(@NonNull Application application) {
        super(application);
        repository = NoteTagJoinRepository.getInstance(application);
    }

    public LiveData<List<Tag>> getTagsForNote(int noteId){
        tags = repository.getTagsForNote(noteId);
        if(tags==null){
            Log.e("NoteTagJoinViewModel", "null tags");
        }
        return tags;
    }

    public LiveData<List<Note>> getNotesForTag(int tagId){
        notes = repository.getNotesForTag(tagId);
        if(notes==null){
            Log.e("NoteTagJoinViewModel", "null notes");
        }
        return notes;
    }

}
