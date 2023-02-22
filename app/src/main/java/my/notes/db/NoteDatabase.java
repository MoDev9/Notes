package my.notes.db;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import my.notes.db.dao.NoteDao;
import my.notes.db.dao.NoteTagJoinDao;
import my.notes.db.dao.TagDao;
import my.notes.db.entity.Folder;
import my.notes.db.entity.Note;
import my.notes.db.entity.NoteTagJoin;
import my.notes.db.entity.Tag;

@Database(entities = {Note.class, Tag.class, NoteTagJoin.class, Folder.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract TagDao tagDao();
    public abstract NoteTagJoinDao noteTagJoinDao();
    private static NoteDatabase INSTANCE;

    public static synchronized NoteDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    /*private static NoteDatabase buildDatabase(Context context) {
        return Room.databaseBuilder()
    }*/

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new DeleteDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class DeleteDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
