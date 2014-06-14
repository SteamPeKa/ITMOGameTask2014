package  model.entities;

import  model.GameEndedException;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 19:59
 */
public interface Movable {
    public void move() throws GameEndedException;
}
