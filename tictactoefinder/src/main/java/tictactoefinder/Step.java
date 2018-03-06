package tictactoefinder;

import net.imagej.Dataset;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.DoubleType;
import org.scijava.log.LogService;

public abstract class Step {
    public static LogService logging;
    public static final int ERROR = 0;
    public static final int WARN = 1;
    public static final int INFO = 2;

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

    public static void log(Class theClass, int level, String message)
    {
        if(level == ERROR)
            logging.error(theClass.getSimpleName() + " : " + message);
        else if(level == WARN)
            logging.warn(theClass.getSimpleName()+ " : " + message);
        else if(level == INFO)
            logging.info(theClass.getSimpleName() + " : " + message);

    }
}
