package model.entities.blocks;

import data.Constants;
import view.EntityType;
import model.ScoreCounter;
import model.entities.doodle.Doodle;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 17:51
 */
public class NormalBlock implements Block {

    private final int centreX;
    private final int halfWidth;
    private boolean scored = false;

    private final int y;

    public NormalBlock(final int centreX, final int halfWidth, final int y) {
        this.halfWidth = halfWidth;
        this.centreX = centreX;
        this.y = y;
    }

    public NormalBlock(final int centreX, final int y) {
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
        return EntityType.STANDARD_BLOCK;
    }

    @Override
    public void collideWithDoodle() {
        Doodle.getInstance().getMoveTactic().setHVelocity(Constants.basicHVelocity);
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
    public int getHalfWidth() {
        return halfWidth;
    }

    @Override
    public int getCentreCoordinate() {
        return centreX;
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
