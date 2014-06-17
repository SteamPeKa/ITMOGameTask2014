package view.drawing_tactics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 17.06.14
 * Time: 17:54
 */
public class CorovanTactic implements DrawingTactic {
    private final Image image;
    private final Color rescueColor;

    public CorovanTactic() {
        final String imagePath = "res/corovan.png";
        final Color rescueColor = Color.MAGENTA;
        Image image1;
        try {
            image1 = ImageIO.read(new File(imagePath));
        } catch (final IOException e) {
            e.printStackTrace();
            image1 = null;
        }
        image = image1;
        this.rescueColor = image == null ? rescueColor : null;
    }

    @Override
    public void drawIt(final Graphics2D g, final int x, final int y, final int w, final int h, final int maxW, final int maxH) {
        if (image == null) {
            g.setColor(rescueColor);
            g.fillRect(x, y, w, h);
            return;
        }
        g.drawImage(image, x, y, w, h, null);
    }
}
