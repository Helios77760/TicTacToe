package tictactoefinder;

import org.scijava.command.Command;
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

@Plugin(type = Command.class, menuPath = "Plugins>TicTacToe Finder")
public class TicTacToeFinder implements Command {

    @Parameter
    DatasetService ds;

    @Parameter(label = "Image du TicTacToe")
    Dataset img;

    @Parameter(label = "Template cercle")
    Dataset templateCircle;

    @Parameter(label = "Template croix")
    Dataset templateCross;

    @Override
    public void run() {
        computeTicTacToe(img, templateCircle, templateCross);
    }

    private void computeTicTacToe(Dataset img, Dataset templateCircle, Dataset templateCross) {

        //RandomAccess<? extends RealType<?>> imgCursor = img.randomAccess();


        Img<DoubleType> res = datasetToImgDouble(img);
        Img<DoubleType> circle = datasetToImgDouble(templateCircle);
        Img<DoubleType> cross = datasetToImgDouble(templateCross);

        //Preparation
        res = Preparation.makeImageUniform(res);
        res = Preparation.threshold(res);
        res = Preparation.clean(res);
        res = Preparation.crop(res);

        //Decoupage
        long[][] borders = Spliting.detection(res);
        ArrayList<Img<DoubleType>> tiles = Spliting.split(res, borders);

        Integer[] matrix = new Integer[9];

        for(int i=0; i<9;i++)
        {
            //Detection
            if(!DetectContent.isEmpty(tiles.get(i)))
            {
                matrix[i] = DetectContent.isCircleByRegistration(tiles.get(i),circle, cross ) ? 1 : -1;
            }else
            {
                matrix[i] = 0;
            }

        }

        //Synthese
        Synthesis.showBoardAsMatrix(matrix);
        Synthesis.whoIsWinning(matrix);
    }

    private Img<DoubleType> datasetToImgDouble(Dataset image)
    {
        long[] imgSize = getDimensions(image);
        Img<DoubleType> res = ArrayImgs.doubles(imgSize);

        //TODO

        return res;
    }

}
