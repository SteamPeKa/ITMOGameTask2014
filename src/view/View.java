package view;

import controller.Controller;
import data.Publisher;
import model.Entity;
import model.EntityType;
import model.GameModel;
import model.ScoreCounter;
import view.drawing_tactics.ColoredRectangleTactic;
import view.drawing_tactics.DoodleTactic;
import view.drawing_tactics.DrawingTactic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
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
        colorMap.put(EntityType.DOODLE, new DoodleTactic());
        colorMap.put(EntityType.STANDARD_BLOCK, new ColoredRectangleTactic(Color.GREEN));
        colorMap.put(EntityType.BLUE_GEL_BLOCK, new ColoredRectangleTactic(Color.BLUE));
        colorMap.put(EntityType.ROCKET_BLOCK, new ColoredRectangleTactic(Color.ORANGE));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        try {
            backGround = ImageIO.read(new File(
                    "res/hhh.png"));
        } catch (final IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        this.setBorder(BorderFactory.createLineBorder(Color.black));
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
    }


    public void repaintYourSelf() {
        repaint();
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        int imageHeight = (((backGround.getWidth(null)) / getWidth()) + 1) * getHeight();
        if (imageHeight == 0) {
            imageHeight = getHeight();
        }
        int start = ((backGround.getHeight(null) - imageHeight) - model.getMinHeight() / 20);
        if (start <= 0) {
            start = 0;
        }
        g.drawImage(backGround, 0, 0, getWidth(), getHeight(), 0, start, backGround.getWidth(null) - 1, start + imageHeight, null);
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final java.util.List<Entity> entities = model.getAllEntities();
        for (final Entity entity : entities) {
            final DrawingTactic tactic = colorMap.get(entity.getType()) != null ? colorMap.get(entity.getType()) : new ColoredRectangleTactic(Color.cyan);
            final int drawingX = (int) (entity.getX() * xMult);
            final int drawingY = (int) ((entity.getY()) * yMult);
            final int drawingW = (int) (entity.getWidth() * xMult);
            final int drawingH = (int) (entity.getHeight() * yMult);
            tactic.drawIt(g2d, drawingX, drawingY, drawingW, drawingH, getWidth(), getHeight());
        }
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
