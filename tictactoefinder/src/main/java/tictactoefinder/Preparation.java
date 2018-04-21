package tictactoefinder;

import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.DoubleType;
import java.util.ArrayList;

public abstract class Preparation extends Step{

    public static Img<DoubleType> makeImageUniform(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);
        RandomAccess<DoubleType> curOut = res.randomAccess();
        RandomAccess<DoubleType> curIn = img.randomAccess();
        int ballRadius = 25;

        //Rolling ball algorithm
        double[][] kernel = generateBallKernel(ballRadius);
        double max, value;
        long[] pos = {0,0}, kpos = {0,0};
        for(pos[0]=0; pos[0]<imgSize[0];pos[0]++)
        {
            for(pos[1]=0; pos[1]<imgSize[1];pos[1]++)
            {
                curOut.setPosition(pos);
                max=0;
                for(int x=0; x<kernel.length;x++)
                {
                    kpos[0]=pos[0]+x-ballRadius;
                    if(kpos[0] >= 0 && kpos[0] < imgSize[0])
                        for(int y=0; y<kernel[x].length;y++)
                        {
                            kpos[1]=pos[1]+y-ballRadius;
                            if(kpos[1] >=0 && kpos[1] < imgSize[1])
                            {
                                curIn.setPosition(kpos);
                                value = curIn.get().getRealDouble()*kernel[x][y];
                                if(value > max)
                                {
                                    max=value;
                                }
                            }
                        }
                }
                curIn.setPosition(pos);
                curOut.get().set(curIn.get().getRealDouble()+1 - max);
            }
        }
        return rectifyValues(res);
    }

    private static Img<DoubleType> rectifyValues(Img<DoubleType> img)
    {
        double[] boundValues = {Double.MAX_VALUE, Double.MIN_VALUE};
        processImage(img, (p)->{
            double value = p.getRealDouble();
            if(value < boundValues[0])
                boundValues[0]=value;
            if(value > boundValues[1])
                boundValues[1]=value;
        });
        if(boundValues[1] - boundValues[0] <= 0)
            return img.copy();
        return createImageFrom(img,(p)-> (p.getRealDouble() - boundValues[0])*(255/(boundValues[1]-boundValues[0])));
    }

    private static double[][] generateBallKernel(int radius) {
        int diameter = radius*2+1;
        double[][] kernel =new double[diameter][diameter];
        double distance, xdist, ydist;
        for(int x=0; x<diameter;x++)
        {
            for(int y=0;y<diameter;y++)
            {

                xdist =Math.abs((x-radius)/radius);
                ydist =Math.abs((y-radius)/radius);
                distance = Math.sqrt(xdist*xdist + ydist*ydist);
                if(distance> 1.0)
                {
                    kernel[x][y]=0;
                }else
                {
                    kernel[x][y] = Math.sin(Math.acos(distance));
                }
            }
        }

        return kernel;
    }


    /**
     * Fonction threshold
     * @param img
     * @return res la nouvelle image avec le Seuillage
     */

    public static Img<DoubleType> threshold(Img<DoubleType> img)
    {

        // Récupère la dimension de l'image
        long[] imgSize = getDimensions(img);

        // Créer une nouvelle image de la même dimension
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);

        // Tableau de déplacement
        long[] posImg={0,0};


        // Niveau de Seuillage
        double seuil=determineThreshold(img);

        // Préparation des curseurs de lecture et d'écriture pour les images
        RandomAccess<DoubleType> cursorIn = img.randomAccess();
        RandomAccess<DoubleType> cursorOut = res.randomAccess();

        for(int x = 0; x<imgSize[0];x++){
            for(int y = 0; y<imgSize[1] ; y++){

                //Affectation de la position actuelle dans un tableau
                posImg[0]=x;
                posImg[1]=y;

                //Positionnement du curseur
                cursorIn.setPosition(posImg);
                cursorOut.setPosition(posImg);

                if(cursorIn.get().getRealDouble() < seuil)
                    //Affectation de la nouvelle valeur au curseur : 0 (noir)
                    cursorOut.get().setReal(0);
                else
                    //Affectation de la nouvelle valeur au curseur : 255 (blanc)
                    cursorOut.get().setReal(255);
            }
        }

        return res;
    }

    private static int determineThreshold(Img<DoubleType> img) {

        long[] imgSize = getDimensions(img);
        int[] histogram = new int[256];
        RandomAccess<DoubleType> cur = img.randomAccess();
        long[] pos = {0,0};
        double value;
        int finalValue;
        for(pos[0]=0; pos[0]<imgSize[0];pos[0]++)
        {
            for(pos[1]=0; pos[1]<imgSize[1];pos[1]++)
            {
                cur.setPosition(pos);
                value = cur.get().getRealDouble();
                if(value <= 0)
                {
                    finalValue=0;
                }else if(value >= 255)
                {
                    finalValue=255;
                }else
                {
                    finalValue = (int)value;
                }
                histogram[finalValue]++;
            }
        }
        long numberOfPixels = imgSize[0]*imgSize[1];
        int threshold, bestThreshold=0;
        double numb=0, numf=0, sumb=0, sumf=0, bestValue=0, wb, wf, mb, mf, current;
        for(int i=0; i<histogram.length;i++)
        {
            numf += histogram[i];
            sumf += i*histogram[i];
        }
        for(threshold=1; threshold<256;threshold++)
        {
            numb+=histogram[threshold-1];
            numf-=histogram[threshold-1];
            sumb+=(threshold-1)*histogram[threshold-1];
            sumf-=(threshold-1)*histogram[threshold-1];

            wb = numb/numberOfPixels;
            wf = numf/numberOfPixels;
            mb = numb != 0 ? sumb/numb : 0;
            mf = numf != 0 ? sumf/numf : 0;

            current = wb*wf*(mb-mf)*(mb-mf);

            if(current >= bestValue)
            {
                bestValue=current;
                bestThreshold=threshold;
            }


        }
        return bestThreshold;
    }


    public static Img<DoubleType> crop(Img<DoubleType> img)
    {
        long[] imgSize = getDimensions(img);

        //Détermination des bords
        RandomAccess<DoubleType> cursorIn = img.randomAccess();
        long xmin=imgSize[0], xmax = 0, ymin=imgSize[1], ymax=0;
        long[] pos = {0,0};
        for(long x=0; x<imgSize[0];x++)
        {
            for(long y=0; y<imgSize[1];y++)
            {
                pos[0] = x;
                pos[1] = y;
                cursorIn.setPosition(pos);
                if(cursorIn.get().getRealDouble()==0)
                {
                    if(x < xmin)
                        xmin = x;
                    if(x > xmax)
                        xmax = x;
                    if(y < ymin)
                        ymin = y;
                    if(y > ymax)
                        ymax = y;
                }
            }
        }
        if(xmax < xmin || ymax < ymin)
        {
            log(Preparation.class,ERROR, "TicTacToe not detected");
        }

        //On copie
        long[] newImgSize = {(xmax-xmin)+1, (ymax-ymin)+1};
        Img<DoubleType> res = ArrayImgs.doubles(newImgSize);
        RandomAccess<DoubleType> cursorOut = res.randomAccess();
        long [] outPos = {0,0};
        for(int x=0;x < newImgSize[0]; x++)
        {
            for(int y=0; y<newImgSize[1]; y++)
            {
                outPos[0] = x;
                pos[0] = x+xmin;
                outPos[1] = y;
                pos[1] = y+ymin;
                cursorIn.setPosition(pos);
                cursorOut.setPosition(outPos);
                cursorOut.get().set(cursorIn.get().getRealDouble());
            }
        }
        return res;
    }

    public static Img<DoubleType> swap(Img<DoubleType> img) {
        long[] dim = getDimensions(img);
        long counter=0;
        RandomAccess<DoubleType> cur = img.randomAccess();
        long[] pos = new long[]{0,0};
        for(int i=0; i<dim[0];i++)
        {
            pos[0]=i;
            for(int j=0; j<dim[1];j++)
            {
                pos[1]=j;
                cur.setPosition(pos);
                if(cur.get().getRealDouble()>127)
                    counter++;
            }
        }
        if(counter > dim[0]*dim[1]/2)
            return img;

        Img<DoubleType> res = ArrayImgs.doubles(dim);
        RandomAccess<DoubleType> curOut = res.randomAccess();
        for(int i=0; i<dim[0];i++)
        {
            pos[0]=i;
            for(int j=0; j<dim[1];j++)
            {
                pos[1]=j;
                cur.setPosition(pos);
                curOut.setPosition(pos);

                curOut.get().set(255-cur.get().getRealDouble());
            }
        }
        return res;
    }


}
