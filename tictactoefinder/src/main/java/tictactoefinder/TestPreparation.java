package tictactoefinder;

import net.imglib2.img.array.ArrayImgs;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.DoubleType;

import java.util.Arrays;

interface Test
{
    void run(Img<DoubleType> img);
}

@Plugin(type = Command.class, menuPath = "Plugins>TicTacToe Test>Test Preparation")
public class TestPreparation implements Command {



    @Parameter
    DatasetService ds;

    @Parameter(label = "Image du TicTacToe")
    Dataset img;

    @Parameter(type= ItemIO.OUTPUT)
    Dataset out;

    @Parameter(label = "Fonction à tester", choices = {"makeImageUniform", "threshold", "clean", "crop", "swap"})
    String testChoice;

    public static String[] choices = {
            "makeImageUniform",
            "threshold",
            "clean",
            "crop",
            "swap",};

    public Test[] functions = {
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Preparation.makeImageUniform(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Preparation.threshold(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Preparation.clean(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Preparation.crop(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Preparation.swap(img));}}
    };

    @Override
    public void run() {
        Img<DoubleType> testImg = TicTacToeFinder.datasetToImgDouble(img);
        int index = Arrays.asList(choices).indexOf(testChoice);
        if(index == -1)
        {
            out = ds.create(ArrayImgs.doubles(10,10));
        }else
        {
            functions[index].run(testImg);
        }
    }

}
