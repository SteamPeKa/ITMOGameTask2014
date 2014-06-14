package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 14.06.14
 * Time: 22:05
 */
public class ControllingView extends JLabel {

    private final JButton pauseButton;

    private final JButton restartButton;

    private final Controller controller;


    public ControllingView(final Controller controlleR) {
        this.controller = controlleR;
        pauseButton = new JButton("Поставить на паузу");
        pauseButton.setFocusable(false);
        this.setLayout(new GridLayout(2, 1, 10, 20));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(pauseButton);
        pauseButton.addActionListener(new ActionListener() {

            private State state = State.PAUSE;

            @Override
            public void actionPerformed(final ActionEvent e) {
                if (state == State.PAUSE) {
                    state = State.UNPAUSE;
                    controller.pause();
                    pauseButton.setText("Снять с паузы");
                } else {
                    state = State.PAUSE;
                    controller.unpause();
                    pauseButton.setText("Поставить на паузу");
                }
            }
        });
        restartButton = new JButton("Рестарт");
        restartButton.setFocusable(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.restart();
            }
        });
        this.add(restartButton);
    }


    private enum State {
        PAUSE, UNPAUSE
    }

}
