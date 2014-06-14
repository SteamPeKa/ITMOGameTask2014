package model.play_field;

import model.Entity;
import model.EntityImpl;
import model.entities.blocks.Block;
import model.lines.Line;
import model.lines.LineMaker;
import model.lines.LinesList;

import java.util.ArrayList;
import java.util.List;

import static data.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 17:10
 */
public class PlayGround {
    private  LinesList lines;

    private static final PlayGround ourInstance = new PlayGround();

    public static PlayGround getInstance() {
        return ourInstance;
    }

    private PlayGround() {
        lines = LineMaker.makeStartingLines();
    }

 /*   public int getLineNumber(final int hCoordinate) {
        if (hCoordinate == 0) {
            return 1;
        }
        if (hCoordinate % getOneLineHeight() == 0) {
            return hCoordinate / getOneLineHeight();
        } else {
            if ((hCoordinate / getOneLineHeight()) + 1 <= linesQuantity) {
                return (hCoordinate / getOneLineHeight()) + 1;
            } else {
                return linesQuantity;
            }
        }
    }    */

    public static int getOneLineHeight() {
        return oneLineHeight;
    }

    public Line getLineByHCoordinate(final int h) {
        return lines.getLine(h);
    }

    public List<Entity> getBlocksAsEntities() {
        final List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < linesQuantity; i++) {
            final Line current = lines.getLine(i);
            final int y = playHeight - (current.getRelativeHeight() + PlayGround.getOneLineHeight());
            for (final Block block : current.getBlocks()) {
                entities.add(new EntityImpl(
                        block.getType(),
                        block.getLeftCoordinate(),
                        y,
                        block.getRightCoordinate() - block.getLeftCoordinate(),
                        actualOneLineHeight));
            }
        }
        return entities;
    }

    public void pushNewLine() {
        lines.pushLine(LineMaker.generateNewLine());
    }

    public void restart() {
        lines = LineMaker.makeStartingLines();
    }
}
