package com.example.project3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * MasterDetailActivity is the main activity that hosts the MajorListFragment and DetailFragment.
 */
public class MasterDetailActivity extends AppCompatActivity implements MajorListFragment.OnMajorSelectedListener {

    private boolean twoPane = false;
    private String selectedMajor = null;
    private static final String STATE_SELECTED_MAJOR = "selected_major";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        // Determine if we're in two-pane (landscape) mode by checking for the detail_container.
        twoPane = (findViewById(R.id.detail_container) != null);

        // Restore the selected major if available.
        if (savedInstanceState != null) {
            selectedMajor = savedInstanceState.getString(STATE_SELECTED_MAJOR);
            // Remove any fragment that was automatically restored in the portrait container.
            Fragment restoredFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (restoredFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(restoredFragment).commit();
            } // if
        } // if

        if (twoPane) {
            // In landscape, add the MajorListFragment into list_container if not already added.
            if (getSupportFragmentManager().findFragmentById(R.id.list_container) == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_container, new MajorListFragment())
                        .commit();
            } // if
            // If a major was selected before rotation, display its detail.
            if (selectedMajor != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, DetailFragment.newInstance(selectedMajor))
                        .commit();
            } // if
        } else {
            // In portrait, add MajorListFragment into fragment_container if it's not already there.
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MajorListFragment())
                        .commit();
            } // if
        } // if
    } // onCreate

    /**
     * Callback when a major is selected.
     * @param major The selected major.
     */
    @Override
    public void onMajorSelected(String major) {
        selectedMajor = major;
        DetailFragment detailFragment = DetailFragment.newInstance(major);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (twoPane) {
            // In landscape, display the detail fragment in detail_container.
            transaction.replace(R.id.detail_container, detailFragment);
        } else {
            // In portrait, replace the entire container and add the transaction to the back stack.
            transaction.replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null);
        } // if
        transaction.commit();
    } // onMajorSelected

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_SELECTED_MAJOR, selectedMajor);
    } // onSaveInstanceState
} // MasterDetailActivity
