package sk.zeal.sensorchair.receivers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import java.util.ArrayList;

import sk.zeal.sensorchair.listeners.BTDiscoveryListener;
import sk.zeal.sensorchair.model.BTDeviceAndRSSIPair;
import sk.zeal.sensorchair.utils.Logger;

/**
 * Created by denis.valdman on 8. 3. 2016.
 */
public class BTDiscoverReceiver extends BaseReceiver{

    private BTDiscoveryListener listener;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BTDeviceAndRSSIPair> deviceList;
    private String nameFilter = "";

    public BTDiscoverReceiver(BTDiscoveryListener listener, String nameFilter){
        setListener(listener);
        this.nameFilter = nameFilter;
        initReceiver();
    }



    public BTDiscoveryListener getListener() {
        return listener;
    }

    public void setListener(BTDiscoveryListener listener) {
        this.listener = listener;
    }

    private void addDevice(BluetoothDevice device, int rssi){
        if(deviceList == null)
            deviceList = new ArrayList<BTDeviceAndRSSIPair>();
        BTDeviceAndRSSIPair pair = new BTDeviceAndRSSIPair(device, rssi);
        deviceList.add(pair);
        listener.onDeviceDiscovered(pair);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Logger.i("receiver got intent: " + action);
        // When discovery finds a device
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            onDeviceFound(intent);
        }  else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            onStateChanged(intent);
        } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            if(listener != null)
                listener.onDiscoveryStarted();
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            if(listener != null)
                listener.onDiscoveryFinished(deviceList);
        }

    }

    private void onDeviceFound(Intent intent){
        // Get the BluetoothDevice object from the Intent
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
        Logger.i("Found device "+device.getName());
        if (device.getName() != null && device.getName().startsWith(nameFilter) ) {
//                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
            addDevice(device, rssi);
//                } else {
//                    int position = newDevicesArrayAdapter.getPosition(device);
//                    newDevicesArrayAdapter.getRssiList().set(position, rssi);
//                    newDevicesArrayAdapter.notifyDataSetChanged();
//                }
        }
    }


    private void onStateChanged(Intent intent){
//  TODO: To be implemented
    }

    private void onCancel(){
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        listener.onDiscoveryFinished(deviceList);
    }

    @Override
    protected void initReceiver(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isDiscovering())
            bluetoothAdapter.cancelDiscovery();
        if(deviceList == null)
            deviceList = new ArrayList<BTDeviceAndRSSIPair>();
        else
            deviceList.clear();
//        addBondedDevices();
        bluetoothAdapter.startDiscovery();
    }

//    TODO: prepared in case we will decide to display bonded devices even if they are not nearby
//    private void addBondedDevices() {
//        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
//        if (bondedDevices.size() > 0) {
//            // Loop through paired devices
//            for (BluetoothDevice device : bondedDevices) {
//                // Add the name and address to an array adapter to show in a ListView
//                if (device.getName().startsWith(nameFilter)) {
//                    deviceList.add(new BTDeviceAndRSSIPair(device,0));
//                }
//            }
//        }
//    }

    @Override
    public IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_UUID);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        return filter;
    }
}
