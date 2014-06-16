package model.entities.blocks;

import view.EntityType;
import model.entities.Collidable;

/**
 * Интерфейс, описывающий блоки, от которых дудл может отпрыкнуть.
 */
public interface Block extends Collidable {

    int getLeftCoordinate();

    int getRightCoordinate();

    EntityType getType();

    int getHalfWidth();

    int getCentreCoordinate();
}
