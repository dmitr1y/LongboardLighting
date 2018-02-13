package ru.com.konstantinov.longboardlighting;

import android.app.ListFragment;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ru.com.konstantinov.longboardlighting.interfaces.DeviceFinder;


public class DevicesListFragment extends ListFragment {

    private BluetoothDevice[] foundedDeviceArray;
    private DeviceFinder deviceFinder;

    public DevicesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.deviceFinder=((MainActivity)getActivity()).getFinder();

        Set<BluetoothDevice> foundDevices = this.deviceFinder.getBondedDevices();
        this.foundedDeviceArray = foundDevices.toArray(new BluetoothDevice[foundDevices.size()]);

        List<String> deviceList = new ArrayList<>();

        if (foundDevices.size() > 0) {

            for (BluetoothDevice aFoundedDeviceArray : this.foundedDeviceArray) {
                deviceList.add(aFoundedDeviceArray.getName() + "\n" + aFoundedDeviceArray.getAddress());
            }
        }

        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, deviceList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Do your stuff..
//        TextView textView = (TextView) v;
//        String itemText = textView.getText().toString(); // получаем текст нажатого элемента
        Toast.makeText(getActivity(), getText(R.string.connecting_to)+": " + this.foundedDeviceArray[position].getName(), Toast.LENGTH_SHORT).show();
        BluetoothSocket socket = this.deviceFinder.ConnectToDevice(this.foundedDeviceArray[position]);
        ((MainActivity) getActivity()).setBluetoothSocket(socket);
    }

}
