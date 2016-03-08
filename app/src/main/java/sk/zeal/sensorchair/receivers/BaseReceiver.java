package sk.zeal.sensorchair.receivers;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * Created by denis.valdman on 8. 3. 2016.
 */
public abstract class BaseReceiver extends BroadcastReceiver{

    abstract protected void initReceiver();

    abstract public IntentFilter getIntentFilter();
}
