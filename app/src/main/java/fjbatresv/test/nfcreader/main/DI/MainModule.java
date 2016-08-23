package fjbatresv.test.nfcreader.main.DI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fjbatresv.test.nfcreader.libs.base.EventBus;
import fjbatresv.test.nfcreader.main.MainInteractor;
import fjbatresv.test.nfcreader.main.MainInteractorImplementation;
import fjbatresv.test.nfcreader.main.MainPresenter;
import fjbatresv.test.nfcreader.main.MainPresenterImplementation;
import fjbatresv.test.nfcreader.main.MainRepository;
import fjbatresv.test.nfcreader.main.MainRepositoryImplementation;
import fjbatresv.test.nfcreader.main.ui.MainView;

/**
 * Created by javie_000 on 8/17/2016.
 */
@Module
public class MainModule {
    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    public MainView providesMainView() {
        return this.view;
    }

    @Provides @Singleton
    public MainPresenter providesMainPresenter(MainInteractor interactor, MainView view, EventBus bus) {
        return new MainPresenterImplementation(interactor, view, bus);
    }

    @Provides @Singleton
    public MainInteractor providesMainInteractor(MainRepository repository) {
        return new MainInteractorImplementation(repository);
    }

    @Provides @Singleton
    public MainRepository providesMainRepository(EventBus eventBus) {
        return new MainRepositoryImplementation(eventBus);
    }


}
