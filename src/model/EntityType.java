package model;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 22:49
 */
public enum EntityType {

    STANDARD_BLOCK(true), BLUE_GEL_BLOCK(true), ROCKET_BLOCK(true), ADDITIONAL_BLOCK(true),
    DOODLE_L_F(false), DOODLE_R_F(false), DOODLE_R_J(false), DOODLE_L_J(false);

    private final boolean isBlock;


    EntityType(final boolean isBlock) {
        this.isBlock = isBlock;
    }

    public boolean isBlock() {
        return isBlock;
    }

}
