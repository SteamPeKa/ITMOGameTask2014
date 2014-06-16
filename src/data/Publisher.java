package data;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 21:31
 */
public interface Publisher {

    public void addSubscriber(final Subscriber subscriber);

    public void removeSubscriber(final Subscriber subscriber);

    public void notifySubscribers(Event event);

    public static interface Subscriber {
        public void eventHappened(final Event event);
    }

    public enum Event {
        FAIL, MOVED, APPROXED, LINE_PUSHED, LINE_PUSHING, SCORE_CHANGED, UNPAUSED, RESTARTED, MISSILE_MOVED;
    }

}
