package model.lines;

import model.entities.blocks.Block;
import model.lines.Line;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 17:33
 */
public class LineImpl implements Line {
    private List<Block> blocks;
    private final int absoluteHeight;
    private int relativeHeight;

    public LineImpl(final List<Block> blocks, final int absoluteHeight, final int relativeHeight) {
        this.blocks = blocks;
        this.absoluteHeight = absoluteHeight;
        this.relativeHeight = relativeHeight;
    }


    @Override
    public int getAbsoluteHeight() {
        return absoluteHeight;
    }

    @Override
    public int getRelativeHeight() {
        return relativeHeight;
    }

    @Override
    public List<Block> getBlocks() {
        return blocks;
    }

    @Override
    public void decHeight(final int dh) {
        relativeHeight -= dh;
    }
}
