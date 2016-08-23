package fjbatresv.test.nfcreader.libs.DI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fjbatresv.test.nfcreader.libs.GreenRobotEventBus;
import fjbatresv.test.nfcreader.libs.base.EventBus;

/**
 * Created by javie_000 on 8/17/2016.
 */
@Module
public class LibsModule {

    public LibsModule() {
    }

    @Singleton
    @Provides
    public EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus) {
        return new GreenRobotEventBus(eventBus);
    }

    @Singleton
    @Provides
    public org.greenrobot.eventbus.EventBus providesLibraryEventBus() {
        return org.greenrobot.eventbus.EventBus.getDefault();

    }
}
