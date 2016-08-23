package fjbatresv.test.nfcreader.libs;

import fjbatresv.test.nfcreader.libs.base.EventBus;

/**
 * Created by javie_000 on 8/17/2016.
 */
public class GreenRobotEventBus implements EventBus {
    private org.greenrobot.eventbus.EventBus bus;

    public GreenRobotEventBus(org.greenrobot.eventbus.EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void register(Object subscriber) {
        bus.register(subscriber);
    }

    @Override
    public void unRegister(Object subscriber) {
        bus.unregister(subscriber);
    }

    @Override
    public void post(Object subscriber) {
        bus.post(subscriber);
    }
}
