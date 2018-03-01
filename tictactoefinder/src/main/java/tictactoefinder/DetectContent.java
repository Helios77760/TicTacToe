package tictactoefinder;

import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;

public class DetectContent extends Step {
    public static boolean isEmpty(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);

        //TODO
        boolean res = false;

        return res;
    }

    public static boolean isCircleByRegistration(Img<DoubleType> img, Img<DoubleType> circle, Img<DoubleType> cross)
    {
        long[] imgSize = getDimensions(img);

        //TODO
        boolean res = false;

        return res;
    }

    public static boolean isCircleByArbitraryRegistration(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);

        //TODO
        boolean res = false;

        return res;
    }

    public static boolean isCrossByAngledProjection(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);

        //TODO
        boolean res = false;

        return res;
    }
}
