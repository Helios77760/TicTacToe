package tictactoefinder;

import net.imagej.Dataset;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.DoubleType;

public abstract class Step {
    public static long[] getDimensions(Img<DoubleType> image)
    {
        long[] imgSize = new long[image.numDimensions()];
        image.dimensions(imgSize);
        return imgSize;
    }

    public static long[] getDimensions(Dataset image)
    {
        long[] imgSize = new long[image.numDimensions()];
        image.dimensions(imgSize);
        return imgSize;
    }
}
