package fjbatresv.test.nfcreader.main;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import fjbatresv.test.nfcreader.libs.base.EventBus;
import fjbatresv.test.nfcreader.main.events.MainEvent;
import fjbatresv.test.nfcreader.main.ui.MainView;

/**
 * Created by javie_000 on 8/17/2016.
 */
public class MainPresenterImplementation implements MainPresenter {
    private MainInteractor interactor;
    private MainView view;
    private EventBus bus;

    public MainPresenterImplementation(MainInteractor interactor, MainView view, EventBus bus) {
        this.interactor = interactor;
        this.view = view;
        this.bus = bus;
    }

    @Override
    public void onCreate() {
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        bus.unRegister(this);
    }

    @Override
    public void readNfc(Context context) {
        view.reading(true);
        interactor.readNfc(context);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MainEvent event) {
        Log.e("presenter", "Event Main Thread");
        if (event.getError() == null) {
            Log.e("presenter", "Event Main Thread sin error");
            switch (event.getTipo()) {
                case MainEvent.TIPO_NFC:
                    Log.e("presenter", "Event Main Thread sin error nfc");
                    view.setAdapterNfc(event.getmAdapter());
                    break;
                case MainEvent.TIPO_QRCODE:
                    break;
            }
        }else{
            Log.e("presenter", "Event Main Thread con error | " + event.getError());
            //view.result(event.getError());
        }
    }
}
