package model.entities.blocks;

import data.Constants;
import model.ScoreCounter;
import model.EntityType;
import model.entities.doodle.Doodle;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 23.05.14
 * Time: 20:27
 */
public class BlueGelBlock implements Block {
    private int centreX;
    private int halfWidth;

    private boolean scored = false;

    public BlueGelBlock(final int centreX, final int halfWidth) {
        this.halfWidth = halfWidth;
        this.centreX = centreX;
    }

    public BlueGelBlock(final int centreX) {
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
    public void collideWithMissile() {
        //Тут ничего не делаем
    }
}
