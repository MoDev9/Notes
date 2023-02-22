package my.notes.ui.note;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import my.notes.NoteTag;
import my.notes.R;
import my.notes.db.entity.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    public static final String TAG = NoteAdapter.class.getSimpleName();
    private List<NoteTag> noteTags;
    private ViewModel viewModel;
    private int counter = 0;
    private int counter2 = 0;
    private OnNoteAdapterListener noteAdapterListener;

    public interface OnNoteAdapterListener{

        void setOnLongClick(View v, Note note);
    }

    public NoteAdapter(OnNoteAdapterListener noteAdapterListener, ViewModel viewModel) {
        Log.e(TAG, "NoteAdapter constructor called");
        this.noteAdapterListener = noteAdapterListener;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteHolder(itemView);
    }

    public Note getNoteAt(int position){
        Log.e(TAG,"returning note at layout position "+position);
        return noteTags.get(position).getNote();
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(noteTags.get(position));
    }

    @Override
    public int getItemCount() {
        if(noteTags!=null)
            return noteTags.size();
        return 0;
    }

    public void setNotes(List<Note> notes) {
        Log.e(TAG, "setNotes()");

    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView tag1, tag2, tag3, number;
        private ViewGroup viewGroup;
        private ViewGroup noteContainer;

        private NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.note_title);
            textViewDescription = itemView.findViewById(R.id.note_description);
            tag1 = itemView.findViewById(R.id.tag1);
            tag2 = itemView.findViewById(R.id.tag2);
            tag3 = itemView.findViewById(R.id.tag3);
            number = itemView.findViewById(R.id.number);
            viewGroup = itemView.findViewById(R.id.tags_container);
            noteContainer = itemView.findViewById(R.id.noteContainer);
        }

        private void bind(NoteTag noteTag){
            ++counter2;
            Log.e(TAG, "Number of Times holder.bind() called: "+counter2);
            Note note = noteTag.getNote();
            List<String> tags= noteTag.getTagLabels();
            noteAdapterListener.setOnLongClick(noteContainer, note);
            textViewTitle.setText(note.getTitle());
            textViewDescription.setText(note.getDescription());
            int tagsSize;
            if(tags!=null) {
                if ((tagsSize = tags.size()) > 0) {
                    if (tagsSize == 1) {
                        tag1.setText(tags.get(0));
                        tag1.setVisibility(View.VISIBLE);
                    } else if (tagsSize == 2) {
                        tag1.setText(tags.get(0));
                        tag1.setVisibility(View.VISIBLE);
                        tag2.setText(tags.get(1));
                        tag2.setVisibility(View.VISIBLE);
                    } else if (tagsSize == 3) {
                        tag1.setText(tags.get(0));
                        tag1.setVisibility(View.VISIBLE);
                        tag2.setText(tags.get(1));
                        tag2.setVisibility(View.VISIBLE);
                        tag3.setText(tags.get(2));
                        tag3.setVisibility(View.VISIBLE);
                    } else {
                        tag1.setText(tags.get(0));
                        tag1.setVisibility(View.VISIBLE);
                        tag2.setText(tags.get(1));
                        tag2.setVisibility(View.VISIBLE);
                        number.setText((tagsSize - 3) + "+");
                    }
                    viewGroup.setVisibility(View.VISIBLE);
                }
                else {
                    Log.e(TAG, "tags are empty");
                }
            }
            else {
                Log.e(TAG, "tags are null");
            }
        }
    }

}
