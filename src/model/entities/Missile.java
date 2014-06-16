package model.entities;

import data.Constants;
import data.Publisher;
import model.GameEndedException;
import model.entities.doodle.Doodle;
import view.EntityType;
import view.OutputEntity;
import view.OutputEntityImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 16.06.14
 * Time: 22:55
 */
public class Missile implements Movable, Destroyable, Publisher {

    private final int x;
    private int y;
    private boolean destroyed;

    private final Set<Subscriber> subscribers;

    public Missile(final Subscriber subscriber) {
        x = Doodle.getInstance().getX();
        y = Doodle.getInstance().getH() + (Doodle.getInstance().getFullHeight() / 2);
        destroyed = false;
        subscribers = new HashSet<>();
        subscribers.add(subscriber);
    }

    @Override
    public void move() throws GameEndedException {
        y++;
    }

    public void decHeight(final int dh) {
        y -= dh;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        if (y >= Constants.playHeight) {
            destroyed = true;
        }
        return destroyed;
    }

    public OutputEntity getOut() {
        return new OutputEntityImpl(destroyed ? EntityType.DESTROYED : EntityType.MISSILE,
                x - Constants.missileWidth / 2,
                Constants.playHeight - (y + Constants.missileHeight / 2),
                Constants.missileWidth,
                Constants.missileHeight);
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
}
