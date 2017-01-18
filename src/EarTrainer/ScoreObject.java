package EarTrainer;

/**
 * Basic class meant to be the base for anything that will be eventually be able to be drawn to a Score, or at least
 * be owned by a Score (or another ScoreObject).
 */
abstract class ScoreObject
{
    //This is necessary for EVERYTHING. Only 1 canvas can be used at a time currently.
    private static Score score;

    private double x, y;
    private double width, height;

    void calculateFields() { }

    public static Score getScore()
    {
        return score;
    }

    public static void setScore(Score score)
    {
        ScoreObject.score = score;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double height)
    {
        this.height = height;
    }
}
