package model.lines;

import model.play_field.PlayGround;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static data.Constants.linesQuantity;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 17:20
 */
public class LinesList {

    private final List<Line> lines;

    public LinesList() {
        lines = Collections.synchronizedList(new ArrayList<Line>(linesQuantity));
    }

    public void pushLine(final Line newLine) {
        synchronized (lines) {
            lines.remove(0);
            for (final Line line : lines) {
                line.decHeight(PlayGround.getOneLineHeight());
            }
            lines.add(newLine);
        }

    }

    public void addLine(final Line newLine) {
        synchronized (lines) {
            lines.add(newLine);
        }
    }

    public Line getLine(final int index) {
        synchronized (lines) {
            //  try {
            return lines.get(index);
       /* } catch (final IndexOutOfBoundsException e) {
            System.out.println("pish");
            return null;
        }*/
        }
    }
}
