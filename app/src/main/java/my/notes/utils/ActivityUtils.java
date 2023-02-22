package my.notes.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    public static void replaceFragmentInActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void replaceFragmentInActivity(FragmentManager fragmentManager, Fragment fragment, int frameId){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void addFragmentInActivity(FragmentManager fragmentManager, Fragment fragment, int frameId){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void removeFragmentFromContainer(FragmentManager fragmentManager, Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }
}
