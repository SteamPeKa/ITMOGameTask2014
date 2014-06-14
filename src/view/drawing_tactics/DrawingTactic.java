package view.drawing_tactics;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 14.06.14
 * Time: 19:55
 */
public interface DrawingTactic {
    public void drawIt(final Graphics2D g, final int x, final int y, final int w, final int h, int maxW, int maxH);
}

