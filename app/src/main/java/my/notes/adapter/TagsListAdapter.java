package my.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import my.notes.R;
import my.notes.db.entity.Tag;


import java.util.List;

public class TagsListAdapter extends ArrayAdapter<Tag> {

    public TagsListAdapter(@NonNull Context context, List<Tag> tags) {
        super(context, 0, tags);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tag tag = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvTag = convertView.findViewById(R.id.tv_tags);

        tvTag.setText(tag.getTitle());
        return convertView;
    }


}
