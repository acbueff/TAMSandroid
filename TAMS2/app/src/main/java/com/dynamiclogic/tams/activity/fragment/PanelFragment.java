package com.dynamiclogic.tams.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dynamiclogic.tams.R;

public class PanelFragment extends Fragment {

    private static final String TAG = MyAdapter.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    private ListView mListView;
    private String[] favoriteTVShows = {"the office", "game of thrones", "everybody loves raymond"};
    private ListAdapter mListAdapter;

    public PanelFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_panel, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();


        Log.d(TAG, "getView().getId() = " + getActivity().findViewById(R.id.fragment_panel));

        mListAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, favoriteTVShows);
        mListView = (ListView) getView().findViewById(R.id.listView);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, String.format("at position (%d+1) : %s ", position,
                        String.valueOf(mListAdapter.getItem(position))));

            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    private class MyAdapter extends BaseAdapter {

        private final String TAG = MyAdapter.class.getSimpleName();

        public MyAdapter() {
            Log.d(TAG, "MyAdapter()");
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView()");
            return null;
        }
    }

}
