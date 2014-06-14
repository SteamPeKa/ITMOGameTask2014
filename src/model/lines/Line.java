package model.lines;

import model.entities.blocks.Block;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 16:20
 */
public interface Line {

    public int getAbsoluteHeight();

    public int getRelativeHeight();

    public List<Block> getBlocks();

    public void decHeight(final int dh);
}
