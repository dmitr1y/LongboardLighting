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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import ru.com.konstantinov.longboardlighting.dummy.TestConnector;
import ru.com.konstantinov.longboardlighting.interfaces.ConnectionInterface;


public class ModesListFragment extends ListFragment {

    public ModesListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        String modes[] = getNames(LedMode.class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, modes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Do your stuff..
        TextView textView = (TextView) v;
        String itemText = textView.getText().toString(); // получаем текст нажатого элемента
        Toast.makeText(getActivity(), "выбран: " + Integer.toString(position) + " - " + LedMode.getModeByName(itemText).getCode(), Toast.LENGTH_SHORT).show();
        ConnectionInterface connector = new TestConnector();
        connector.setMode(LedMode.getModeByName(itemText));
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }
}
