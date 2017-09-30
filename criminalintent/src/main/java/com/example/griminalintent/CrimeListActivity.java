package com.example.griminalintent;


import android.support.v4.app.Fragment;

/**
 * Created by mdx on 2017/9/11.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
