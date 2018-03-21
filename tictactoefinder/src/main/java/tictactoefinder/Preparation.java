package tictactoefinder;

import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.DoubleType;

import java.util.Arrays;

public abstract class Preparation extends Step{
    public static final String GAUSSIANKERNEL = "0.000563\t0.000683\t0.000813\t0.000947\t0.001081\t0.001209\t0.001325\t0.001423\t0.001498\t0.001544\t0.00156\t0.001544\t0.001498\t0.001423\t0.001325\t0.001209\t0.001081\t0.000947\t0.000813\t0.000683\t0.000563\n" +
            "0.000683\t0.000829\t0.000986\t0.001149\t0.001312\t0.001467\t0.001608\t0.001727\t0.001817\t0.001874\t0.001893\t0.001874\t0.001817\t0.001727\t0.001608\t0.001467\t0.001312\t0.001149\t0.000986\t0.000829\t0.000683\n" +
            "0.000813\t0.000986\t0.001173\t0.001366\t0.00156\t0.001745\t0.001912\t0.002054\t0.002161\t0.002228\t0.002251\t0.002228\t0.002161\t0.002054\t0.001912\t0.001745\t0.00156\t0.001366\t0.001173\t0.000986\t0.000813\n" +
            "0.000947\t0.001149\t0.001366\t0.001592\t0.001817\t0.002033\t0.002228\t0.002393\t0.002518\t0.002596\t0.002622\t0.002596\t0.002518\t0.002393\t0.002228\t0.002033\t0.001817\t0.001592\t0.001366\t0.001149\t0.000947\n" +
            "0.001081\t0.001312\t0.00156\t0.001817\t0.002075\t0.002321\t0.002543\t0.002731\t0.002874\t0.002963\t0.002994\t0.002963\t0.002874\t0.002731\t0.002543\t0.002321\t0.002075\t0.001817\t0.00156\t0.001312\t0.001081\n" +
            "0.001209\t0.001467\t0.001745\t0.002033\t0.002321\t0.002596\t0.002845\t0.003055\t0.003215\t0.003315\t0.003349\t0.003315\t0.003215\t0.003055\t0.002845\t0.002596\t0.002321\t0.002033\t0.001745\t0.001467\t0.001209\n" +
            "0.001325\t0.001608\t0.001912\t0.002228\t0.002543\t0.002845\t0.003118\t0.003349\t0.003524\t0.003633\t0.00367\t0.003633\t0.003524\t0.003349\t0.003118\t0.002845\t0.002543\t0.002228\t0.001912\t0.001608\t0.001325\n" +
            "0.001423\t0.001727\t0.002054\t0.002393\t0.002731\t0.003055\t0.003349\t0.003596\t0.003784\t0.003901\t0.003941\t0.003901\t0.003784\t0.003596\t0.003349\t0.003055\t0.002731\t0.002393\t0.002054\t0.001727\t0.001423\n" +
            "0.001498\t0.001817\t0.002161\t0.002518\t0.002874\t0.003215\t0.003524\t0.003784\t0.003982\t0.004105\t0.004147\t0.004105\t0.003982\t0.003784\t0.003524\t0.003215\t0.002874\t0.002518\t0.002161\t0.001817\t0.001498\n" +
            "0.001544\t0.001874\t0.002228\t0.002596\t0.002963\t0.003315\t0.003633\t0.003901\t0.004105\t0.004233\t0.004276\t0.004233\t0.004105\t0.003901\t0.003633\t0.003315\t0.002963\t0.002596\t0.002228\t0.001874\t0.001544\n" +
            "0.00156\t0.001893\t0.002251\t0.002622\t0.002994\t0.003349\t0.00367\t0.003941\t0.004147\t0.004276\t0.00432\t0.004276\t0.004147\t0.003941\t0.00367\t0.003349\t0.002994\t0.002622\t0.002251\t0.001893\t0.00156\n" +
            "0.001544\t0.001874\t0.002228\t0.002596\t0.002963\t0.003315\t0.003633\t0.003901\t0.004105\t0.004233\t0.004276\t0.004233\t0.004105\t0.003901\t0.003633\t0.003315\t0.002963\t0.002596\t0.002228\t0.001874\t0.001544\n" +
            "0.001498\t0.001817\t0.002161\t0.002518\t0.002874\t0.003215\t0.003524\t0.003784\t0.003982\t0.004105\t0.004147\t0.004105\t0.003982\t0.003784\t0.003524\t0.003215\t0.002874\t0.002518\t0.002161\t0.001817\t0.001498\n" +
            "0.001423\t0.001727\t0.002054\t0.002393\t0.002731\t0.003055\t0.003349\t0.003596\t0.003784\t0.003901\t0.003941\t0.003901\t0.003784\t0.003596\t0.003349\t0.003055\t0.002731\t0.002393\t0.002054\t0.001727\t0.001423\n" +
            "0.001325\t0.001608\t0.001912\t0.002228\t0.002543\t0.002845\t0.003118\t0.003349\t0.003524\t0.003633\t0.00367\t0.003633\t0.003524\t0.003349\t0.003118\t0.002845\t0.002543\t0.002228\t0.001912\t0.001608\t0.001325\n" +
            "0.001209\t0.001467\t0.001745\t0.002033\t0.002321\t0.002596\t0.002845\t0.003055\t0.003215\t0.003315\t0.003349\t0.003315\t0.003215\t0.003055\t0.002845\t0.002596\t0.002321\t0.002033\t0.001745\t0.001467\t0.001209\n" +
            "0.001081\t0.001312\t0.00156\t0.001817\t0.002075\t0.002321\t0.002543\t0.002731\t0.002874\t0.002963\t0.002994\t0.002963\t0.002874\t0.002731\t0.002543\t0.002321\t0.002075\t0.001817\t0.00156\t0.001312\t0.001081\n" +
            "0.000947\t0.001149\t0.001366\t0.001592\t0.001817\t0.002033\t0.002228\t0.002393\t0.002518\t0.002596\t0.002622\t0.002596\t0.002518\t0.002393\t0.002228\t0.002033\t0.001817\t0.001592\t0.001366\t0.001149\t0.000947\n" +
            "0.000813\t0.000986\t0.001173\t0.001366\t0.00156\t0.001745\t0.001912\t0.002054\t0.002161\t0.002228\t0.002251\t0.002228\t0.002161\t0.002054\t0.001912\t0.001745\t0.00156\t0.001366\t0.001173\t0.000986\t0.000813\n" +
            "0.000683\t0.000829\t0.000986\t0.001149\t0.001312\t0.001467\t0.001608\t0.001727\t0.001817\t0.001874\t0.001893\t0.001874\t0.001817\t0.001727\t0.001608\t0.001467\t0.001312\t0.001149\t0.000986\t0.000829\t0.000683\n" +
            "0.000563\t0.000683\t0.000813\t0.000947\t0.001081\t0.001209\t0.001325\t0.001423\t0.001498\t0.001544\t0.00156\t0.001544\t0.001498\t0.001423\t0.001325\t0.001209\t0.001081\t0.000947\t0.000813\t0.000683\t0.000563\n";
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
        return res;
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
