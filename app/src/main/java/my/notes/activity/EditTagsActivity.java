package my.notes.activity;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import my.notes.R;
import my.notes.adapter.EditTagsAdapter;
import my.notes.db.entity.Tag;
import my.notes.viewmodels.EditTagsViewModel;

import java.util.List;

public class EditTagsActivity extends AppCompatActivity implements View.OnClickListener, EditTagsAdapter.FragmentInteractor {
    public static final String TAG = EditTagsActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private EditTagsAdapter editTagsAdapter;
    private EditText editText;
    private EditTagsViewModel viewModel;
    private View checkButton, firstView, secondView, plusButton, exitButton, backButton, deleteAllButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_tags);

        backButton = findViewById(R.id.backButton);
        deleteAllButton = findViewById(R.id.deleteAllButton);
        firstView = findViewById(R.id.firstView);
        secondView = findViewById(R.id.secondView);
        plusButton = findViewById(R.id.plusButton);
        exitButton = findViewById(R.id.exitButton);
        checkButton = findViewById(R.id.checkButton);

        recyclerView = findViewById(R.id.recyclerView);

        backButton.setOnClickListener(this);
        deleteAllButton.setOnClickListener(this);
        firstView.setOnClickListener(this);
        secondView.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        checkButton.setOnClickListener(this);

        editText = findViewById(R.id.et_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            editText.setFocusedByDefault(true);
        }
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Log.e(TAG, "Got the focus");
                    Toast.makeText(EditTagsActivity.this, "Got the focus", Toast.LENGTH_LONG).show();
                    onFocus();
                } else {
                    Log.e(TAG, "Lost the focus");
                    Toast.makeText(EditTagsActivity.this, "Lost the focus", Toast.LENGTH_LONG).show();
                    loseFocus();
                }
            }
        });

        editTagsAdapter = new EditTagsAdapter(this);
        recyclerView.setAdapter(editTagsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(EditTagsViewModel.class);
        viewModel.getAllTags().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                editTagsAdapter.setTags(tags);
            }
        });

    }

    private void onFocus(){
        Log.e(TAG, "onFocus()");
        plusButton.setClickable(false);
        plusButton.setVisibility(View.GONE);
        exitButton.setClickable(true);
        exitButton.setVisibility(View.VISIBLE);
        checkButton.setClickable(true);
        checkButton.setVisibility(View.VISIBLE);
        firstView.setVisibility(View.VISIBLE);
        secondView.setVisibility(View.VISIBLE);
    }

    private void loseFocus(){
        Log.e(TAG, "loseFocus()");
        exitButton.setClickable(false);
        exitButton.setVisibility(View.INVISIBLE);
        plusButton.setClickable(true);
        plusButton.setVisibility(View.VISIBLE);
        checkButton.setClickable(false);
        checkButton.setVisibility(View.INVISIBLE);
        firstView.setVisibility(View.INVISIBLE);
        secondView.setVisibility(View.INVISIBLE);
    }

    private void addTag(){
        String title = editText.getText().toString();
        if(!title.isEmpty()) {
            Log.e(TAG, "Adding tag with title: " +title);
            Toast.makeText(this,"Adding tag with title: " +title,Toast.LENGTH_SHORT).show();
            viewModel.insert(new Tag(title));
            editText.getText().clear();
            return;
        }
        Log.e(TAG, "Tag title is empty");
        Toast.makeText(this,"Tag title is empty",Toast.LENGTH_SHORT).show();
    }

    private void deleteAllTags(){
        viewModel.deleteAllTags();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exitButton:
                if(v.isClickable()) {
                    Log.e(TAG, "exitButton clicked");
                    loseFocus();
                }
                break;
            case R.id.plusButton:
                if(v.isClickable()) {
                    Log.e(TAG, "plusButton clicked");
                    if (editText.hasFocus()) {
                        onFocus();
                    } else {
                        editText.requestFocus();
                    }
                }
                break;
            case R.id.checkButton:
                if(v.isClickable()) {
                    Log.e(TAG, "checkButton clicked");
                    addTag();
                }
                break;
            case R.id.backButton:
                Log.e(TAG, "backButton clicked");
                finish();
                break;
            case R.id.deleteAllButton:
                Log.e(TAG, "deleteAllButton clicked");
                deleteAllTags();
                break;
        }
    }

    @Override
    public void delete(Tag tag) {
        viewModel.delete(tag);
    }
}
