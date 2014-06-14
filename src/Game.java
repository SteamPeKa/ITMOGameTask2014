import controller.Controller;
import model.GameModel;
import view.ScoreView;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 23.05.14
 * Time: 17:25
 */
public class Game extends JFrame {
    public Game() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 700);
        setResizable(true);
        setLocationRelativeTo(null);
        //setLayout(new GridLayout(1, 1));
    }

    public static void main(final String[] args) {
        final Game game = new Game();
        game.setVisible(true);
        final GameModel model = new GameModel();
        final Controller controller = new Controller(model);
        final View view = new View(controller, model);
        final ScoreView view2 = new ScoreView(model);
        controller.setView(view);
        game.setLayout(new GridLayout(1, 2));
        view.setBorder(BorderFactory.createLineBorder(Color.BLACK,10));
        view2.setBorder(BorderFactory.createLineBorder(Color.BLACK,10));
        game.add(view);
        game.add(view2);
        game.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {

            }

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    controller.leftKeyPressed();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    controller.rightKeyPressed();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    view.flag = true;
                }
            }


            @Override
            public void keyReleased(final KeyEvent e) {
                controller.keyReleased();
            }
        });
        game.revalidate();
        controller.start();
    }
}
