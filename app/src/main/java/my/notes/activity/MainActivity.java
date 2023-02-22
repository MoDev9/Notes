package my.notes.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import my.notes.ui.note.NotesFragment;
import my.notes.R;
import my.notes.db.NoteDatabase;
import my.notes.db.dao.NoteDao;
import my.notes.fragment.*;
import my.notes.utils.ActivityUtils;
import my.notes.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity implements NotesFragment.OnNotesFragmentInteractionListener,
        RemindersFragment.OnRemindersFragmentInteractionListener, ArchivesFragment.OnArchivesFragmentInteractionListener,
        BinFragment.OnBinFragmentInteractionListener, EditTagsFragment.OnEditTagsFragmentInteractionListener {

    private Toolbar toolbar;

    private MainViewModel viewModel;

    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate() being called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setupToolbar();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupViewFragment();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"OnResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"OnStart()");
    }

    private void tempdelete(){
        new DeleteDbAsyncTask(NoteDatabase.getInstance(getApplication())).execute();
    }

    private void setupViewFragment(){
        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(NotesFragment.TAG);
        if(notesFragment==null){
            Log.d("NotesFrag", "null fragment");
            notesFragment = NotesFragment.newInstance(0);
        }
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),notesFragment, R.id.contentFrame, NotesFragment.TAG);

    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
    }


    @Override
    public void onEditTagsFragmentFinish(EditTagsFragment editTagsFragment) {
        ActivityUtils.removeFragmentFromContainer(getSupportFragmentManager(), editTagsFragment);
    }

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

    @Override
    public void onBinFragmentInteraction() {
        getSupportActionBar().setTitle("Deleted");
    }

    @Override
    public void onNotesFragmentInteraction() {
        getSupportActionBar().setTitle("Notes");
    }

    @Override
    public void onRemindersFragmentInteraction() {
        getSupportActionBar().setTitle("Reminders");
    }

    @Override
    public void onArchivesFragmentInteraction() {
        getSupportActionBar().setTitle("Archive");
    }
}
