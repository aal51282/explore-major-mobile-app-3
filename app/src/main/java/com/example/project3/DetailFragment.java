package com.example.project3;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * DetailFragment is a fragment that displays detailed information about a major.
 * It loads text and images from raw resources based on the selected major.
 */
public class DetailFragment extends Fragment {
    private static final String ARG_MAJOR = "major";
    private String major;

    /**
     * Factory method to create a new instance of DetailFragment.
     * @param major The selected major string.
     * @return A new instance of DetailFragment.
     */
    public static DetailFragment newInstance(String major) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MAJOR, major);
        fragment.setArguments(args);
        return fragment;
    } // newInstance

    // Required empty public constructor.
    public DetailFragment() {
    } // DetailFragment constructor

    /**
     * Called to do initial creation of a fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the selected major from the arguments.
        if (getArguments() != null) {
            major = getArguments().getString(ARG_MAJOR);
        } // if
    } // onCreate

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Get references to the views in the layout.
        TextView textDetail = view.findViewById(R.id.textDetail);
        ImageView imagePrimary = view.findViewById(R.id.imagePrimary);
        ImageView imageSecondary = view.findViewById(R.id.imageSecondary);

        // Load the combined text from raw resources.
        int rawResource = getRawResourceForMajor(major);
        String content = readRawTextFile(rawResource);
        textDetail.setText(content);

        // Get two image resources for the selected major.
        int[] images = getImageResourcesForMajor(major);
        imagePrimary.setImageResource(images[0]);
        imageSecondary.setImageResource(images[1]);

        return view;
    } // onCreateView

    /**
     * Get the raw resource ID for the combined information of the selected major.
     * @param major The selected major string.
     * @return The raw resource ID for the combined information.
     */
    private int getRawResourceForMajor(String major) {
        if (major.equals("Computer Science")) {
            return R.raw.cs_combined;
        } else if (major.equals("Genetics")) {
            return R.raw.genetics_combined;
        } else if (major.equals("Economy")) {
            return R.raw.economy_combined;
        } else if (major.equals("Chemistry")) {
            return R.raw.chemistry_combined;
        } else if (major.equals("Mathematics")) {
            return R.raw.mathematics_combined;
        } else if (major.equals("Physics")) {
            return R.raw.physics_combined;
        } // if
        return R.raw.default_info;
    } // getRawResourceForMajor

    /**
     * Get the image resources for the selected major.
     * @param major The selected major string.
     * @return An array with two image resource IDs.
     */
    private int[] getImageResourcesForMajor(String major) {
        // Return an array with two image resource IDs.
        if (major.equals("Computer Science")) {
            return new int[]{R.drawable.cs_details, R.drawable.cs_overview};
        } else if (major.equals("Genetics")) {
            return new int[]{R.drawable.genetics_details, R.drawable.genetics_overview};
        } else if (major.equals("Economy")) {
            return new int[]{R.drawable.economy_details, R.drawable.economy_overview};
        } else if (major.equals("Chemistry")) {
            return new int[]{R.drawable.chemistry_details, R.drawable.chemistry_overview};
        } else if (major.equals("Mathematics")) {
            return new int[]{R.drawable.mathematics_details, R.drawable.mathematics_overview};
        } else if (major.equals("Physics")) {
            return new int[]{R.drawable.physics_details, R.drawable.physics_overview};
        } // if
        return new int[]{R.drawable.default_image, R.drawable.default_image};
    } // getImageResourcesForMajor

    /**
     * Read a text file from raw resources.
     * @param resId The resource ID of the text file.
     * @return The content of the text file as a string.
     */
    private String readRawTextFile(int resId) {
        InputStream inputStream = getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            // Read each line of the text file and append it to the StringBuilder.
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            } // while
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the reader and the input stream to free up resources
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } // try-catch
        } // try-catch
        return stringBuilder.toString();
    } // readRawTextFile

} // DetailFragment

