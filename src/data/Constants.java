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
    //LineGenerating
    public static final int temperature = 5000;
    public static final int difficulty = 4;
    //
    //Speed
    public static final int basicHVelocity = 30;
    public static final int acceleration = (int) (-1 * (Math.pow(basicHVelocity, 2) / (2 * (9.3 * actualOneLineHeight))));
    public static final int BasicXVelocity = (int) (((playWidth * Math.abs(acceleration)) / (2 * basicHVelocity)) * 0.5);

    public static final int missileSpeed = 45;
    public static final int missileWidth = 10;
    public static final int missileHeight = 10;
    //
    //Dimensions
    public static final int typicalBlockWidth = playWidth / 5;
    public static final int doodleHalfWidth = typicalBlockWidth / 4;
    public static final int doodleFullHeight = (int) (doodleHalfWidth * 2 * 1.16);
    //
    //View
    public static final int backgroundDiv = 50;
    //
    //Enemies
    public static final int corovanHalfWidth = 50;
    public static final int corovanHalfHeight = 25;
    public static final int corovanSpeed = 5;
    //
}