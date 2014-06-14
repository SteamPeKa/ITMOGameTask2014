package  model.entities.blocks;

import data.Constants;
import model.EntityType;
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

    private int centreX;
    private int halfWidth;
    private boolean scored = false;

    public NormalBlock(final int centreX, final int halfWidth) {
        this.halfWidth = halfWidth;
        this.centreX = centreX;
    }

    public NormalBlock(final int centreX) {
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
    public void collideWithMissile() {
        //Тут ничего не делаем
    }
}
