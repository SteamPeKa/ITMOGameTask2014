package view.drawing_tactics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 15.06.14
 * Time: 19:17
 */
public abstract class ImageTactic implements DrawingTactic {
    private final Image image;
    private final ColoredRectangleTactic rescueTactic;

    protected ImageTactic(final String imagePath, final Color rescueColor) {
        Image image1;
        try {
            image1 = ImageIO.read(new File(imagePath));
            //image1 = ImageIO.read(getClass().getResource(imagePath));
            // image1 = ImageIO.read(System.class.getResource(imagePath));

        } catch (final Exception e) {
            e.printStackTrace();
            image1 = null;
        }
        image = image1;
        this.rescueTactic = image == null ? new ColoredRectangleTactic(rescueColor) : null;
    }

    @Override
    public void drawIt(final Graphics2D g, final int x, final int y, final int w, final int h, final int maxW, final int maxH) {
        if (image == null) {
            rescueTactic.drawIt(g, x, y, w, h, maxW, maxH);
            return;
        }
        if (x + w > maxW) {
            g.drawImage(image, x - maxW, y, w, h, null);
        }
        if (x <= 0) {
            g.drawImage(image, x + maxW, y, w, h, null);
        }
        g.drawImage(image, x, y, w, h, null);
    }
}
