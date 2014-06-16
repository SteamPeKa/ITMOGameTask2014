package model.entities.blocks;

import view.EntityType;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 15.06.14
 * Time: 10:07
 */
public class AdditionalBlock extends NormalBlock {
    public AdditionalBlock(final int centreX, final int halfWidth, final int y) {
        super(centreX, halfWidth, y);
    }

    public AdditionalBlock(final int centreX, final int y) {
        super(centreX, y);
    }

    @Override
    public EntityType getType() {
        return EntityType.ADDITIONAL_BLOCK;
    }

}
