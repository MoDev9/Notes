package my.notes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    private View mRoot;

    public BaseFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(getLayoutResId(), container,false);
        inOnCreateView(mRoot, container,savedInstanceState);
        return mRoot;
    }
    @LayoutRes
    public abstract int getLayoutResId();

    public abstract void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public View findViewById(@IdRes int id) {
        return mRoot.findViewById(id);
    }
}
