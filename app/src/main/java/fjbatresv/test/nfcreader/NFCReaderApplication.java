package fjbatresv.test.nfcreader;

import android.app.Application;

import fjbatresv.test.nfcreader.libs.DI.LibsModule;

import fjbatresv.test.nfcreader.main.DI.DaggerMainComponent;
import fjbatresv.test.nfcreader.main.DI.MainComponent;
import fjbatresv.test.nfcreader.main.DI.MainModule;
import fjbatresv.test.nfcreader.main.ui.MainView;

/**
 * Created by javie_000 on 8/17/2016.
 */
public class NFCReaderApplication extends Application{

    private LibsModule libsModule;
    private NFCReaderApplicationModule nfcReaderApplicationModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initModule();
    }

    private void initModule() {
        nfcReaderApplicationModule = new NFCReaderApplicationModule(this);
        libsModule = new LibsModule();
    }

    public MainComponent getMainComponent(MainView view) {
        return DaggerMainComponent.builder()
                .nFCReaderApplicationModule(nfcReaderApplicationModule)
                .libsModule(libsModule)
                .mainModule(new MainModule(view))
                .build();
    }
}
