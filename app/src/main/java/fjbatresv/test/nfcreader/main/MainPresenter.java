package fjbatresv.test.nfcreader.main;

import android.content.Context;

import fjbatresv.test.nfcreader.main.events.MainEvent;

/**
 * Created by javie_000 on 8/17/2016.
 */
public interface MainPresenter {
    void onCreate();
    void onDestroy();
    void readNfc(Context context);
    void onEventMainThread(MainEvent event);
}
