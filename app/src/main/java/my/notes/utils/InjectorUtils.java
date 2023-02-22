package my.notes.utils;

import android.content.Context;
import my.notes.AppExecutors;
import my.notes.db.datasource.NoteRepository;
import my.notes.db.datasource.TagRepository;
import my.notes.ui.note.NoteViewModelFactory;

public class InjectorUtils {

    public static AppExecutors provideAppExecutor() {
        return AppExecutors.getInstance();
    }

    public static TagRepository provideTagRepository(Context context) {
        return TagRepository.getInstance(context);
    }

    public static NoteRepository provideNoteRepository(Context context) {
        return NoteRepository.getInstance(context);
    }

    public static NoteViewModelFactory provideNoteViewModelFactory(Context context) {
        NoteRepository noteRepository = provideNoteRepository(context);
        TagRepository tagRepository = provideTagRepository(context);
        return new NoteViewModelFactory(noteRepository, tagRepository);
    }
    

}
