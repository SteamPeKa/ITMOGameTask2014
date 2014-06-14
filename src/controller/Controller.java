package controller;

import data.Constants;
import data.Publisher;
import model.GameModel;
import view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 23.05.14
 * Time: 16:58
 */
public class Controller implements Publisher.Subscriber {

    private final GameModel model;

    private View view;

    private boolean activityFlag = true;

    public Controller(final GameModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    public void setView(final View view) {
        this.view = view;
    }

    public void start() {
        while (activityFlag) {
            try {

                Thread.sleep(Constants.iterationTime);
            } catch (final InterruptedException
                    e) {
                e.printStackTrace();
            }
            model.move();
            view.repaintYourSelf();
        }

    }

    public void leftKeyPressed() {
        model.leftKeyPressed();
    }

    public void rightKeyPressed() {
        model.rightKeyPressed();
    }

    public void keyReleased() {
        model.keyReleased();
    }

    @Override
    public void eventHappened(final Publisher.Event event) {
        if (event == Publisher.Event.FAIL) {
            stop();
        }
    }

    public void stop() {
        activityFlag = false;
    }
}
