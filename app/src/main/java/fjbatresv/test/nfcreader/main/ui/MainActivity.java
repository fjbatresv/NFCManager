package fjbatresv.test.nfcreader.main.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fjbatresv.test.nfcreader.NFCReaderApplication;
import fjbatresv.test.nfcreader.R;
import fjbatresv.test.nfcreader.main.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView{
    @Bind(R.id.readNfc)
    Button readNfc;
    @Bind(R.id.writeNfc)
    Button writeNfc;
    @Bind(R.id.loading)
    CardView loading;
    @Bind(R.id.container)
    RelativeLayout container;
    @Bind(R.id.tagMsg)
    EditText tagMsg;
    @Bind(R.id.msgLbl)
    TextView msgLbl;

    @Inject
    public MainPresenter presenter;

    private NFCReaderApplication app;
    private PendingIntent mPendingIntent;
    private NfcAdapter mAdapter;
    private boolean leer = true;
    IntentFilter[] intentFilters = new IntentFilter[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        app = (NFCReaderApplication) getApplication();
        injection();
        presenter.onCreate();
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter != null){
            this.mAdapter.enableForegroundDispatch(this, mPendingIntent, intentFilters, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null){
            mAdapter.disableForegroundDispatch(this);
        }
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void injection(){
        app.getMainComponent(this).inject(this);
    }

    @OnClick(R.id.readNfc)
    public void readNfc(){
        this.leer = true;
        presenter.readNfc(this);
    }

    @OnClick(R.id.writeNfc)
    public void writeNfc(){
        this.leer = false;
        presenter.readNfc(this);
    }

    @Override
    public void reading(Boolean opt) {
        int visible = View.VISIBLE;
        int inVisible = View.GONE;
        if(opt){
            readNfc.setVisibility(inVisible);
            writeNfc.setVisibility(inVisible);
            tagMsg.setVisibility(inVisible);
            msgLbl.setVisibility(inVisible);
            loading.setVisibility(visible);
        }else{
            readNfc.setVisibility(visible);
            writeNfc.setVisibility(visible);
            tagMsg.setVisibility(visible);
            msgLbl.setVisibility(visible);
            loading.setVisibility(inVisible);
        }

    }

    @Override
    public void result(String result) {
        Snackbar.make(container, result, Snackbar.LENGTH_LONG).show();
        //Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAdapterNfc(NfcAdapter mAdapter) {
        this.mAdapter = mAdapter;
        Log.e("activity", "Adaptador obtenido");
        this.mAdapter.enableForegroundDispatch(this, mPendingIntent, intentFilters, null);
        //result("Adaptador");
    }

    @OnClick(R.id.cancel)
    public void cancel(){
        reading(false);
        this.mAdapter = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e("activity", "new Intent | " + intent.toString());
        super.onNewIntent(intent);
        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)){
            if (this.leer){
                Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

                if(parcelables != null && parcelables.length > 0)
                {
                    readMessage((NdefMessage) parcelables[0]);
                }else{
                    result("No se encontro mensaje NDEF");
                    this.mAdapter = null;
                }
            }else{
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                NdefMessage ndefMessage = createMessage(tagMsg.getText().toString());
                writeNdefMessage(tag, ndefMessage);
                tagMsg.setText(null);
            }
        }else{
            result("No se encontro extra tag");
        }
        reading(false);
        this.mAdapter = null;
    }

    private void readMessage(NdefMessage ndefMessage) {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length>0){

            NdefRecord ndefRecord = ndefRecords[0];

            String tagContent = getTextFromNdefRecord(ndefRecord);
            result("TAG LEIDA");
            tagMsg.setText(tagContent);
        }else {
            Log.e("readMessage", "No NDEF records found!");
        }
        this.mAdapter = null;
    }

    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = "UTF-8";
            if (!((payload[0] & 128) == 0)){
                textEncoding = "UTF-16";
            }
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

    private void formatTag(Tag tag, NdefMessage message){
        try{
            NdefFormatable ndefFormatable = NdefFormatable.get(tag);
            if (ndefFormatable == null){
                Log.e("format", "Tag is not ndef formatable");
            }
            ndefFormatable.connect();
            ndefFormatable.format(message);
            ndefFormatable.close();
        }catch(Exception ex){
            Log.e("format", "Tag is not ndef formatable | " + ex.toString());
        }
    }

    public void writeNdefMessage(Tag tag, NdefMessage ndefMessage){
        try{
            if (tag == null){
                result("El tag no puede ser null");
                return;
            }
            Ndef ndef = Ndef.get(tag);
            if (ndef == null){
                formatTag(tag, ndefMessage);
            }else{
                ndef.connect();
                if (!ndef.isWritable()){
                    ndef.close();
                    Log.e("write", "No se puede escribir");
                    return;
                }
                ndef.writeNdefMessage(ndefMessage);
                ndef.close();
                result("Tag escrita");
            }
        }catch(Exception ex){
            Log.e("write", "Tag is not ndef formatable | " + ex.toString());
        }
        this.mAdapter = null;
    }

    private NdefMessage createMessage(String message){
        NdefRecord record = createTextRecord(message);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{record});
        return ndefMessage;
    }

    private NdefRecord createTextRecord(String message) {
        try{
            byte[] language;
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");
            final byte[] text = message.getBytes("UTF-8");
            final int languageSize = language.length;
            final int textLength = text.length;
            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);
            payload.write((byte) (languageSize & 0x1F));
            payload.write(language, 0, languageSize);
            payload.write(text, 0, textLength);
            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
        } catch (Exception e) {
            Log.e("createTextRecord", e.toString());
        }
        return null;
    }

}
