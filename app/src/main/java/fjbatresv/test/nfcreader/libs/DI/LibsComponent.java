package fjbatresv.test.nfcreader.libs.DI;

import javax.inject.Singleton;

import dagger.Component;
import fjbatresv.test.nfcreader.NFCReaderApplicationModule;

/**
 * Created by javie_000 on 8/17/2016.
 */
@Singleton
@Component(modules = {LibsModule.class, NFCReaderApplicationModule.class})
public interface LibsComponent {
}
