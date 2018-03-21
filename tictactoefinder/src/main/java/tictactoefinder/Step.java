package tictactoefinder;

import net.imagej.Dataset;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;
import org.scijava.log.LogService;

import java.util.function.Consumer;
import java.util.function.Function;

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

    public static void processImage(Img<DoubleType> img, Consumer<DoubleType> cons)
    {
        long[] imgSize = getDimensions(img);
        RandomAccess<DoubleType> cur = img.randomAccess();
        long[] pos = new long[]{0,0};
        for(int x=0; x<imgSize[0];x++)
        {
            pos[0]=x;
            for(int y=0;y<imgSize[1];y++)
            {
                pos[1]=y;
                cur.setPosition(pos);
                cons.accept(cur.get());
            }
        }
    }

    public static Img<DoubleType> createImageFrom(Img<DoubleType> img, Function<DoubleType, Double> func)
    {
        long[] imgSize = getDimensions(img);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);
        RandomAccess<DoubleType> curIn = img.randomAccess();
        RandomAccess<DoubleType> curOut = res.randomAccess();
        long[] pos = new long[]{0,0};
        for(int x=0; x<imgSize[0];x++)
        {
            pos[0]=x;
            for(int y=0;y<imgSize[1];y++)
            {
                pos[1]=y;
                curIn.setPosition(pos);
                curOut.setPosition(pos);
                curOut.get().set(func.apply(curIn.get()));
            }
        }
        return res;
    }

    public static Img<DoubleType> convolve(Img<DoubleType> img, double[][] kernel)
    {
        long[] imgSize = getDimensions(img);
        int xcenter = kernel.length/2;
        int ycenter;

        RandomAccess<DoubleType> cur = img.randomAccess();

        Img<DoubleType> res = ArrayImgs.doubles(imgSize);
        RandomAccess<DoubleType> curOut = res.randomAccess();
        long[] pos = {0,0}, kpos = {0,0};
        double value, normalizer;
        for(int x=0; x<imgSize[0];x++)
        {
            pos[0]=x;
            for(int y=0; y<imgSize[1];y++)
            {
                pos[1]=y;
                curOut.setPosition(pos);
                value=0;
                normalizer=0;
                for(int xkernel=0; xkernel<kernel.length;xkernel++)
                {
                    kpos[0]=x+xkernel-xcenter;
                    if(kpos[0] >= 0 && kpos[0] < imgSize[0])
                    {
                        ycenter = kernel[xkernel].length/2;
                        for(int ykernel=0; ykernel<kernel[xkernel].length;ykernel++)
                        {
                            kpos[1]=y+ykernel-ycenter;
                            if(kpos[1] >= 0 && kpos[1] < imgSize[1])
                            {
                                cur.setPosition(kpos);
                                value+=cur.get().getRealDouble()*kernel[xkernel][ykernel];
                                normalizer+=kernel[xkernel][ykernel];
                            }
                        }
                    }
                }
                if(normalizer == 0)
                {
                    curOut.get().set(cur.get().getRealDouble());
                }else
                {
                    curOut.get().set(value/normalizer);
                }
            }
        }

        return res;
    }
}
