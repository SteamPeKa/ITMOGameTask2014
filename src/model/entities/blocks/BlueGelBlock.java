package model.entities.blocks;

import data.Constants;
import model.ScoreCounter;
import model.entities.doodle.Doodle;
import view.EntityType;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 23.05.14
 * Time: 20:27
 */
public class BlueGelBlock implements Block {
    private final int centreX;
    private final int halfWidth;
    private final int y;

    private boolean scored = false;

    public BlueGelBlock(final int centreX, final int halfWidth, final int y) {
        this.halfWidth = halfWidth;
        this.centreX = centreX;
        this.y = y;
    }

    public BlueGelBlock(final int centreX, final int y) {
        this.y = y;
        this.halfWidth = Constants.typicalBlockWidth / 2;
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
    public EntityType getType() {
        return EntityType.BLUE_GEL_BLOCK;
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
    public void collideWithDoodle() {
        Doodle.getInstance().getMoveTactic().setHVelocity((int) (Constants.basicHVelocity * 1.6));
        if (!scored) {
            ScoreCounter.getInstance().eventHappened(getType(), ScoreCounter.ModelEvent.COLLISION);
            scored = true;
        }

    }

    @Override
    public boolean collideWithMissile() {
        return false;
        //Тут ничего не делаем
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
