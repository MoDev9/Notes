package my.notes.viewmodels;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import my.notes.db.datasource.TagRepository;
import my.notes.db.entity.Tag;

import java.util.List;

public class EditTagsViewModel extends AndroidViewModel {
    private TagRepository repository;
    private LiveData<List<Tag>> allTags;

    public EditTagsViewModel(@NonNull Application application) {
        super(application);
        repository = TagRepository.getInstance(application);
    }

    public void insert(Tag tag) {
        Log.e("EditTagsViewModel","insert()");
        repository.insert(tag);
    }

    public void update(Tag tag) {
        repository.update(tag);
    }

    public void delete(Tag tag) {
        Log.e("EditTagsViewModel","delete()");
        Log.e("EditTagsViewModel","About to delete tag with title "+tag.getTitle() +" and id "+tag.getId());
        repository.delete(tag);
    }

    public void deleteAllTags() {
        Log.e("EditTagsViewModel","deleteAllTags()");
        if(allTags.getValue()!=null) {
            if (!allTags.getValue().isEmpty()) {
                Log.e("EditTagsViewModel","Tags are not empty. About to be deleted");
                repository.deleteAllTags();
            }
        } else {
            Log.e("EditTagsViewModel","Tags are empty. No delete operation");
        }
    }

    public LiveData<List<Tag>> getAllTags() {
        Log.e("EditTagsViewModel","getAllTags()");
        if(allTags==null){
            allTags = repository.getAllTags();
        }
        return allTags;
    }

}
