package fjbatresv.test.nfcreader.main.DI;

import javax.inject.Singleton;

import dagger.Component;
import fjbatresv.test.nfcreader.NFCReaderApplicationModule;
import fjbatresv.test.nfcreader.libs.DI.LibsModule;
import fjbatresv.test.nfcreader.main.ui.MainActivity;

/**
 * Created by javie_000 on 8/17/2016.
 */
@Singleton
@Component(modules = {LibsModule.class, NFCReaderApplicationModule.class, MainModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
