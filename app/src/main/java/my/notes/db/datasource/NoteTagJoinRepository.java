package my.notes.db.datasource;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import my.notes.db.NoteDatabase;
import my.notes.db.dao.NoteTagJoinDao;
import my.notes.db.entity.Note;
import my.notes.db.entity.NoteTagJoin;
import my.notes.db.entity.Tag;

import java.util.List;

public class NoteTagJoinRepository {
    private NoteTagJoinDao dao;
    private static NoteTagJoinRepository INSTANCE;

    public static NoteTagJoinRepository getInstance(Application application) {
        if(INSTANCE == null)
            INSTANCE = new NoteTagJoinRepository(application);
        return INSTANCE;
    }

    private NoteTagJoinRepository(Application application){
        NoteDatabase db = NoteDatabase.getInstance(application);
        dao = db.noteTagJoinDao();
    }

    public void insert(NoteTagJoin noteTagJoin){
        new InsertNoteTagJoinAsyncTask(dao).execute(noteTagJoin);
    }

    public LiveData<List<Tag>> getTagsForNote(int noteId){
        return dao.getTagsForNote(noteId);
    }

    public LiveData<List<Note>> getNotesForTag(int tagId){
        return dao.getNotesForTag(tagId);
    }

    private static class InsertNoteTagJoinAsyncTask extends AsyncTask<NoteTagJoin,Void,Void> {
        private NoteTagJoinDao dao;

        private InsertNoteTagJoinAsyncTask(NoteTagJoinDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(NoteTagJoin... noteTagJoins) {
            dao.insert(noteTagJoins[0]);
            return null;
        }
    }
}
