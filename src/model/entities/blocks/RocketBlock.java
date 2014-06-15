package model.entities.blocks;

import data.Constants;
import model.EntityType;
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

    public RocketBlock(final int centreX, final int halfWidth) {
        this.halfWidth = halfWidth;
        this.centreX = centreX;
    }

    public RocketBlock(final int centreX) {
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
        return EntityType.ROCKET_BLOCK;
    }

    @Override
    public void collideWithDoodle() {
        Doodle.getInstance().setMoveTactic(new RocketTactic());
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
