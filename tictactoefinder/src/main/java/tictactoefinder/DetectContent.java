package tictactoefinder;

import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class DetectContent extends Step {

    public static final double FLOODINGTHRESHOLD = 0.05;
    public static final double EMPTYTHRESHOLD = 0.01;
    public static boolean isEmpty(Img<DoubleType> img)
    {
        double[] values = {0,0};
        processImage(img, (p)->{
            if(p.getRealDouble() < 127)
            {
                values[0]++;
            }else
            {
                values[1]++;
            }
        });
        if(values[1] ==0)
            return true;
        double val = values[0]/(values[0]+values[1]);
        return val < EMPTYTHRESHOLD || val > 1-EMPTYTHRESHOLD;
    }


    public static boolean isCircleByRegistration(Img<DoubleType> img)
    {
        throw new NotImplementedException();
    }

    public static boolean isCrossByAngledProjection(Img<DoubleType> img)
    {
        throw new NotImplementedException();
    }

    public static boolean isCircleByFlooding(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);

        RandomAccess<DoubleType> curIn = img.randomAccess();
        Img<DoubleType> testImg = img.copy();
        RandomAccess<DoubleType> curTest = testImg.randomAccess();
        long[] pos = {0,0};
        for(pos[0]=0; pos[0] < imgSize[0]; pos[0]++)
        {
            for(pos[1]=0; pos[1]<imgSize[1];pos[1]++)
            {
                curIn.setPosition(pos);
                if(curIn.get().getRealDouble() > 127)
                    break;
            }
            if(pos[1] != imgSize[1])
                break;
        }
        if(pos[0] == imgSize[0] && pos[1] == imgSize[1])
            return false;

        curTest.setPosition(pos);
        ArrayList<long[]> posFIFO = new ArrayList<>();
        posFIFO.add(new long[]{pos[0], pos[1]});
        long[] offpos = pos;
        while(!posFIFO.isEmpty())
        {
            pos = posFIFO.remove(posFIFO.size()-1);
            curTest.setPosition(pos);
            curTest.get().set(0);
            for(int offx=-1; offx < 2; offx++)
            {
                for(int offy=-1; offy < 2; offy++)
                {
                    offpos[0] = pos[0]+offx;
                    offpos[1] = pos[1]+offy;
                    if(offpos[0] >= 0 && offpos[0]<imgSize[0] && offpos[1] >= 0 && offpos[1]<imgSize[1])
                    {
                        curTest.setPosition(offpos);
                        if(curTest.get().getRealDouble() > 127)
                        {
                            curTest.get().set(0);
                            posFIFO.add(new long[]{offpos[0], offpos[1]});
                        }
                    }
                }
            }
        }
        long[] values = {0,0};
        processImage(testImg, (point) ->{
            if(point.getRealDouble() > 127)
            {
                values[0]++;
            }
            values[1]++;
        });

        return ((double)values[0])/values[1] > FLOODINGTHRESHOLD;
    }
}
