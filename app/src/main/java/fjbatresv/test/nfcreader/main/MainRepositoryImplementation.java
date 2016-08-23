package fjbatresv.test.nfcreader.main;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.Log;

import fjbatresv.test.nfcreader.R;
import fjbatresv.test.nfcreader.libs.base.EventBus;
import fjbatresv.test.nfcreader.main.events.MainEvent;
import fjbatresv.test.nfcreader.main.ui.MainActivity;

/**
 * Created by javie_000 on 8/17/2016.
 */
public class MainRepositoryImplementation implements MainRepository{
    private EventBus bus;
    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;

    public MainRepositoryImplementation(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void readNfc(Context context) {
        mAdapter = NfcAdapter.getDefaultAdapter(context);
        if (mAdapter == null){
            Log.e("repo", "null adapter");
            bus.post(new MainEvent(1, "Tu dispositivo no soporta NFC"));
        }else {
            Log.e("repo", " adapter = " + mAdapter.toString());
            bus.post(new MainEvent(1, mAdapter));
        }
    }
}
