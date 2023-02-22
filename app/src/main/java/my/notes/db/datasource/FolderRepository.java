package my.notes.db.datasource;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import my.notes.db.NoteDatabase;
import my.notes.db.dao.FolderDao;
import my.notes.db.entity.Folder;

import java.util.List;

public class FolderRepository {
    private FolderDao dao;
    private LiveData<List<Folder>> folders;
    private static FolderRepository INSTANCE;

    private FolderRepository(Application application) {
        NoteDatabase db = NoteDatabase.getInstance(application);
    }

    public static FolderRepository getInstance(Application application) {
        if(INSTANCE == null) {
            INSTANCE = new FolderRepository(application);
        }
        return INSTANCE;
    }

    public void insert(Folder... folders){
        new InsertFolderAsyncTask(dao).execute(folders);
    }

    public void update(Folder... folders){
        new UpdateFolderAsyncTask(dao).execute(folders);
    }

    public void delete(Folder... folders) {
        new DeleteFolderAsyncTask(dao).execute(folders);
    }

    public LiveData<List<Folder>> getAllFolders() {
        if (folders==null){
            folders = dao.getAllFolders();
        }
        return folders;
    }

    private static class InsertFolderAsyncTask extends AsyncTask<Folder, Void, Void> {
        private FolderDao dao;

        private InsertFolderAsyncTask(FolderDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Folder... folders) {
            dao.insert(folders);
            return null;
        }
    }


    private static class UpdateFolderAsyncTask extends AsyncTask<Folder, Void, Void> {
        private FolderDao dao;

        private UpdateFolderAsyncTask(FolderDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Folder... folders) {
            dao.update(folders[0]);
            return null;
        }
    }

    private static class DeleteFolderAsyncTask extends AsyncTask<Folder, Void, Void> {
        private FolderDao dao;

        private DeleteFolderAsyncTask(FolderDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Folder... folders) {
            dao.delete(folders);
            return null;
        }
    }

}
