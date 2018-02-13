package ru.com.konstantinov.longboardlighting;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


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
        TextView textView = (TextView) v;
        String itemText = textView.getText().toString(); // получаем текст нажатого элемента
        Toast.makeText(getActivity(), "Выбран: " + itemText, Toast.LENGTH_SHORT).show();
        ((MainActivity) getActivity()).getConnector().setMode(LedMode.getModeByName(itemText));
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }
}
