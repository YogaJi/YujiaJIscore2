package site.yogaji.yujiaji_score;

import android.os.Bundle;

//import androidx.fragment.app.Fragment;
import android.preference.PreferenceFragment;


public class SettingFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);

    }

}