import controller.Controller;
import model.GameModel;
import view.ControllingView;
import view.ScoreView;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

    private static final GameModel model = new GameModel();
    private static final Controller controller = new Controller(model);


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

        final View view = new View(controller, model);
        final ScoreView view2 = new ScoreView(model);
        final ControllingView view3 = new ControllingView(controller);
        model.addSubscriber(view3);
        controller.setView(view);
        game.setLayout(new GridLayout(1, 2));
        final JLabel additional = new JLabel();
        additional.setLayout(new GridLayout(2, 1));
        additional.add(view2);
        additional.add(view3); //Пока что это не работает вообще. Очень странная штука творится.
        view.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        view2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        game.add(view);
        game.add(additional);
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
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                controller.keyReleased();
            }
        });
        game.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(final FocusEvent e) {

            }

            @Override
            public void focusLost(final FocusEvent e) {
          //      System.out.println("Фокус потерян");
                game.requestFocus(false);
                game.requestFocus();

            }
        });
        controller.start();
    }

}
