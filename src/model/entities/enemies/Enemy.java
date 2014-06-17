package model.entities.enemies;

import model.entities.Collidable;
import model.entities.Destroyable;
import model.entities.Movable;
import view.EntityType;
import view.OutputEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 17.06.14
 * Time: 15:11
 */
public interface Enemy extends Movable, Destroyable, Collidable {
    public OutputEntity getOut();

    public void decH(final int dh);

    public EntityType getType();
}
