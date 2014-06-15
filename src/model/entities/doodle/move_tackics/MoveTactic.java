package model.entities.doodle.move_tackics;

import model.GameEndedException;
import model.entities.doodle.Doodle;

public interface MoveTactic {
    public void move(final Doodle.Coordinates coordinates) throws GameEndedException;

    public void setXVelocity(final int newXV);

    public void setHVelocity(int newHV);

    public boolean isFalling();

    public int getXOrientation();

}