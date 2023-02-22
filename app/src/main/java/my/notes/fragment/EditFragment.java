package my.notes.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import my.notes.R;

public class EditFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_edit;
    }

    public static Fragment getInstance(){
        return new EditFragment();
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }
}
