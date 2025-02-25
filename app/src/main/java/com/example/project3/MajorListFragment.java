package com.example.project3;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.ListFragment;

public class MajorListFragment extends ListFragment {

    OnMajorSelectedListener mCallback;

    // Container Activity must implement this interface.
    public interface OnMajorSelectedListener {
        void onMajorSelected(String major);
    } // OnMajorSelectedListener interface

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    } // onActivityCreated

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String major = (String) getListAdapter().getItem(position);
        mCallback.onMajorSelected(major);
        l.setItemChecked(position, true);
    } // onListItemClick

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int position = getListView().getCheckedItemPosition();
        outState.putInt("selected_position", position);
    } // onSaveInstanceState

} // MajorListFragment

