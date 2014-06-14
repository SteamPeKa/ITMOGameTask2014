package model;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 22:49
 */
public enum EntityType {


    DOODLE(false), STANDARD_BLOCK(true), BLUE_GEL_BLOCK(true), ROCKET_BLOCK(true);

    private final boolean isBlock;


    EntityType(final boolean isBlock) {
        this.isBlock = isBlock;
    }

    public boolean isBlock() {
        return isBlock;
    }

}
