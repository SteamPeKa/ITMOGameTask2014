package view.drawing_tactics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 14.06.14
 * Time: 20:10
 */
public class DoodleTactic implements DrawingTactic {

    private final Image image;

    private final DrawingTactic rescueTactic;

    public DoodleTactic() {
        Image image1;
        try {
            image1 = ImageIO.read(new File("res/pear.png"));
        } catch (final IOException e) {
            e.printStackTrace();
            image1 = null;
        }
        image = image1;
        rescueTactic = image == null ? new ColoredRectangleTactic(Color.RED) : null;
    }


    @Override
    public void drawIt(final Graphics2D g, final int x, final int y, final int w, final int h, final int maxW, final int maxH) {
        if (image == null) {
            rescueTactic.drawIt(g, x, y, w, h, maxW, maxH);
            return;
        }
        g.setColor(Color.RED);
        if (x + w > maxW) {
            g.fillRect(
                    x,
                    y,
                    -x + maxW,
                    h);
            g.fillRect(
                    0,
                    y,
                    x + w - maxW,
                    h

            );
            return;
        }
        if (x <= 0) {
            g.fillRect(0, y, w + x, h);
            g.fillRect(maxW + x, y, -x, h);
            return;
        }
        g.drawImage(image, x, y, w, h, null);
    }
}
