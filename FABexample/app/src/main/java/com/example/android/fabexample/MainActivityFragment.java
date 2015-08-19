package com.example.android.fabexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        final FloatingActionsMenu leftLabels = (FloatingActionsMenu) v.findViewById(R.id.left_labels);
        final com.getbase.floatingactionbutton.FloatingActionButton deleteButton = new com.getbase.floatingactionbutton.FloatingActionButton(getActivity());
        deleteButton.setTitle("Remove Me");
        deleteButton.setSize(FloatingActionButton.SIZE_MINI);
        deleteButton.setIcon(R.drawable.ic_delete_black_24dp);
        leftLabels.addButton(deleteButton);

        final FloatingActionButton polyLine = (FloatingActionButton)v.findViewById(R.id.polyline);
        polyLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Pressed Polyline", Toast.LENGTH_SHORT ).show();

            }
        });

        final FloatingActionButton newNode = (FloatingActionButton)v.findViewById(R.id.node);
        newNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Pressed New Node", Toast.LENGTH_SHORT ).show();

                Intent intent = new Intent(getActivity(), AddAsset.class);
                startActivity(intent);


            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButton.setVisibility(deleteButton.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });



        return v;
    }
}
