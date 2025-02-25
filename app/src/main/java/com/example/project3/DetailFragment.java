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

public class DetailFragment extends Fragment {
    private static final String ARG_MAJOR = "major";
    private String major;

    public static DetailFragment newInstance(String major) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MAJOR, major);
        fragment.setArguments(args);
        return fragment;
    } // newInstance

    public DetailFragment() {
        // Required empty public constructor.
    } // DetailFragment constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            major = getArguments().getString(ARG_MAJOR);
        } // if
    } // onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
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

    private int getRawResourceForMajor(String major) {
        // Return the raw resource id containing combined information.
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

    private String readRawTextFile(int resId) {
        InputStream inputStream = getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            } // while
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } // try-catch
        } // try-catch
        return stringBuilder.toString();
    } // readRawTextFile

} // DetailFragment

