package view;

import controller.Controller;
import data.Constants;
import data.Publisher;
import model.GameModel;
import model.ScoreCounter;
import view.drawing_tactics.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 23.05.14
 * Time: 16:59
 */
public class View extends JLabel implements Publisher.Subscriber {

    private double xMult;

    private double yMult;

    private final Controller controller;

    private final GameModel model;

    private final Map<EntityType, DrawingTactic> colorMap = new EnumMap<>(EntityType.class);

    private Image backGround;

    public View(final Controller controller, final GameModel model) {
        this.controller = controller;
        this.model = model;
        model.addSubscriber(this);
        colorMap.put(EntityType.DOODLE_R_F, new DoodleRFTactic());
        colorMap.put(EntityType.DOODLE_L_F, new DoodleLFTactic());
        colorMap.put(EntityType.DOODLE_R_J, new DoodleRJTactic());
        colorMap.put(EntityType.DOODLE_L_J, new DoodleLJTactic());
        colorMap.put(EntityType.DESTROYED, new DestroyedTactic());
        colorMap.put(EntityType.STANDARD_BLOCK, new StandardBlockTactic());
        colorMap.put(EntityType.BLUE_GEL_BLOCK, new BlueBlockTactic());
        colorMap.put(EntityType.ROCKET_BLOCK, new RocketBlockTactic());
        colorMap.put(EntityType.COROVAN, new CorovanTactic());
        colorMap.put(EntityType.MISSILE, new MissileTactic());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        try {
            backGround = ImageIO.read(getClass().getClassLoader().getResource("res/hhh.png"));
        } catch (final IOException e) {
            e.printStackTrace();
            backGround = null;
            System.out.println("Игра запустится без фонового изображения");
        }
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(final ComponentEvent e) {
                xMult = (double) (getWidth()) / model.getFieldWidth();
                yMult = (double) (getHeight()) / model.getFieldHeight();
            }

            @Override
            public void componentMoved(final ComponentEvent e) {

            }

            @Override
            public void componentShown(final ComponentEvent e) {

            }

            @Override
            public void componentHidden(final ComponentEvent e) {

            }
        });
        setFocusable(false);
    }


    public void repaintYourSelf() {
        repaint();
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        if (backGround != null) {
            makeBackground(g);
        }

        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final java.util.List<OutputEntity> entities = model.getAllEntities();
        for (final OutputEntity entity : entities) {
            final DrawingTactic tactic = colorMap.get(entity.getType()) != null ? colorMap.get(entity.getType()) : new ColoredRectangleTactic(Color.cyan);
            final int drawingX = (int) (entity.getX() * xMult);
            final int drawingY = (int) ((entity.getY()) * yMult);
            final int drawingW = (int) (entity.getWidth() * xMult);
            final int drawingH = (int) (entity.getHeight() * yMult);
            tactic.drawIt(g2d, drawingX, drawingY, drawingW, drawingH, getWidth(), getHeight());
        }
    }

    private void makeBackground(final Graphics g) {
        int imageHeight = (((backGround.getWidth(null)) / getWidth()) + 1) * getHeight();
        if (imageHeight == 0) {
            imageHeight = getHeight();
        }
        int start = ((backGround.getHeight(null) - imageHeight) - (model.getMinHeight() / Constants.backgroundDiv));
        if (start <= 0) {
            start = 0;
        }
        g.drawImage(backGround, 0, 0, getWidth(), getHeight(), 0, start, backGround.getWidth(null) - 1, start + imageHeight, null);
    }


    @Override
    public void eventHappened(final Publisher.Event event) {
        if (event == Publisher.Event.MOVED) {
            repaintYourSelf();
        }

        if (event == Publisher.Event.FAIL) {
            repaintYourSelf();
            JOptionPane.showMessageDialog(
                    this.getParent(),
                    "ПОТРАЧЕНО. Результат: " + ScoreCounter.getInstance().getScore().getFullScore(),
                    "Конец игры", JOptionPane.ERROR_MESSAGE);
        }
    }
}
