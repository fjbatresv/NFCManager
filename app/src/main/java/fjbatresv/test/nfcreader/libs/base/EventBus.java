package fjbatresv.test.nfcreader.libs.base;

/**
 * Created by javie_000 on 8/17/2016.
 */
public interface EventBus {
    void register(Object subscriber);
    void unRegister(Object subscriber);
    void post(Object subscriber);
}
