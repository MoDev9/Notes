package my.notes.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import my.notes.R;
import my.notes.adapter.EditTagsAdapter;

public class EditTagsFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = EditTagsFragment.class.getSimpleName();
    private OnEditTagsFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private EditTagsAdapter editTagsAdapter;
    private EditText editText;
    private View checkButton, firstView, secondView, plusButton, exitButton, backButton;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_edit_tags;
    }

    public EditTagsFragment() {
    }

    public static EditTagsFragment newInstance() {
        return new EditTagsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditTagsFragmentInteractionListener) {
            mListener = (OnEditTagsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEditTagsFragmentInteractionListener");
        }
    }

    private void onFocus(){
        plusButton.setEnabled(false);
        plusButton.setVisibility(View.GONE);
        exitButton.setEnabled(true);
        exitButton.setVisibility(View.VISIBLE);
        checkButton.setEnabled(true);
        checkButton.setVisibility(View.VISIBLE);
        firstView.setVisibility(View.VISIBLE);
        secondView.setVisibility(View.VISIBLE);
    }

    private void loseFocus(){
        exitButton.setEnabled(false);
        exitButton.setVisibility(View.INVISIBLE);
        plusButton.setEnabled(true);
        plusButton.setVisibility(View.VISIBLE);
        checkButton.setEnabled(false);
        checkButton.setVisibility(View.INVISIBLE);
        firstView.setVisibility(View.INVISIBLE);
        secondView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        backButton = findViewById(R.id.backButton);
        firstView = findViewById(R.id.firstView);
        secondView = findViewById(R.id.secondView);
        plusButton = findViewById(R.id.plusButton);
        exitButton = findViewById(R.id.exitButton);
        checkButton = findViewById(R.id.checkButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditTagsFragmentFinish(EditTagsFragment.this);
            }
        });

        editText = (EditText) findViewById(R.id.et_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            editText.setFocusedByDefault(true);
        }
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Toast.makeText(getActivity(), "Got the focus", Toast.LENGTH_LONG).show();
                    onFocus();
                } else {
                    Toast.makeText(getActivity(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);




        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(text.isEmpty()){
                    Toast.makeText(getActivity(), "Edit Text is Empty", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Creating new tag", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exitButton:
                Log.e(TAG, "exitButton clicked");
                loseFocus();
            case R.id.plusButton:
                Log.e(TAG, "plusButton clicked");
                if(editText.hasFocus()){
                    onFocus();
                } else {
                    editText.requestFocus();
                }
                break;
            case R.id.checkButton:
                Log.e(TAG, "checkButton clicked");
                break;
            case R.id.backButton:
                Log.e(TAG, "backButton clicked");
                break;
        }
    }

    public interface OnEditTagsFragmentInteractionListener{
        void onEditTagsFragmentFinish(EditTagsFragment editTagsFragment);
    }
}
