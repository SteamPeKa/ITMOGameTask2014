package model.entities.doodle.move_tackics;

import model.GameEndedException;
import model.entities.doodle.Doodle;
import model.play_field.PlayGround;

import static data.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 18:18
 */
public class Standard implements MoveTactic {


    private int xVelocity = 0;

    private int hVelocity = 1;

    @Override
    public void move(final Doodle.Coordinates coordinates) throws GameEndedException {
        final int iterations;
        final double iterationVelocity;

        final int hvCopy = hVelocity;

        iterations = Math.abs(hVelocity);
        iterationVelocity = (double) hVelocity / iterations;

        for (int i = 0; i < iterations; i++) {
            if (coordinates.getH() + iterationVelocity < 0 && Doodle.getInstance().getPrevH() <= PlayGround.getOneLineHeight()) {
                throw new GameEndedException();
            }
            if (coordinates.getH() + iterationVelocity < playHeight) {
                coordinates.changeH((int) iterationVelocity);
                if (hvCopy != hVelocity) {
                    try {
                        Thread.sleep(iterations - i);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }

            }
        }

        hVelocity += acceleration;
        coordinates.changeX(xVelocity);
        if (coordinates.getX() < 0) {
            coordinates.setX(playWidth + coordinates.getX());
        }
        if (coordinates.getX() > playWidth) {
            coordinates.setX(coordinates.getX() - playWidth);
        }
    }


    @Override
    public void setXVelocity(final int newXV) {
        xVelocity = newXV;

    }

    @Override
    public void setHVelocity(final int newHV) {
        if (newHV >= hVelocity) {
            hVelocity = newHV;
        }
    }

    @Override
    public boolean isFalling() {
        return hVelocity < 0;
    }

    @Override
    public int getXOrientation() {
        if (xVelocity == 0) {
            return 0;
        }
        return xVelocity > 0 ? 1 : -1;
    }

}
