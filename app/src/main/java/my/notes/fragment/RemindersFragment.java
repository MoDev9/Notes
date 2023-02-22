package my.notes.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import my.notes.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RemindersFragment.OnRemindersFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RemindersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemindersFragment extends BaseFragment {
    public static final String TAG = RemindersFragment.class.getSimpleName();
    private OnRemindersFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RemindersFragment.
     */

    public RemindersFragment(){}

    public static RemindersFragment newInstance() {
        return new RemindersFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reminders_fragment_menu, menu);
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
    public int getLayoutResId() {
        return R.layout.fragment_reminders;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpToolbar();

    }

    private void setUpToolbar(){
        mListener.onRemindersFragmentInteraction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRemindersFragmentInteractionListener) {
            mListener = (OnRemindersFragmentInteractionListener) context;
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
    public interface OnRemindersFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRemindersFragmentInteraction();
    }
}
