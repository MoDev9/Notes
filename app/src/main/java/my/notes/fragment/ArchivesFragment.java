package my.notes.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import my.notes.ui.note.NotesFragment;
import my.notes.R;
import my.notes.ui.note.NoteAdapter;
import my.notes.db.entity.Note;
import my.notes.viewmodels.ArchiveFragmentViewModel;

import java.util.List;

public class ArchivesFragment extends BaseFragment implements NoteAdapter.OnNoteAdapterListener {
    public static final String TAG = ArchivesFragment.class.getSimpleName();
    private OnArchivesFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private ActionMode actionMode;
    private ArchiveFragmentViewModel viewModel;

    public ArchivesFragment(){}

    public static ArchivesFragment newInstance() {
        return new ArchivesFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_edit;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "OnCreate()");
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ArchiveFragmentViewModel.class);
        noteAdapter = new NoteAdapter(this, viewModel);
        observeViewModel(viewModel);
        setHasOptionsMenu(true);
    }

    private void observeViewModel(ArchiveFragmentViewModel archiveFragmentViewModel){
        Log.e(TAG, "observeViewModel()");
        archiveFragmentViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                Log.e(TAG, "onChanged()");
                if(notes==null)
                    Log.e(TAG, "notes is null");
                noteAdapter.setNotes(notes);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.archives_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.gridOn:
                Log.d(TAG, "gridOn pressed");
                break;
            case R.id.searchAllNotes:
                Log.d(TAG, "searchAllNotes pressed");
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpToolbar();


    }

    private void setUpToolbar(){
        mListener.onArchivesFragmentInteraction();
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NotesFragment.OnNotesFragmentInteractionListener) {
            mListener = (OnArchivesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    @Override
//    public NoteTagJoinViewModel setUpTags() {
//        return null;
//    }

    @Override
    public void setOnLongClick(View v, Note note) {

    }

    public interface OnArchivesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onArchivesFragmentInteraction();
    }
}
