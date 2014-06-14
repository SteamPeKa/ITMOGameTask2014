package  model.entities.doodle.move_tackics;

import data.Constants;
import  model.GameEndedException;
import  model.entities.doodle.Doodle;
import  model.play_field.PlayGround;

import static  data.Constants.playHeight;
import static  data.Constants.playWidth;
import static  data.Constants.rocketProcs;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 24.05.14
 * Time: 15:07
 */
public class RocketTactic implements MoveTactic {

    private int procs = rocketProcs;

    private int hVelocity = (int) (Constants.basicHVelocity * 0.8);
    private int xVelocity = 0;

    @Override
    public void move(final Doodle.Coordinates coordinates) throws GameEndedException {
        final int iterations;
        final double iterationVelocity;


        iterations = Math.abs(hVelocity);
        iterationVelocity = (double) hVelocity / iterations;

        for (int i = 0; i < iterations; i++) {
            if (coordinates.getH() + iterationVelocity < 0 && Doodle.getInstance().getPrevH() <= PlayGround.getOneLineHeight()) {
                throw new GameEndedException();
            }
            if (coordinates.getH() + iterationVelocity < playHeight) {
                coordinates.changeH((int) iterationVelocity);
            }
        }
        procs--;
        if (procs == 0) {
            Doodle.getInstance().setMoveTactic(new Standard());
        }

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

    }

    @Override
    public boolean isFalling() {
        return false;
    }
}
