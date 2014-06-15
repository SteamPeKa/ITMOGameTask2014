package model;

import data.Constants;
import data.Publisher;
import model.entities.blocks.Block;
import model.entities.doodle.Doodle;
import model.entities.doodle.move_tackics.Standard;
import model.play_field.PlayGround;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static data.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 21:18
 */
public class GameModel implements Publisher, Publisher.Subscriber {


    private final PlayGround playGround;
    private final Doodle doodle;
    private final ScoreCounter scoreCounter;

    private final Set<Subscriber> subscribers;

    public GameModel() {
        playGround = PlayGround.getInstance();
        doodle = Doodle.getInstance();
        doodle.setMoveTactic(new Standard());
        doodle.addSubscriber(this);
        subscribers = new HashSet<>();
        scoreCounter = ScoreCounter.getInstance();
    }

    public int getFieldWidth() {
        return playWidth;
    }

    public int getFieldHeight() {
        return playHeight;
    }

    public int getLineQuantity() {
        return linesQuantity;
    }

    public int getLineHeight() {
        return playGround.getOneLineHeight();
    }

    public List<Entity> getAllEntities() {
        final List<Entity> entities = new ArrayList<>();
        entities.addAll(playGround.getBlocksAsEntities());
        entities.add(new EntityImpl(
                EntityType.DOODLE,
                (doodle.getX() - doodle.getHalfOfWidth()),
                getFieldHeight() - (doodle.getH() + doodle.getFullHeight()),
                (doodle.getHalfOfWidth() * 2),
                doodle.getFullHeight()));

        //@TODO У этого метода странная архитектура. При добавлении новых сущностей(врагов например или бонусов)
        //@TODO придётся придумать какую-то фогню. Можно это решить храниением всех этих сущностей в отдельном листе
        //@TODO но переписать этот метод хотя бы 1 раз придётся.
        return entities;
    }

    public void move() {
        try {
            doodle.move();
        } catch (final GameEndedException e) {
            notifySubscribers(Event.FAIL);
        }
        inspectCollisions();
        inspectNewLine();
        notifySubscribers(Event.MOVED);
    }

    public void inspectCollisions() {
        final List<Block> currentLine = playGround.getLineByHCoordinate(doodle.getH()).getBlocks();
        if (doodle.isFalling()) {
            if (doodle.getPrevH() >= playGround.getLineByHCoordinate(doodle.getH()).getRelativeHeight() + PlayGround.getOneLineHeight()) {
                for (final Block block : currentLine) {
                    final int r1 = Math.abs(block.getCentreCoordinate() - doodle.getX());
                    final int r2 = Math.abs((block.getCentreCoordinate() - Constants.playWidth) - doodle.getX());
                    final int r3 = Math.abs((block.getCentreCoordinate() + Constants.playWidth) - doodle.getX());
                    final int len = (block.getHalfWidth() + doodle.getHalfOfWidth());
                    if (r1 < len || r2 < len || r3 < len) {
                        block.collideWithDoodle();
                    }
                }
            }
        }
    }

    private void inspectNewLine() {
        final boolean f = doodle.getH() >= ((double) getFieldHeight() * (5.0 / 9.0));
        if (f) {
            notifySubscribers(Event.LINE_PUSHING);

        }
        while (doodle.getH() >= ((double) getFieldHeight() * (5.0 / 9.0))) {
            playGround.pushNewLine();
            doodle.decHeight(oneLineHeight);
            notifySubscribers(Event.MOVED);

        }
        if (f) {
            notifySubscribers(Event.LINE_PUSHED);
            scoreCounter.notifySubscribers(Event.SCORE_CHANGED);
        }
    }


    public void leftKeyPressed() {
        doodle.getMoveTactic().setXVelocity(-BasicXVelocity);
    }

    public void rightKeyPressed() {
        doodle.getMoveTactic().setXVelocity(BasicXVelocity);
    }

    public void keyReleased() {
        doodle.getMoveTactic().setXVelocity(0);
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

    @Override
    public void eventHappened(final Event event) {
        if (event == Event.MOVED) {
            inspectCollisions();
        }

    }

    public int getMinHeight() {
        return playGround.getLineByHCoordinate(0).getAbsoluteHeight();
    }

    public void restart() {
        PlayGround.getInstance().restart();
        Doodle.getInstance().restart();
        doodle.setMoveTactic(new Standard());
        doodle.addSubscriber(this);
        scoreCounter.restart();
        notifySubscribers(Event.RESTARTED);
    }
}
