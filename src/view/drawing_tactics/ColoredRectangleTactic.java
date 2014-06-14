package view.drawing_tactics;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 14.06.14
 * Time: 20:00
 */
public class ColoredRectangleTactic implements DrawingTactic {

    public final Color color;

    public ColoredRectangleTactic(final Color color) {
        this.color = color;
    }


    @Override
    public void drawIt(final Graphics2D g, final int x, final int y, final int w, final int h,
                       final int maxW, final int maxH) {
        g.setColor(color);
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
        g.fillRect(x, y, w, h);
    }
}
