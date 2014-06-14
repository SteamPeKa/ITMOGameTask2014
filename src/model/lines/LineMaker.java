package model.lines;

import model.entities.blocks.Block;
import model.entities.blocks.BlueGelBlock;
import model.entities.blocks.NormalBlock;
import model.entities.blocks.RocketBlock;
import model.play_field.PlayGround;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static data.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: Балагуров Владимир
 * группа 2742
 * Date: 22.05.14
 * Time: 17:29
 */
public class LineMaker {

    private static int generated = 0;

    // private static boolean flag = true;

    private LineMaker() {
    }

    public static LinesList makeStartingLines() {
        final LinesList result = new LinesList();
        final List<Block> blocks = new ArrayList<>();
        for (int w = (typicalBlockWidth / 2); w <= playWidth - (typicalBlockWidth / 2); w += typicalBlockWidth) {
            blocks.add(new NormalBlock(w));
        }
        for (int i = 0; i < actualOneLineHeight; i++) {
            result.addLine(generateEmptyLine(i * PlayGround.getOneLineHeight(), i * PlayGround.getOneLineHeight()));
            generated++;
        }
        result.addLine(new LineImpl(blocks, actualOneLineHeight, actualOneLineHeight));
        generated++;
        for (int i = 16; i < linesQuantity; i++) {
            if (i % (2 * actualOneLineHeight) != 0) {
                result.addLine(generateEmptyLine(i * PlayGround.getOneLineHeight(), i * PlayGround.getOneLineHeight()));
            } else {
                result.addLine(generateOneRandomLine(i * PlayGround.getOneLineHeight(), i * PlayGround.getOneLineHeight()));
            }
        }
        return result;
    }

    public static Line generateOneRandomLine(final int absHeight, final int height) {
        final List<Block> blocks = new ArrayList<>();
        final int x = new Random().nextInt(playWidth);
        if (Math.random() > 0.95) {
            blocks.add(new BlueGelBlock(x));
        } else {
            if (Math.random() > 0.97) {
                blocks.add(new RocketBlock(x));
            } else {
                blocks.add(new NormalBlock(x));
            }
        }
        generated++;
        return new LineImpl(blocks, absHeight, height);
    }

    public static Line generateEmptyLine(final int absHeight, final int height) {
        final List<Block> blocks = new ArrayList<>();
        generated++;
        return new LineImpl(blocks, absHeight, height);
    }

    public static Line generateNewLine() {
        final int absHeight = generated * PlayGround.getOneLineHeight();
        final int relHeight = PlayGround.getOneLineHeight() * (linesQuantity - 1);

        /*@TODO Следующий блок используется для проверки, её надо закоммитить
        if (flag) {

            final Line result;
            final List<Block> blocks = new ArrayList<>();
            blocks.add(new NormalBlock(0));
            blocks.add(new NormalBlock(PlayGround.WIDTH));
            flag=false;
            return new LineImpl(blocks, absHeight, relHeight);
        }  else {
            return  generateEmptyLine(absHeight, relHeight);

        }
        *///@TODO

        if (generated % (actualOneLineHeight * 4) != 0) {
            return generateEmptyLine(absHeight, relHeight);
        } else {
            return generateOneRandomLine(absHeight, relHeight);
        }
    }
}
