package model.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 16:51
 */
public interface Collidable {

    void collideWithDoodle();

    boolean collideWithMissile();

    HitBox getHitBox();


    //В качестве x и y методы выдают нижний левый угол хитбокса
    public interface HitBox {
        int getX();

        int getY();

        int getWidth();

        int getHeight();
    }
}
