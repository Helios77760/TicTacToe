package tictactoefinder;

import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.UnsignedByteType;
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
        double seuil=125;

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
            for(int y=0; y<newImgSize[0]; y++)
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
}
