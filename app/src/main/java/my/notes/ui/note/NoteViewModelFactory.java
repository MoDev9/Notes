package my.notes.ui.note;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import my.notes.db.datasource.NoteRepository;
import my.notes.db.datasource.TagRepository;

public class NoteViewModelFactory implements ViewModelProvider.Factory {

    private NoteRepository noteRepository;
    private TagRepository tagRepository;

    public NoteViewModelFactory(NoteRepository noteRepository, TagRepository repository) {
        this.noteRepository = noteRepository;
        this.tagRepository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(NoteViewModel.class))
            return (T) new NoteViewModel(noteRepository, tagRepository);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
