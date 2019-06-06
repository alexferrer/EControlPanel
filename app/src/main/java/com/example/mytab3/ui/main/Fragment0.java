package com.example.mytab3.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mytab3.R;

public class Fragment0 extends Fragment {
    private static final String TAG = "Frag0";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PageViewModel model;

    public Fragment0() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment0 newInstance(String param1, String param2) {
        Fragment0 fragment = new Fragment0();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(PageViewModel.class);
        Log.e(TAG, model.toString());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        model.setIndex(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment0, container, false);
        final TextView tv_mAmps = root.findViewById(R.id.tv_mAmps);
        final TextView tv_mVolts = root.findViewById(R.id.tv_mVolts);
        final TextView tv_mWatts = root.findViewById(R.id.tv_mWatts);

        model.getmAmps().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                tv_mAmps.setText(""+i);
                Log.e(TAG,"mAmps="+i);
            }
        });
        model.getmVolts().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                tv_mVolts.setText(""+i);
                Log.e(TAG,"mVolts="+i);
            }
        });
        model.getmWatts().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                tv_mWatts.setText(""+i);
                Log.e(TAG,"mWatts="+i);
            }
        });


        return root;
    }

}
