package my.notes.viewmodels;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import my.notes.db.datasource.TagRepository;
import my.notes.db.entity.Tag;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private TagRepository repository;
    private LiveData<List<Tag>> allTags;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = TagRepository.getInstance(application);
    }

    public LiveData<List<Tag>> getAllTags() {
        Log.e("MainViewModel","getAllTags()");
        if(allTags==null){
            allTags = repository.getAllTags();
        }
        return allTags;
    }
}
