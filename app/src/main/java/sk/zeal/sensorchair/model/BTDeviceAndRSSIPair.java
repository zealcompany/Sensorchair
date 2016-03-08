package sk.zeal.sensorchair.model;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by denis.valdman on 8. 3. 2016.
 */
public class BTDeviceAndRSSIPair implements Parcelable {

    private BluetoothDevice device;
    private int rssi;

    public BTDeviceAndRSSIPair(BluetoothDevice device, int rssi){
        setDevice(device);
        setRssi(rssi);
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }



    protected BTDeviceAndRSSIPair(Parcel in) {
        device = (BluetoothDevice) in.readValue(BluetoothDevice.class.getClassLoader());
        rssi = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(device);
        dest.writeInt(rssi);
    }

    @SuppressWarnings("unused")
    public static final Creator<BTDeviceAndRSSIPair> CREATOR = new Creator<BTDeviceAndRSSIPair>() {
        @Override
        public BTDeviceAndRSSIPair createFromParcel(Parcel in) {
            return new BTDeviceAndRSSIPair(in);
        }

        @Override
        public BTDeviceAndRSSIPair[] newArray(int size) {
            return new BTDeviceAndRSSIPair[size];
        }
    };
}