package my.notes.db.datasource;

import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import my.notes.AppExecutors;
import my.notes.db.NoteDatabase;
import my.notes.db.entity.Note;
import my.notes.db.dao.NoteDao;

import java.util.Arrays;
import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private static NoteRepository INSTANCE;

    private AppExecutors executor;

    private NoteRepository(Context context, AppExecutors executor) {
        NoteDatabase db = NoteDatabase.getInstance(context);
        this.executor = executor;
        noteDao = db.noteDao();
    }

    private NoteRepository(Context context) {
        NoteDatabase db = NoteDatabase.getInstance(context);
        noteDao = db.noteDao();
    }

    public static NoteRepository getInstance(Context context, AppExecutors executor){
        if(INSTANCE == null)
            INSTANCE = new NoteRepository(context, executor);
        return INSTANCE;
    }

    public static NoteRepository getInstance(Context context){
        if(INSTANCE == null)
            INSTANCE = new NoteRepository(context);
        return INSTANCE;
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void insertNotes(Note... notes){
        new InsertNotesAsyncTask(noteDao).execute(notes);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note, int folderId) {
        Nf nf = new Nf(folderId, note);
        new DeleteNoteAsyncTask(noteDao).execute(nf);
    }

    public void deleteAllNotes(List<Integer> ids) {
        new DeleteAllNotesAsyncTask(noteDao).execute(ids.toArray(new Integer[0]));
    }

    public LiveData<List<Note>> getAllNotes(int folderId) {
//        return noteDao.getNotes(folderId);
        return noteDao.getNotes();
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNote(notes[0]);
            return null;
        }
    }

    private static class InsertNotesAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNotes(notes);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Nf, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Nf... nfs) {
            Nf nf = nfs[0];
//            noteDao.delete(nf.getNote(), nf.getFolderId());
            noteDao.delete(nf.getNote());
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Integer, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            noteDao.deleteAllNotes(Arrays.asList(integers));
            return null;
        }
    }

    private static class Nf {
        private int folderId;
        private Note note;

        public Nf(int folderId, Note note) {
            this.folderId = folderId;
            this.note = note;
        }

        public int getFolderId() {
            return folderId;
        }

        public Note getNote() {
            return note;
        }
    }
}
