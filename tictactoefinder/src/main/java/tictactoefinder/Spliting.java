package tictactoefinder;

import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;

import java.util.ArrayList;

public class Spliting extends Step {
    public static long[][][] detection(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);

        long[][][] res = new long[3][][];

        for(int i=0; i<3; i++)
        {
            res[i]=new long[3][];
            for(int j=0; j<3; j++)
            {
                res[i][j]=new long[4];
            }
        }

        //TODO

        return res;
    }

    public static ArrayList<Img<DoubleType>> split(Img<DoubleType> img, long[][][] borders)
    {
        long[] imgSize = getDimensions(img);
        ArrayList<Img<DoubleType>> res = new ArrayList<>();


        //TODO
        for(int i=0; i<9;i++)
        {
            res.add(ArrayImgs.doubles(imgSize));
        }

        return res;
    }
}
