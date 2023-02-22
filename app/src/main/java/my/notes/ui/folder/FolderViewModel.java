package my.notes.ui.folder;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import my.notes.db.datasource.FolderRepository;
import my.notes.db.entity.Folder;

import java.util.List;

class FolderViewModel extends ViewModel {
    private FolderRepository repository;
    private LiveData<List<Folder>> folders;

    FolderViewModel(FolderRepository repository) {
        this.repository = repository;
        folders = repository.getAllFolders();
    }
    // TODO: Implement the ViewModel

    void insert(Folder... folders) {
        repository.insert(folders);
    }

    void update(Folder folder) {
        repository.update(folder);
    }

    void delete(Folder... folders) {
        repository.delete(folders);
    }

    LiveData<List<Folder>> getAllFolders() {
        return folders;
    }

}
