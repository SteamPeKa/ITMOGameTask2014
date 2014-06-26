package model.entities.enemies;

import data.Publisher;
import model.GameEndedException;
import view.EntityType;
import view.OutputEntity;
import view.OutputEntityImpl;

import java.util.HashSet;
import java.util.Set;

import static data.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 17.06.14
 * Time: 15:16
 */
public class Corovan implements Enemy, Publisher {

    private int x;
    private int y;
    private final Set<Subscriber> subscribers;
    private boolean destroyed;

    public Corovan(final Subscriber model, final int initialY) {
        x = -corovanHalfWidth;
        y = initialY;
        subscribers = new HashSet<>();
        subscribers.add(model);
        destroyed = false;
    }


    @Override
    public OutputEntity getOut() {
        return new OutputEntityImpl(destroyed ? EntityType.DESTROYED : EntityType.COROVAN, x - corovanHalfWidth, playHeight - (y + corovanHalfHeight), corovanHalfWidth * 2, corovanHalfHeight * 2);
    }

    @Override
    public void decH(final int dh) {
        y -= dh;
        if (y <= 0) {
            destroy();
        }
    }

    @Override
    public EntityType getType() {
        return EntityType.COROVAN;
    }

    @Override
    public void collideWithDoodle() {
        notifySubscribers(Event.FAIL);
    }

    @Override
    public boolean collideWithMissile() {
        if (!destroyed) {
            destroyed = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public HitBox getHitBox() {
        return new HitBox() {
            private final int leftX = x - corovanHalfWidth;
            private final int rightX = x + corovanHalfWidth;
            private final int bottomY = y - corovanHalfHeight;
            private final int topY = y + corovanHalfHeight;

            @Override
            public int getX() {
                if (leftX < 0) {
                    return 0;
                }
                if (leftX > playWidth) {
                    return playWidth;
                }
                return leftX;
            }

            @Override
            public int getY() {
                if (bottomY < 0) {
                    return 0;
                }
                if (bottomY > playHeight) {
                    return playHeight;
                }
                return bottomY;

            }

            @Override
            public int getWidth() {
                if (rightX - getX() < 0) {
                    throw new InternalError();
                }
                return (rightX - getX());
            }

            @Override
            public int getHeight() {
                if (topY - getY() <= 0) {
                    throw new InternalError();
                }
                return (topY - getY());
            }
        };
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void move() throws GameEndedException {
        x += corovanSpeed;
        if (x >= playWidth + corovanHalfWidth) {
            destroy();
        }
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
