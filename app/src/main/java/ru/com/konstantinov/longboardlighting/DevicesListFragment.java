package ru.com.konstantinov.longboardlighting;

import android.app.ListFragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ru.com.konstantinov.longboardlighting.Connector.DeviceFinder;


public class DevicesListFragment extends ListFragment {

    public DevicesListFragment() {
        // Required empty public constructor
    }

    String data[] = new String[] { "Device 1", "Device 2", "Device 3", "Device 4" };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        DeviceFinder deviceFinder = new DeviceFinder() {
//            @Override
//            public Set<BluetoothDevice> getBondedDevices() {
//                return null;
//            }
//
//            @Override
//            public boolean ConnectToDevice(@NotNull BluetoothDevice device) {
//                return false;
//            }
//        };
//
//        Set<BluetoothDevice> foundDevices=deviceFinder.getBondedDevices();
//        List<String> deviceList = new ArrayList<String>();
//
//        for(BluetoothDevice bt : foundDevices)
//            deviceList.add(bt.getName());


        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

}
