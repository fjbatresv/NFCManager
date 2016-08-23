package fjbatresv.test.nfcreader.main.ui;

import android.nfc.NfcAdapter;

/**
 * Created by javie_000 on 8/17/2016.
 */
public interface MainView {
    void reading(Boolean opt);
    void result(String result);
    void setAdapterNfc(NfcAdapter mAdapter);
}
