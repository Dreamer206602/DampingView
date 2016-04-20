package com.tq;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

   private static String s1;
    private static TabFragment sTabFragment;


    public static TabFragment newInstance(String s){
        sTabFragment=new TabFragment();
        s1=s;
        return sTabFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

}
