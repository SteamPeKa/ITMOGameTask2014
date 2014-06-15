package model.entities.blocks;

import model.EntityType;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 15.06.14
 * Time: 10:07
 */
public class AdditionalBlock extends NormalBlock {
    public AdditionalBlock(final int centreX, final int halfWidth) {
        super(centreX, halfWidth);
    }

    public AdditionalBlock(final int centreX) {
        super(centreX);
    }

    @Override
    public EntityType getType() {
        return EntityType.ADDITIONAL_BLOCK;
    }

}
