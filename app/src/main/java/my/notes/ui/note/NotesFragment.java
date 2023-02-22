package my.notes.ui.note;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import my.notes.R;
import my.notes.Status;
import my.notes.db.entity.Note;
import my.notes.fragment.BaseFragment;
import my.notes.utils.InjectorUtils;

import java.util.List;

public class NotesFragment extends BaseFragment implements NoteAdapter.OnNoteAdapterListener {
    public static final String TAG = NotesFragment.class.getSimpleName();
    private OnNotesFragmentInteractionListener mListener;
    private static final String ARG_ITEM = "folder_id";
//    public static final String string1 = "When going without air conditioning, you need to consider two factors: the overall heat and the humidity. On a hot day, sweating is a surprisingly effective method of returning your body to its core temperature. As the droplets on your skin transition from liquid to gas, their evaporation pulls warmth away from your body, cooling the blood underneath your skin, which goes back to your body’s core, reducing your overall temperature. Moisture in the air, however, stalls this process. In meteorological terms, humidity is the amount of water vapor the atmosphere can hold. The more humid it is, the less space there is for your sweat to evaporate. And without your body’s natural chilling system, everything feels hotter. (Just to rub it in, humidity is likely be higher in warmer months, so as the temperature is reaching its highest pitch, your sweat will be at its least effective.) What this boils down to is that keeping cool is a matter of getting as much sweat as possible to evaporate off your skin. To do that, you need to keep relatively dry air moving across your body. And your home’s setup can help you do that.";
//    public static final String string2 = "We need to rethink how we handle user experience and security issues. Mario is going to circle back with Gen and chat about the latest engineering estimates. My Name is Mohamed. I\\'m trying to learn how to decode this proccess of negotiating the applications. If my memory serves correct this should mean that my aces and eights are perfectly working together. The Job right now is to work to complete this testing stage in or now i have to continue writing till the eleventh line is surpassed which it seems. We are no";

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private ActionMode actionMode;
    private static boolean gridOn = false;
    private int notePosition;
    private NoteViewModel viewModel;

    public NotesFragment(){}

    public static NotesFragment newInstance(int folderId){
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM, folderId);
        NotesFragment notesFragment = new NotesFragment();
        notesFragment.setArguments(args);
        return notesFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_note;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "OnCreate()");
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        setUpToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.suggestions_list);

        viewModel = new ViewModelProvider(this, InjectorUtils.provideNoteViewModelFactory(getContext())).get(NoteViewModel.class);

        noteAdapter = new NoteAdapter(this, viewModel);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        int folderId = getArguments().getInt(ARG_ITEM);

        observeViewModel(folderId);

    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {}



    private void addNotes(){
        viewModel.insertNotes(new Note("Note One", getString(R.string.note_test)),
                new Note("Note Two", getString(R.string.note_test2)),
                new Note("Exemplary Presentation",getString(R.string.note_test3)),
                new Note("Significant Function",getString(R.string.note_test4)));
    }

    private void setUpToolbar(){
        Log.e(TAG, "setUpToolbar()");
        mListener.onNotesFragmentInteraction();
    }

    private void observeViewModel(int folderId){
        Log.e(TAG, "observeViewModel()");
        viewModel.init(folderId);
        viewModel.getNotes(folderId).observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                Log.e(TAG, "onChanged()");
                if(notes==null) {
                    Log.e(TAG, "notes are null");
                    addNotes();
                }
                noteAdapter.setNotes(notes);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notes_fragment_menu, menu);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNotesFragmentInteractionListener) {
            mListener = (OnNotesFragmentInteractionListener) context;
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
                            case R.id.deleteItem:
                                Toast.makeText(getContext(), "deleteItem pressed",Toast.LENGTH_SHORT).show();
                                note.setStatus(Status.deletedStatus);
                                viewModel.update(note);
                                return true;
                            case R.id.addReminder:
                                Toast.makeText(getContext(), "addReminder pressed",Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.pinItem:
                                Toast.makeText(getContext(), "pinItem pressed",Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.deleteAllItems:
                                Toast.makeText(getContext(), "deleteAllItems pressed",Toast.LENGTH_SHORT).show();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNotesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNotesFragmentInteraction();
    }
}
