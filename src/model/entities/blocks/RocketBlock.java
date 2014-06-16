package model.entities.blocks;

import data.Constants;
import view.EntityType;
import model.ScoreCounter;
import model.entities.doodle.Doodle;
import model.entities.doodle.move_tackics.RocketTactic;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 24.05.14
 * Time: 15:11
 */
public class RocketBlock implements Block {
    private final int halfWidth;
    private final int centreX;
    private boolean scored = false;
    private final int y;

    private boolean destroyed;

    public RocketBlock(final int centreX, final int halfWidth, final int y) {
        this.halfWidth = halfWidth;
        this.centreX = centreX;
        this.y = y;
        destroyed = false;
    }

    public RocketBlock(final int centreX, final int y) {
        this.y = y;
        this.halfWidth = Constants.typicalBlockWidth / 4;
        this.centreX = centreX;
    }

    @Override
    public int getLeftCoordinate() {
        return centreX - halfWidth;
    }

    @Override
    public int getRightCoordinate() {
        return centreX + halfWidth;
    }

    @Override
    public int getHalfWidth() {
        return halfWidth;
    }

    @Override
    public int getCentreCoordinate() {
        return centreX;
    }

    @Override
    public EntityType getType() {
        return destroyed ? EntityType.DESTROYED : EntityType.ROCKET_BLOCK;
    }

    @Override
    public void collideWithDoodle() {
        if (!destroyed) {
            Doodle.getInstance().setMoveTactic(new RocketTactic());
            if (!scored) {
                ScoreCounter.getInstance().eventHappened(getType(), ScoreCounter.ModelEvent.COLLISION);
                scored = true;
            }
            destroyed = true;
        }
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
            @Override
            public int getX() {
                return centreX - halfWidth;
            }

            @Override
            public int getY() {
                return y - Constants.actualOneLineHeight;
            }

            @Override
            public int getWidth() {
                return halfWidth * 2;
            }

            @Override
            public int getHeight() {
                return Constants.actualOneLineHeight;
            }
        };
    }
}
