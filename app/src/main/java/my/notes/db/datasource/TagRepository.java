package my.notes.db.datasource;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import my.notes.db.NoteDatabase;
import my.notes.db.dao.TagDao;
import my.notes.db.entity.Tag;

import java.util.List;

public class TagRepository {
    private TagDao tagDao;
    private LiveData<List<Tag>> allTags;
    private static TagRepository INSTANCE;

    public static TagRepository getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE = new TagRepository(context);
        }
        return INSTANCE;
    }

    private TagRepository(Context context) {
        NoteDatabase db = NoteDatabase.getInstance(context);
        tagDao = db.tagDao();
    }

    public void insert(Tag tag) {
        new InsertTagAsyncTask(tagDao).execute(tag);
    }

    public void update(Tag tag) {
        new UpdateTagAsyncTask(tagDao).execute(tag);
    }

    public void delete(Tag tag) {
        new DeleteTagAsyncTask(tagDao).execute(tag);
    }

    public void deleteAllTags() {
        new DeleteAllTagsAsyncTask(tagDao).execute();
    }

    public LiveData<List<Tag>> getAllTags() {
        return tagDao.getAllTags();
    }

    private static class InsertTagAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private InsertTagAsyncTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.insertTag(tags[0]);
            return null;
        }
    }

    private static class UpdateTagAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private UpdateTagAsyncTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.update(tags[0]);
            return null;
        }
    }

    private static class DeleteTagAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private DeleteTagAsyncTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.delete(tags[0]);
            return null;
        }
    }

    private static class GTFNAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private GTFNAsyncTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.delete(tags[0]);
            return null;
        }
    }

    private static class DeleteAllTagsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TagDao tagDao;

        private DeleteAllTagsAsyncTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tagDao.deleteAllTags();
            return null;
        }
    }
}
