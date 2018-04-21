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

@Plugin(type = Command.class, menuPath = "Plugins>TicTacToe Test>Test Preparation")
public class TestPreparation implements Command {

    @Parameter
    DatasetService ds;

    @Parameter(label = "Image du TicTacToe")
    Dataset img;

    @Parameter(type= ItemIO.OUTPUT)
    Dataset out;

    @Parameter(label = "Fonction à tester", choices = {"makeImageUniform", "threshold", "clean", "crop", "swap", "unrotate", "eraseLines"})
    String testChoice;

    public static String[] choices = {
            "makeImageUniform",
            "threshold",
            "clean",
            "crop",
            "swap",
            "unrotate",
            "eraseLines"};

    public Test[] functions = {
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Preparation.makeImageUniform(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Preparation.threshold(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Cleaning.clean(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Preparation.crop(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Preparation.swap(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Improvement.unrotate(img));}},
            new Test(){public void run(Img<DoubleType> img){out = ds.create(Spliting.eraseLines(img, Spliting.detection(img)));}}
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
