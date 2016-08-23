package fjbatresv.test.nfcreader;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javie_000 on 8/17/2016.
 */
@Module
public class NFCReaderApplicationModule {
    private NFCReaderApplication app;

    public NFCReaderApplicationModule(NFCReaderApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application providesNFCReaderApplication(){
        return this.app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return this.app.getApplicationContext();
    }

}

