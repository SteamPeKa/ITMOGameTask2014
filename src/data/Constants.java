package data;

public class Constants {
    private Constants() {
    }

    //Basics
    public static final int playWidth = 500;
    public static final int playHeight = 750;
    public static final int linesQuantity = playHeight;
    public static final int actualLinesQuantity = 50;
    public static final int oneLineHeight = playHeight / linesQuantity;
    public static final int actualOneLineHeight = playHeight / actualLinesQuantity;
    public static final int iterationTime = 25;
    public static final int rocketProcs = 150;
    //
    //Speed
    public static final int basicHVelocity = 30;
    public static final int acceleration = (int) (-1 * (Math.pow(basicHVelocity, 2) / (2 * (9.3 * actualOneLineHeight))));
    public static final int BasicXVelocity = (int) (((playWidth * Math.abs(acceleration)) / (2 * basicHVelocity)) * 0.5);
    //
    //Dimensions
    public static final int typicalBlockWidth = playWidth / 5;
    public static final int doodleHalfWidth = typicalBlockWidth / 5;
    public static final int doodleFullHeight = (int) (doodleHalfWidth * 2 * 2.2);
    //
}