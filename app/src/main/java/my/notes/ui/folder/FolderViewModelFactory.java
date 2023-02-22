package my.notes.ui.folder;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import my.notes.db.datasource.FolderRepository;

public class FolderViewModelFactory implements ViewModelProvider.Factory {

    private FolderRepository folderRepository;

    public FolderViewModelFactory(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(FolderViewModel.class))
            return (T) new FolderViewModel(folderRepository);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
