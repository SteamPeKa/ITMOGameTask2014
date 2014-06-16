package model.entities.doodle;

import data.Publisher;
import view.EntityType;
import model.GameEndedException;
import model.entities.Movable;
import model.entities.doodle.move_tackics.MoveTactic;

import java.util.HashSet;
import java.util.Set;

import static data.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 16:57
 */
public class Doodle implements Movable, Publisher {

    private Coordinates coordinates;

    private MoveTactic moveTactic;

    private final Set<Subscriber> subscribers;

    private Orientation lastOrientation = Orientation.LEFT;


    private static final Doodle ourInstance = new Doodle(playWidth / 2, actualOneLineHeight * 5);

    public static Doodle getInstance() {
        return ourInstance;
    }

    private Doodle(final int x, final int h) {
        coordinates = new Coordinates(x, h);
        subscribers = new HashSet<>();
    }

    public int getX() {
        return coordinates.getX();
    }

    public int getH() {
        return coordinates.getH();
    }

    public int getFullHeight() {
        return doodleFullHeight;
    }


    public MoveTactic getMoveTactic() {
        return moveTactic;
    }

    public void setMoveTactic(final MoveTactic newMoveTactic) {
        moveTactic = newMoveTactic;
    }

    public int getHalfOfWidth() {
        return doodleHalfWidth;
    }

    public int getPrevH() {
        return coordinates.prevH;
    }

    @Override
    public void move() throws GameEndedException {
        lastOrientation = moveTactic.getXOrientation() == 0 ? lastOrientation : moveTactic.getXOrientation() > 0 ? Orientation.RIGHT : Orientation.LEFT;
        moveTactic.move(coordinates);
    }

    public boolean isFalling() {
        return moveTactic.isFalling();
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
        coordinates = new Coordinates(playWidth / 2, actualOneLineHeight * 5);
    }

    public class Coordinates {
        private int x;
        private int h;

        private int prevH;


        public Coordinates(final int x, final int h) {
            this.x = x;
            this.h = h;
            prevH = h;
        }

        public void changeH(final int dH) {
            prevH = h;
            h += dH;
            Doodle.getInstance().notifySubscribers(Event.MOVED);

        }

        public void changeX(final int dX) {
            x += dX;
            Doodle.getInstance().notifySubscribers(Event.MOVED);
        }

        public int getX() {
            return x;
        }

        public int getH() {
            return h;
        }

        public void setX(final int newX) {
            x = newX;
        }

    }

    public void decHeight(final int dh) {
        coordinates.h -= dh;
    }

    public enum Orientation {
        LEFT, RIGHT;
    }

    public EntityType getType() {
        if (isFalling()) {
            if (moveTactic.getXOrientation() > 0) {
                return EntityType.DOODLE_R_F;
            }
            if (moveTactic.getXOrientation() < 0) {
                return EntityType.DOODLE_L_F;
            }
            return lastOrientation.equals(Orientation.LEFT) ? EntityType.DOODLE_L_F : EntityType.DOODLE_R_F;
        } else {
            if (moveTactic.getXOrientation() > 0) {
                return EntityType.DOODLE_R_J;
            }
            if (moveTactic.getXOrientation() < 0) {
                return EntityType.DOODLE_L_J;
            }
            return lastOrientation.equals(Orientation.LEFT) ? EntityType.DOODLE_L_J : EntityType.DOODLE_R_J;
        }
    }
}

