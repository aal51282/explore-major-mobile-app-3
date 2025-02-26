package com.example.project3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * MasterDetailActivity is the main activity that hosts the MajorListFragment and DetailFragment.
 */
public class MasterDetailActivity extends AppCompatActivity implements MajorListFragment.OnMajorSelectedListener {

    private boolean twoPane = false;
    private String selectedMajor = null;
    private static final String STATE_SELECTED_MAJOR = "selected_major";

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        // Determine if we're in two-pane (landscape) mode by checking for the detail_container.
        twoPane = (findViewById(R.id.detail_container) != null);

        // Only set up fragments if this is the first creation
        if (savedInstanceState == null) {
            setupInitialFragments();
        } else {
            // Restore the selected major
            selectedMajor = savedInstanceState.getString(STATE_SELECTED_MAJOR);

            // Handle fragment restoration after configuration change
            handleFragmentRestoration();
        } // if
    } // onCreate

    /**
     * Sets up the initial fragments for the activity.
     */
    private void setupInitialFragments() {
        if (twoPane) {
            // In landscape, add MajorListFragment to list_container
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_container, new MajorListFragment())
                    .commit();
        } else {
            // In portrait, add MajorListFragment to fragment_container
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MajorListFragment())
                    .commit();
        } // if
    } // setupInitialFragments

    /**
     * Handles fragment restoration after configuration change.
     */
    private void handleFragmentRestoration() {
        // We need to handle fragment restoration manually
        getSupportFragmentManager().executePendingTransactions();

        if (twoPane) {
            // We're in landscape mode now
            // Make sure list fragment is in the correct container
            MajorListFragment listFragment = (MajorListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);

            if (listFragment != null) {
                // Move the list fragment to the correct container
                getSupportFragmentManager().beginTransaction()
                        .remove(listFragment)
                        .add(R.id.list_container, new MajorListFragment())
                        .commit();
                getSupportFragmentManager().executePendingTransactions();
            } // if

            // Add the detail fragment if we have a selected major
            if (selectedMajor != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, DetailFragment.newInstance(selectedMajor))
                        .commit();
            } // if
        } // if
    } // handleFragmentRestoration

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

    /**
     * Called when the activity may be temporarily destroyed, save the instance state here.
     * @param outState Bundle in which to place your saved state.
     *
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_SELECTED_MAJOR, selectedMajor);
    } // onSaveInstanceState

    /**
     * Called when the configuration of the activity has changed.
     * @param newConfig The new device configuration.
     */
    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Save current fragments state and selected major
        String currentMajor = selectedMajor;

        // Reload the layout
        setContentView(R.layout.activity_master_detail);

        // Check for two-pane mode
        boolean newTwoPane = (findViewById(R.id.detail_container) != null);

        // Handle fragment transitions
        if (newTwoPane && !twoPane) {
            // Portrait to landscape transition
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_container, new MajorListFragment())
                    .commit();

            if (currentMajor != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, DetailFragment.newInstance(currentMajor))
                        .commit();
            } // if
        } else if (!newTwoPane && twoPane) {
            // Landscape to portrait transition
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MajorListFragment())
                    .commit();
        } // if

        // Update the twoPane state
        twoPane = newTwoPane;
        selectedMajor = currentMajor;
    } // onConfigurationChanged

} // MasterDetailActivity
