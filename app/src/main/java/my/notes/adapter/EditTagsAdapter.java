package my.notes.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import my.notes.R;
import my.notes.db.entity.Tag;

import java.util.ArrayList;
import java.util.List;

public class EditTagsAdapter extends RecyclerView.Adapter<EditTagsAdapter.TagHolder> {
    private List<Tag> tags = new ArrayList<>();
    private FragmentInteractor fragmentInteractor;
    private boolean deleteOperation;
    public static final String TAG = EditTagsAdapter.class.getSimpleName();
    public EditTagsAdapter(FragmentInteractor fragmentInteractor) {
        this.fragmentInteractor = fragmentInteractor;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        holder.bind(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void setTags(List<Tag> tags){
        this.tags.clear();
        this.tags.addAll(tags);
        if(deleteOperation){
            Log.e(TAG,"delete operation");
        }
        notifyDataSetChanged();
    }

    class TagHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Tag tag;
        private String tagTitle;
        private EditText editTag;
        private ImageView ivTag, ivBin, ivEdit, ivCheck;
        private TagHolder(@NonNull View itemView) {
            super(itemView);
            editTag = itemView.findViewById(R.id.edit_tag);
            ivTag = itemView.findViewById(R.id.iv_tag);
            ivBin = itemView.findViewById(R.id.iv_bin);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivCheck = itemView.findViewById(R.id.iv_check);

            ivTag.setOnClickListener(this);
            ivBin.setOnClickListener(this);
            ivEdit.setOnClickListener(this);
            ivCheck.setOnClickListener(this);

            editTag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        Log.e(TAG,"editTag has focus");
                        onFocus();
                    } else {
                        Log.e(TAG,"editTag lost focus");
                        ivBin.setClickable(false);
                        ivBin.setVisibility(View.GONE);
                        ivTag.setClickable(true);
                        ivTag.setVisibility(View.VISIBLE);
                        ivCheck.setClickable(false);
                        ivCheck.setVisibility(View.GONE);
                        ivEdit.setClickable(true);
                        ivEdit.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        private void bind(Tag tag){
            this.tag = tag;
            tagTitle = tag.getTitle();
            editTag.setText(tagTitle);
        }

        private void onFocus(){
            ivTag.setClickable(false);
            ivTag.setVisibility(View.INVISIBLE);
            ivBin.setClickable(true);
            ivBin.setVisibility(View.VISIBLE);
            ivEdit.setClickable(false);
            ivEdit.setVisibility(View.INVISIBLE);
            ivCheck.setClickable(true);
            ivCheck.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_edit:
                    if(v.isClickable()) {
                        Log.e(TAG,"iv_edit clicked");
                        editTag.requestFocus();
                    }
                    break;
                case R.id.iv_bin:
                    if(v.isClickable()) {
                        Log.e(TAG,"iv_bin clicked");
                        fragmentInteractor.delete(tag);
                    }
                    break;
                case R.id.iv_check:
                    if(v.isClickable()) {
                        Log.e(TAG,"iv_check clicked");
                    }
                    break;
                case R.id.iv_tag:
                    if(v.isClickable()) {
                        Log.e(TAG,"iv_tag clicked");
                    }
                    break;
            }
        }
    }

    public interface FragmentInteractor{
        void delete(Tag tag);

    }
}
