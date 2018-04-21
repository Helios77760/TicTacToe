package tictactoefinder;

import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;

public class Cleaning {
    public static Img<DoubleType> clean(Img<DoubleType> img)
    {
        return open(img, 1);
    }

    public static Img<DoubleType> open(Img<DoubleType> img, int kernelSize)
    {
        if(kernelSize < 1)
            return img.copy();
        double[][] kernel = generateCleanKernel(kernelSize);
        return dilatation(erosion(img, kernel), kernel);
    }

    public static Img<DoubleType> close(Img<DoubleType> img, int kernelSize)
    {
        if(kernelSize < 1)
            return img.copy();
        double[][] kernel = generateCleanKernel(kernelSize);
        return erosion(dilatation(img, kernel), kernel);
    }

    private static double[][] generateCleanKernel(int size)
    {
        size = size*2+1;
        double [][] kernel = new double[size][size];
        for(int x=0; x<size;x++)
        {
            for(int y=0; y<size;y++)
            {
                kernel[x][y] = 255;
            }
        }
        return kernel;
    }

    public static Img<DoubleType> dilatation(Img<DoubleType> img, double [][] kernel) {

        boolean stopKernel = false;

        int xcenter = kernel.length / 2, ycenter;

        long[] imgSize = Step.getDimensions(img);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);

        RandomAccess<DoubleType> cursorImg = img.randomAccess();
        RandomAccess<DoubleType> cursorRes = res.randomAccess();
        RandomAccess<DoubleType> cursorImg2 = img.randomAccess();


        long[] pos = {0, 0}, kpos = {0,0};

        for (int imgX = 0; imgX < imgSize[0]; imgX++) {
            pos[0] = imgX;

            for (int imgY = 0; imgY < imgSize[1]; imgY++) {

                pos[1] = imgY;

                cursorImg.setPosition(pos);
                cursorRes.setPosition(pos);

                stopKernel = false;

                for (int xKernel = 0; xKernel < kernel.length; xKernel++) {
                    kpos[0] = imgX + xKernel - xcenter;

                    if (kpos[0] >= 0 && kpos[0] < imgSize[0]) {
                        ycenter = kernel[xKernel].length / 2;
                        for (int yKernel = 0; yKernel < kernel[xKernel].length; yKernel++) {
                            kpos[1] = imgY + yKernel - ycenter;

                            if (kpos[1] >= 0 && kpos[1] < imgSize[1]) {
                                cursorImg2.setPosition(kpos);
                                if (cursorImg2.get().getRealDouble() < kernel[xKernel][yKernel]) {
                                    cursorRes.get().setReal(0);
                                    stopKernel = true;
                                    break;
                                } else
                                    cursorRes.get().setReal(255);
                            }
                        }
                        if (stopKernel)
                            break;
                    }
                }
            }
        }
        return res;
    }

    public static Img<DoubleType> erosion(Img<DoubleType> img, double [][] kernel ){


        boolean stopKernel = false;

        int xcenter = kernel.length / 2, ycenter;

        long[] imgSize = Step.getDimensions(img);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);

        RandomAccess<DoubleType> cursorImg = img.randomAccess();
        RandomAccess<DoubleType> cursorRes = res.randomAccess();
        RandomAccess<DoubleType> cursorImg2 = img.randomAccess();


        long[] pos = {0, 0}, kpos = {0,0};

        for (int imgX = 0; imgX < imgSize[0]; imgX++) {
            pos[0] = imgX;

            for (int imgY = 0; imgY < imgSize[1]; imgY++) {


                pos[1] = imgY;

                cursorImg.setPosition(pos);
                cursorRes.setPosition(pos);

                stopKernel = false;

                for (int xKernel = 0; xKernel < kernel.length; xKernel++) {
                    kpos[0] = imgX + xKernel - xcenter;

                    if (kpos[0] >= 0 && kpos[0] < imgSize[0]) {
                        ycenter = kernel[xKernel].length / 2;
                        for (int yKernel = 0; yKernel < kernel[xKernel].length; yKernel++) {
                            kpos[1] = imgY + yKernel - ycenter;

                            if (kpos[1] >= 0 && kpos[1] < imgSize[1]) {
                                cursorImg2.setPosition(kpos);
                                if (!(cursorImg2.get().getRealDouble() < kernel[xKernel][yKernel])) {
                                    cursorRes.get().setReal(255);
                                    stopKernel = true;
                                    break;
                                }
                            }
                        }
                        if (stopKernel)
                            break;
                    }
                }
                if(!stopKernel){
                    cursorRes.get().setReal(0);
                }
            }
        }
        return res;
    }
}
