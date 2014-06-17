package model;

import data.Publisher;
import model.play_field.PlayGround;
import view.EntityType;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 06.06.14
 * Time: 19:02
 */
public class ScoreCounter implements Publisher {
    Set<Subscriber> subscribers = new HashSet<>();
    private static final ScoreCounter ourInstance = new ScoreCounter();

    private final Map<String, Integer> scoreInfo = new HashMap<>();

    public static ScoreCounter getInstance() {
        return ourInstance;
    }

    private int fullScore = 0;

    private ScoreCounter() {
    }

    public void eventHappened(final EntityType cause, final ModelEvent event) {
        if (cause == EntityType.ROCKET_BLOCK && event == ModelEvent.COLLISION) {
            fullScore += 5;
            if (!scoreInfo.containsKey("Очки за прыжки от ракетных блоков: ")) {
                scoreInfo.put("Очки за прыжки от ракетных блоков: ", 5);
            } else {
                scoreInfo.put("Очки за прыжки от ракетных блоков: ", scoreInfo.get("Очки за прыжки от ракетных блоков: ") + 5);
            }

            notifySubscribers(Event.SCORE_CHANGED);
            return;
        }
        if (cause == EntityType.STANDARD_BLOCK && event == ModelEvent.COLLISION) {
            fullScore += 1;
            if (!scoreInfo.containsKey("Очки за прыжки от обычных блоков: ")) {
                scoreInfo.put("Очки за прыжки от обычных блоков: ", 1);
            } else {
                scoreInfo.put("Очки за прыжки от обычных блоков: ", scoreInfo.get("Очки за прыжки от обычных блоков: ") + 1);
            }
            notifySubscribers(Event.SCORE_CHANGED);
            return;
        }
        if (cause == EntityType.BLUE_GEL_BLOCK && event == ModelEvent.COLLISION) {
            fullScore += 2;
            if (!scoreInfo.containsKey("Очки за прыжки от блоков из синего геля: ")) {
                scoreInfo.put("Очки за прыжки от блоков из синего геля: ", 2);
            } else {
                scoreInfo.put("Очки за прыжки от блоков из синего геля: ", scoreInfo.get("Очки за прыжки от блоков из синего геля: ") + 2);
            }
            notifySubscribers(Event.SCORE_CHANGED);
            return;
        }
        if (cause == null && event == ModelEvent.LINE_ADDED) {
            notifySubscribers(Event.SCORE_CHANGED);
        }
        if (cause == EntityType.COROVAN && event == ModelEvent.DESTROYED) {
            if (!scoreInfo.containsKey("Номадов побеждено: ")) {
                scoreInfo.put("Номадов побеждено: ", 1);
            } else {
                scoreInfo.put("Номадов побеждено: ", scoreInfo.get("Номадов побеждено: ") + 1);
            }
            notifySubscribers(Event.SCORE_CHANGED);
            return;
        }

    }

    public ScoreInfo getScore() {
        return new ScoreInfo(scoreInfo, fullScore);
    }

    @Override
    public void addSubscriber(final Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(final Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(final Event event) {
        for (final Subscriber subscriber : subscribers) {
            subscriber.eventHappened(event);
        }
    }

    public void restart() {
        fullScore = 0;
        scoreInfo.clear();
    }

    public enum ModelEvent {
        COLLISION, LINE_ADDED, DESTROYED;
    }

    public class ScoreInfo implements Iterable<String> {

        private final Map<String, Integer> score;
        private final int fullScore;

        public ScoreInfo(final Map<String, Integer> score, final int fullScore) {
            this.score = score;
            this.fullScore = fullScore + PlayGround.getInstance().getLineByHCoordinate(0).getAbsoluteHeight() / 10;
        }


        @Override
        public Iterator<String> iterator() {
            final List<String> res = new ArrayList<>();
            res.add("Результат: " + fullScore);
            for (final String key : score.keySet()) {
                res.add(key + score.get(key));
            }
            ;
            return res.iterator();
        }

        public int getFullScore() {
            return fullScore;
        }
    }
}

