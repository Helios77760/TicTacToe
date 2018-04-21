package tictactoefinder;

import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

import java.util.ArrayList;

import static tictactoefinder.Step.getDimensions;
import static tictactoefinder.Step.log;

@Plugin(type = Command.class, menuPath = "Plugins>TicTacToe Finder")
public class TicTacToeFinder implements Command {

    @Parameter
    DatasetService ds;

    @Parameter(label = "Image du TicTacToe")
    Dataset img;

    @Parameter
    LogService logService;

    @Override
    public void run() {
        computeTicTacToe(img);
    }

    private void computeTicTacToe(Dataset img) {
        Step.logging=logService;
        long start = System.currentTimeMillis();

        log(TicTacToeFinder.class, Step.WARN, "Analyze commencée...");
        Img<DoubleType> res = datasetToImgDouble(img);

        //Preparation
        log(TicTacToeFinder.class, Step.INFO, "Uniformisation de l'éclairage...");
        res = Preparation.makeImageUniform(res);
        log(TicTacToeFinder.class, Step.INFO, "Binarisation...");
        res = Preparation.threshold(res);
        log(TicTacToeFinder.class, Step.INFO, "Inversion si besoin...");
        res = Preparation.swap(res);
        log(TicTacToeFinder.class, Step.INFO, "Nettoyage...");
        res = Cleaning.clean(res);
        log(TicTacToeFinder.class, Step.INFO, "Rognage...");
        res = Preparation.crop(res);
        log(TicTacToeFinder.class, Step.INFO, "Rectification de l'angle...");
        res = Improvement.unrotate(res);

        //Decoupage
        log(TicTacToeFinder.class, Step.INFO, "Détection de la grille...");
        long[][] borders = Spliting.detection(res);
        log(TicTacToeFinder.class, Step.INFO, "Découpage de la grille...");
        ArrayList<Img<DoubleType>> tiles = Spliting.split(res, borders);

        Integer[] matrix = new Integer[9];
        log(TicTacToeFinder.class, Step.INFO, "Détection des cases en cours...\n");
        for(int i=0; i<9;i++)
        {
            //Detection
            if(!DetectContent.isEmpty(tiles.get(i)))
            {
                matrix[i] = DetectContent.isCircleByFlooding(tiles.get(i)) ? 1 : -1;
            }else
            {
                matrix[i] = 0;
            }
        }

        //Synthese
        Synthesis.showResults(matrix);
        long end = System.currentTimeMillis();
        log(TicTacToeFinder.class, Step.WARN, "Analyze terminée en "+ String.format("%.3f",(end-start)/1000.0) + "s\n");
    }

    public static Img<DoubleType> datasetToImgDouble(Dataset image)
    {
        long[] imgSize = getDimensions(image);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize[0], imgSize[1]);
        RandomAccess<? extends RealType> cursorIn = image.randomAccess();
        RandomAccess<DoubleType> cursorOut = res.randomAccess();

        long[] posIn = new long[image.numDimensions()], posOut = new long[2];

        for(int x=0; x<imgSize[0];x++)
        {
            for(int y=0; y<imgSize[1];y++)
            {
                posIn[0] = posOut[0] = x;
                posIn[1] = posOut[1] = y;
                cursorOut.setPosition(posOut);
                cursorIn.setPosition(posIn);
                cursorOut.get().set(cursorIn.get().getRealDouble());
            }
        }
        return res;
    }

}
