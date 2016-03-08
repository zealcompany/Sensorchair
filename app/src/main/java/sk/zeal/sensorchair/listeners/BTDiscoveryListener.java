package sk.zeal.sensorchair.listeners;



import java.util.ArrayList;

import sk.zeal.sensorchair.model.BTDeviceAndRSSIPair;

/**
 * Created by denis.valdman on 8. 3. 2016.
 */
public interface BTDiscoveryListener {

    void onDiscoveryStarted();

    void onDeviceDiscovered(BTDeviceAndRSSIPair device);

    void onDiscoveryFinished(ArrayList<BTDeviceAndRSSIPair> deviceList);

    void onConnectionChanged(int status);

}
