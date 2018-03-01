package tictactoefinder;

import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;

public abstract class Preparation extends Step{
    public static Img<DoubleType> makeImageUniform(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);

        //TODO
        res = img.copy();

        return res;
    }

    public static Img<DoubleType> threshold(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);

        //TODO
        res = img.copy();

        return res;
    }

    public static Img<DoubleType> clean(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);

        //TODO
        res = img.copy();

        return res;
    }

    public static Img<DoubleType> crop(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);

        //TODO
        res = img.copy();

        return res;
    }
}
