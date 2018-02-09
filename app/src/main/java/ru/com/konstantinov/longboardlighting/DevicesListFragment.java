package ru.com.konstantinov.longboardlighting;

import android.app.ListFragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ru.com.konstantinov.longboardlighting.Connector.DeviceFinder;
import ru.com.konstantinov.longboardlighting.dummy.TestFinder;


public class DevicesListFragment extends ListFragment {

    private BluetoothDevice[] foundedDeviceArray;

    public DevicesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        DeviceFinder deviceFinder = new TestFinder();

        Set<BluetoothDevice> foundDevices = deviceFinder.getBondedDevices();
        this.foundedDeviceArray = foundDevices.toArray(new BluetoothDevice[foundDevices.size()]);

        List<String> deviceList = new ArrayList<String>();

        if (foundDevices.size() > 0) {

            for (int i = 0; i < this.foundedDeviceArray.length; i++) {
                deviceList.add(this.foundedDeviceArray[i].getName() + "\n" + this.foundedDeviceArray[i].getAddress());
            }
        }

        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, deviceList);
        setListAdapter(adapter);
    }

}
