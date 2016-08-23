package fjbatresv.test.nfcreader.main;

import android.content.Context;

/**
 * Created by javie_000 on 8/17/2016.
 */
public class MainInteractorImplementation implements MainInteractor {
    private MainRepository repo;

    public MainInteractorImplementation(MainRepository repo) {
        this.repo = repo;
    }

    @Override
    public void readNfc(Context context) {
        repo.readNfc(context);
    }
}
