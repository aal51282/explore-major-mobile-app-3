package com.example.project3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * MasterDetailActivity is the main activity that hosts the MajorListFragment and DetailFragment.
 */
public class MasterDetailActivity extends AppCompatActivity implements MajorListFragment.OnMajorSelectedListener {

    // Flag to determine if we’re running in landscape orientation
    private boolean twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout based on orientation (portrait or landscape)
        setContentView(R.layout.activity_master_detail);

        // If the detail container exists, we’re in landscape mode
        if (findViewById(R.id.detail_container) != null) {
            twoPane = true;
        }

        // Load the MajorListFragment into the proper container.
        if (savedInstanceState == null) {
            if (twoPane) {
                // In landscape, load list fragment into list_container.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_container, new MajorListFragment())
                        .commit();
            } else {
                // In portrait, load list fragment into single container.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MajorListFragment())
                        .commit();
            } // if
        } // if

    } // onCreate

    // This callback is triggered when a major is selected in the list fragment.
    @Override
    public void onMajorSelected(String major) {
        DetailFragment detailFragment = DetailFragment.newInstance(major);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (twoPane) {
            // In landscape, show detail fragment in the detail container.
            transaction.replace(R.id.detail_container, detailFragment);
        } else {
            // In portrait, replace the entire container and add the transaction to the back stack.
            transaction.replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null);
        } // if
        transaction.commit();
    } // onMajorSelected

} // MasterDetailActivity