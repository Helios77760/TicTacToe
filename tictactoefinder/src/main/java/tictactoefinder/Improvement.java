package tictactoefinder;

import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;

import java.util.ArrayList;

public class Improvement {
    public static Img<DoubleType> rotate(Img<DoubleType> img, int angle)
    {
        if(angle == 0)
            return img.copy();
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        long[] oldDim = Step.getDimensions(img);
        long[] dim = new long[]{(long)Math.ceil(cos*oldDim[0] + sin*oldDim[1]), (long)Math.ceil(sin*oldDim[0] + cos*oldDim[1])};
        long[] imgPos = new long[2];
        long[] imgCenter = new long[]{oldDim[0]/2,oldDim[1]/2};
        long[] resCenter = new long[]{dim[0]/2,dim[1]/2};
        Img<DoubleType> res = ArrayImgs.doubles(dim);
        RandomAccess<DoubleType> curIn = img.randomAccess(), curOut = res.randomAccess();
        for(imgPos[0]=0; imgPos[0]<dim[0];imgPos[0]++)
        {
            for(imgPos[1]=0; imgPos[1]<dim[1]; imgPos[1]++)
            {
                curOut.setPosition(imgPos);
                curOut.get().set(255);
            }
        }
        for(int x=0; x < oldDim[0]; x++)
        {
            for(int y=0; y<oldDim[1];y++)
            {
                imgPos[0]=x;
                imgPos[1]=y;
                curIn.setPosition(imgPos);
                double value = curIn.get().getRealDouble();
                toCentered(imgPos, imgCenter);
                rotate(imgPos, cos, sin);
                toNormal(imgPos, resCenter);
                if(imgPos[0] >= 0 && imgPos[0]<=dim[0] && imgPos[1]>=0 && imgPos[1]<=dim[1])
                {
                    curOut.setPosition(imgPos);
                    curOut.get().set(value);
                }
            }
        }
        return res;
    }

    private static void toCentered(long[] pos, long[] center)
    {
        assert pos.length == 2 && center.length == 2;
        pos[0] = pos[0]-center[0];
        pos[1] = center[1]-pos[1];
    }

    private static void toNormal(long[] pos, long[] center)
    {
        assert pos.length == 2 && center.length == 2;
        pos[0] = pos[0]+center[0];
        pos[1] = center[1]-pos[1];
    }

    private static void rotate(long[] pos, double cos, double sin)
    {
        assert pos.length == 2;
        long x = pos[0];
        pos[0] = (long)(x*cos - pos[1]*sin);
        pos[1] = (long)(x*sin + pos[1]*cos);
    }

    public static Img<DoubleType> unrotate(Img<DoubleType> img)
    {
        long[] dim = Step.getDimensions(img);
        //Projections avec angle
        double[] projectionValues = new double[90];
        ArrayList<Double> tempProjections = new ArrayList<>();
        ArrayList<Long> tempCount = new ArrayList<>();
        double maxCount=0;
        double maxProjection;
        double current;
        long count;
        boolean valid;
        int bestAngle=0;
        RandomAccess<DoubleType> curIn = img.randomAccess();
        for(int angle=0; angle < 90; angle++)
        {
            maxProjection=-Double.MAX_VALUE;
            tempProjections.clear();
            tempCount.clear();
            double rAngle = Math.toRadians(angle);
            double tan = Math.tan(rAngle);
            if(angle <=45)
            {
                for(long yoff=(long)-((dim[0]-1)*tan); yoff < dim[0]; yoff++)
                {
                    current=0;
                    count=0;
                    for(long x=0;x<dim[0];x++)
                    {
                        double yd = x*tan + yoff;
                        if(yd >= 0)
                        {
                            valid=false;
                            long y=(long)yd;
                            double sup = yd-y;
                            if(y >= 0 && y <dim[1])
                            {
                                current+=(255-valueOf(x,y,curIn))*(1-sup);
                                valid=true;
                            }
                            if(y+1 >=0 && y+1 <dim[1])
                            {
                                current+=(255-valueOf(x,y+1, curIn))*sup;
                                valid=true;
                            }
                            if(valid)
                            {
                                count++;
                            }
                        }
                    }
                    tempCount.add(count);
                    if(count > maxCount)
                    {
                        maxCount=count;
                    }
                    tempProjections.add(count > 0 ? current/count : 0);
                }
            }else
            {
                for(long xoff=(long)-((dim[1]-1)/tan); xoff < dim[1]; xoff++)
                {
                    current=0;
                    count=0;
                    for(long y=0;y<dim[1];y++)
                    {
                        double xd = y/tan + xoff;
                        if(xd >= 0)
                        {
                            valid=false;
                            long x=(long)xd;
                            double sup = xd-x;
                            if(x >= 0 && x <dim[0])
                            {
                                current+=(255-valueOf(x,y,curIn))*(1-sup);
                                valid=true;
                            }
                            if(x+1 >=0 && x+1 <dim[0])
                            {
                                current+=(255-valueOf(x+1,y, curIn))*sup;
                                valid=true;
                            }
                            if(valid)
                            {
                                count++;
                            }
                        }
                    }
                    tempCount.add(count);
                    if(count > maxCount)
                    {
                        maxCount=count;
                    }
                    tempProjections.add(count > 0 ? current/count : 0);
                }
            }

            for(int i=0; i<tempProjections.size();i++)
            {
                double value = tempProjections.get(i)*Math.pow(tempCount.get(i)/maxCount,2);
                if(value > maxProjection)
                {
                    maxProjection=value;
                }
            }
            projectionValues[angle] = maxProjection;
        }
        maxProjection=-Double.MAX_VALUE;
        for(int i=0; i<90;i++)
        {
            if(projectionValues[i] > maxProjection)
            {
                bestAngle=i;
                maxProjection=projectionValues[i];
            }
        }

        return Cleaning.open(Cleaning.close(Preparation.crop(rotate(img,bestAngle)),1),1);
    }

    private static double valueOf(long x, long y, RandomAccess<DoubleType> cur)
    {
        cur.setPosition(new long[]{x,y});
        return cur.get().getRealDouble();
    }
}
