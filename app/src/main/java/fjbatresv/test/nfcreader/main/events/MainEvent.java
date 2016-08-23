package fjbatresv.test.nfcreader.main.events;

import android.nfc.NfcAdapter;

/**
 * Created by javie_000 on 8/17/2016.
 */
public class MainEvent {
    private int tipo;
    private String error;
    private NfcAdapter mAdapter;

    public static final int TIPO_NFC = 1;
    public static final int TIPO_QRCODE = 2;

    public MainEvent() {
    }

    public MainEvent(int tipo, String error) {
        this.tipo = tipo;
        this.error = error;
    }

    public MainEvent(int tipo, NfcAdapter mAdapter) {
        this.tipo = tipo;
        this.mAdapter = mAdapter;
    }

    public NfcAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(NfcAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
