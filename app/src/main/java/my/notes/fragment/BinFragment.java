package my.notes.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import my.notes.ui.note.NotesFragment;
import my.notes.R;
import my.notes.ui.note.NoteAdapter;
import my.notes.db.entity.Note;
import my.notes.viewmodels.BinFragmentViewModel;

import java.util.List;

public class BinFragment extends BaseFragment implements NoteAdapter.OnNoteAdapterListener {
    public static final String TAG = BinFragment.class.getSimpleName();
    private OnBinFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private ActionMode actionMode;
    private BinFragmentViewModel viewModel;

    public BinFragment(){}

    public static BinFragment newInstance() {
        return new BinFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_edit;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "OnCreate()");
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(BinFragmentViewModel.class);

        observeViewModel(viewModel);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.deleted_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.emptyBin:
                Log.d(TAG, "bin emptied");
                break;
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpToolbar();

        setUpListAdapter();

    }

    private void setUpListAdapter(){
        Log.e(TAG, "setUpListAdapter()");
        recyclerView = (RecyclerView) findViewById(R.id.suggestions_list);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setUpToolbar(){
        mListener.onBinFragmentInteraction();
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    private void observeViewModel(BinFragmentViewModel binFragmentViewModel){
        Log.e(TAG, "observeViewModel()");
        binFragmentViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                Log.e(TAG, "onChanged()");
                if(notes!=null)
                    Log.e(TAG, "notes size: "+ notes.size());
                else
                    Log.e(TAG, "notes is null");
                noteAdapter.setNotes(notes);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NotesFragment.OnNotesFragmentInteractionListener) {
            mListener = (OnBinFragmentInteractionListener) context;
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



    private void setSelected(GradientDrawable drawable){
        drawable.setStroke(4,ContextCompat.getColor(getContext(), android.R.color.black));
    }

    private void deselect(GradientDrawable drawable){
        drawable.setStroke((int)pxFromDp(getContext(),1),ContextCompat.getColor(getContext(), R.color.roundRectColor));
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


//    @Override
//    public boolean isTagsNotEmpty() {
//        return false;
//    }
//
//    @Override
//    public List<Tag> getTagsForNote() {
//        return null;
//    }

    @Override
    public void setOnLongClick(View v, final Note note){
        Log.e(TAG,"setOnLongClick()");
        if(note==null){
            Log.e(TAG,"Note is null");
            return;
        }
        final GradientDrawable drawable = (GradientDrawable) v.getBackground();
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(actionMode!=null){
                    return false;
                }

                actionMode = getActivity().startActionMode(new ActionMode.Callback() {
                    // Called when the action mode is created; startActionMode() was called
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        // Inflate a menu resource providing context menu items
                        mode.getMenuInflater().inflate(R.menu.context_menu, menu);
                        return true;
                    }
                    // Called each time the action mode is shown. Always called after onCreateActionMode, but
                    // may be called multiple times if the mode is invalidated.
                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        menu.findItem(R.id.tagItem).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                        menu.findItem(R.id.deleteItem).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                        menu.findItem(R.id.addReminder).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                        return true;// Return false if nothing is done
                    }
                    // Called when the user selects a contextual menu item
                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.tagItem:
                                Toast.makeText(getContext(), "tagItem pressed",Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                    // Called when the user exits the action mode
                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        actionMode = null;
                        deselect(drawable);
                    }
                });
                actionMode.setTitle(Integer.toString(1));
                setSelected(drawable);
                return true;
            }
        });
    }

    public interface OnBinFragmentInteractionListener {
        // TODO: Update argument type and name
        void onBinFragmentInteraction();
    }
}
