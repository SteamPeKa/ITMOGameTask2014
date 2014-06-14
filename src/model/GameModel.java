package model;

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
    }

    public void inspectCollisions() {
        final List<Block> currentLine = playGround.getLineByHCoordinate(doodle.getH()).getBlocks();
        final int dxl = doodle.getX() - doodle.getHalfOfWidth();
        final int dxr = doodle.getX() + doodle.getHalfOfWidth();
        if (doodle.isFalling()) {
            if (doodle.getPrevH() >= playGround.getLineByHCoordinate(doodle.getH()).getRelativeHeight() + PlayGround.getOneLineHeight()) {

                for (final Block block : currentLine) {
                    if (dxl >= block.getLeftCoordinate() && dxl <= block.getRightCoordinate()) {
                        block.collideWithDoodle();
                    }
                    if (dxr >= block.getLeftCoordinate() && dxr <= block.getRightCoordinate()) {
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
            try {
                Thread.sleep(1);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
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
}
