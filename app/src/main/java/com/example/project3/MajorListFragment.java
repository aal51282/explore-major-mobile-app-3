package com.example.project3;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

/**
 * MajorListFragment is a fragment that displays a list of majors.
 */
public class MajorListFragment extends ListFragment {

    // Callback interface to communicate with the hosting activity.
    OnMajorSelectedListener mCallback;

    /**
     * Interface for communication with the hosting activity.
     */
    public interface OnMajorSelectedListener {
        void onMajorSelected(String major);
    } // OnMajorSelectedListener interface

    /**
     * Called when the fragment is attached to the activity.
     * @param context The activity context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnMajorSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMajorSelectedListener");
        } // try-catch
    } // onAttach

    /**
     * Called immediately after onCreateView() has returned, but before any saved state has been restored.
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set up the list using the majors array resource.
        String[] majors = getResources().getStringArray(R.array.majors_array);
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1, majors));

        // Restore the current selection if available.
        if (savedInstanceState != null) {
            int selectedPosition = savedInstanceState.getInt("selected_position", ListView.INVALID_POSITION);
            if (selectedPosition != ListView.INVALID_POSITION) {
                getListView().setItemChecked(selectedPosition, true);
            } // if
        } // if
    } // onViewCreated

    /**
     * Called when a list item is clicked.
     * @param l The ListView where the click happened
     * @param v The view that was clicked within the ListView
     * @param position The position of the view in the list
     * @param id The row id of the item that was clicked
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String major = (String) getListAdapter().getItem(position);
        mCallback.onMajorSelected(major);
        l.setItemChecked(position, true);
    } // onListItemClick

    /**
     * Called when the fragment is no longer attached to the activity.
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int position = getListView().getCheckedItemPosition();
        outState.putInt("selected_position", position);
    } // onSaveInstanceState

} // MajorListFragment

