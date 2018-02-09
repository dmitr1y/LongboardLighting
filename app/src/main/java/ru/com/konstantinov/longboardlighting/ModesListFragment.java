package ru.com.konstantinov.longboardlighting;

import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import ru.com.konstantinov.longboardlighting.dummy.TestConnector;


public class ModesListFragment extends ListFragment {

    public ModesListFragment() {
        // Required empty public constructor
    }

    String data[] = new String[] { "Mode 1", "Mode 2", "Mode 3", "Mode 4" };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        String modes[]=getNames(LedMode.class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, modes);
        setListAdapter(adapter);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Do your stuff..
        Toast.makeText(getActivity(),"clicked to "+Integer.toString(position),Toast.LENGTH_SHORT).show();
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }
}
