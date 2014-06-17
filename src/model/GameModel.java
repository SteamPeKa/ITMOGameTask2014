package model;

import data.Publisher;
import model.entities.Collidable;
import model.entities.Missile;
import model.entities.blocks.Block;
import model.entities.doodle.Doodle;
import model.entities.doodle.move_tackics.Standard;
import model.entities.enemies.Corovan;
import model.entities.enemies.Enemy;
import model.play_field.PlayGround;
import view.OutputEntity;

import java.util.*;

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

    private final List<Missile> missiles;

    private final List<Enemy> enemies;

    private final Set<Subscriber> subscribers;

    public boolean ended = false;

    public GameModel() {
        playGround = PlayGround.getInstance();
        doodle = Doodle.getInstance();
        doodle.setMoveTactic(new Standard());
        doodle.addSubscriber(this);
        subscribers = new HashSet<>();
        scoreCounter = ScoreCounter.getInstance();
        missiles = Collections.synchronizedList(new ArrayList<Missile>());
        enemies = Collections.synchronizedList(new ArrayList<Enemy>());
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

    public List<OutputEntity> getAllEntities() {
        final List<OutputEntity> entities = new ArrayList<>();
        entities.addAll(playGround.getBlocksAsEntities());
        for (final Missile missile : new ArrayList<>(missiles)) {
            entities.add(missile.getOut());
        }
        for (final Enemy enemy : new ArrayList<>(enemies)) {
            entities.add(enemy.getOut());
        }
        entities.add(doodle.getEntity());

        //@TODO У этого метода странная архитектура. При добавлении новых сущностей(врагов например или бонусов)
        //@TODO придётся придумать какую-то фогню. Можно это решить храниением всех этих сущностей в отдельном листе
        //@TODO но переписать этот метод хотя бы 1 раз придётся.
        return entities;
    }

    public void move() {
        try {
            doodle.move();
            for (final Iterator<Enemy> enemyIterator = new ArrayList<>(enemies).iterator(); enemyIterator.hasNext(); ) {
                final Enemy enemy = enemyIterator.next();
                enemy.move();
                if (enemy.isDestroyed()) {
                    enemies.remove(enemy);
                    //   System.out.println("Корован ушёл");
                    continue;
                }
            }
            for (final Iterator<Missile> missileIterator = new ArrayList<>(missiles).iterator(); missileIterator.hasNext(); ) {
                final Missile missile = missileIterator.next();

                for (int i = 0; i < missileSpeed; i++) {
                    missile.move();
                    inspectMissile(missile);
                    notifySubscribers(Event.MOVED);
                }
            }

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
                    final int r2 = Math.abs((block.getCentreCoordinate() - playWidth) - doodle.getX());
                    final int r3 = Math.abs((block.getCentreCoordinate() + playWidth) - doodle.getX());
                    final int len = (block.getHalfWidth() + doodle.getHalfOfWidth());
                    if (r1 < len || r2 < len || r3 < len) {
                        block.collideWithDoodle();
                    }
                }
            }
            for (final Enemy enemy : new ArrayList<>(enemies)) {
                final Collidable.HitBox hitBox = enemy.getHitBox();
                if (doodle.getPrevH() >= hitBox.getY() + hitBox.getHeight()) {
                    if (doodle.getH() > hitBox.getY() && doodle.getH() < hitBox.getY() + hitBox.getHeight()) {
                        final int r1 = Math.abs(hitBox.getX() + hitBox.getWidth() / 2 - doodle.getX());
                        final int r2 = Math.abs((hitBox.getX() + hitBox.getWidth() / 2 - playWidth) - doodle.getX());
                        final int r3 = Math.abs((hitBox.getX() + hitBox.getWidth() / 2 + playWidth) - doodle.getX());
                        final int len = hitBox.getWidth() / 2 + doodle.getHalfOfWidth();
                        if (r1 < len || r2 < len || r3 < len) {
                            enemy.destroy();
                            scoreCounter.eventHappened(enemy.getType(), ScoreCounter.ModelEvent.DESTROYED);
                            enemies.remove(enemy);
                            doodle.getMoveTactic().setHVelocity(basicHVelocity);
                            System.out.println("Корован уничтожен прыжком");
                        }
                    }
                }
            }
        }
        for (final Enemy enemy : new ArrayList<>(enemies)) {
            final Collidable.HitBox hitBox = enemy.getHitBox();
            if ((doodle.getH() > hitBox.getY() && doodle.getH() < hitBox.getY() + hitBox.getHeight())
                    || (doodle.getH() + doodle.getFullHeight() > hitBox.getY() && doodle.getH() + doodle.getFullHeight() < hitBox.getY() + hitBox.getHeight())) {
                final int r1 = Math.abs(hitBox.getX() + hitBox.getWidth() / 2 - doodle.getX());
                final int r2 = Math.abs((hitBox.getX() + hitBox.getWidth() / 2 - playWidth) - doodle.getX());
                final int r3 = Math.abs((hitBox.getX() + hitBox.getWidth() / 2 + playWidth) - doodle.getX());
                final int len = hitBox.getWidth() / 2 + doodle.getHalfOfWidth();
                if (r1 < len || r2 < len || r3 < len) {
                    enemy.collideWithDoodle();
                    ended = true;
                    return;
                }
            }
        }

    }

    private void inspectMissile(final Missile missile) {
        if (missile.getY() >= playHeight) {
            missile.destroy();
            missiles.remove(missile);
            return;
        }
        final List<Block> cur = playGround.getLineByHCoordinate(missile.getY()).getBlocks();
        for (final Block block : cur) {
            if (missile.getX() >= block.getLeftCoordinate() && missile.getX() <= block.getRightCoordinate()) {
                if (block.collideWithMissile()) {
                    missile.destroy();
                    missiles.remove(missile);
                    return;
                }
            }
        }
        for (final Iterator<Enemy> enemyIterator = new ArrayList<>(enemies).iterator(); enemyIterator.hasNext(); ) {
            final Enemy e = enemyIterator.next();
            final Collidable.HitBox enemyHitBox = e.getHitBox();

            if (enemyHitBox.getY() <= missile.getY() && enemyHitBox.getY() + enemyHitBox.getHeight() >= missile.getY()) {
                if (enemyHitBox.getX() <= missile.getX() && enemyHitBox.getX() + enemyHitBox.getWidth() >= missile.getX()) {
                    if (e.collideWithMissile()) {
                        missile.destroy();
                        //   System.out.println("Корован уничтожен");
                        enemies.remove(e);
                        scoreCounter.eventHappened(e.getType(), ScoreCounter.ModelEvent.DESTROYED);

                        missiles.remove(missile);
                        return;
                    }
                }
            }

        }
    }


    private void inspectNewLine() {
        final boolean f = doodle.getH() >= ((double) getFieldHeight() * (5.0 / 9.0));
        if (f) {
            notifySubscribers(Event.LINE_PUSHING);
            while (doodle.getH() >= ((double) getFieldHeight() * (5.0 / 9.0))) {
                playGround.pushNewLine();
                doodle.decHeight(oneLineHeight);
                for (final Missile missile : missiles) {
                    missile.decHeight(oneLineHeight);
                }
                for (final Enemy enemy : enemies) {
                    enemy.decH(oneLineHeight);
                }
                notifySubscribers(Event.MOVED);
                if (playGround.getLineByHCoordinate(0).getAbsoluteHeight() % (playHeight * 1) == 0) {
                    enemies.add(new Corovan(this, playHeight + oneLineHeight * 3));
                    // System.out.println("Корован заспаунен (" + enemies.size() + ")");
                }

            }
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
        if (!ended) {
            if (event == Event.MOVED) {
                inspectCollisions();
                return;
            }
            if (event == Event.FAIL) {
                notifySubscribers(Event.FAIL);
                ended = true;
            }

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
        enemies.clear();
        missiles.clear();
        notifySubscribers(Event.RESTARTED);
        ended = false;
    }

    public void missileShot() {
        if (missiles.size() >= 20) {
            System.out.println(missiles.size());
        }
        missiles.add(new Missile(this));
    }
}
