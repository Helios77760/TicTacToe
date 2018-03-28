package tictactoefinder;

import ij.plugin.HyperStackMaker;
import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.DoubleType;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import java.util.ArrayList;

@Plugin(type = Command.class, menuPath = "Plugins>TicTacToe Test>Test Split")
public class TestSplit implements Command {
    @Parameter
    DatasetService ds;

    @Parameter(label = "Image du TicTacToe")
    Dataset data;

    @Parameter(type= ItemIO.OUTPUT)
    Dataset out0;
    @Parameter(type= ItemIO.OUTPUT)
    Dataset out1;
    @Parameter(type= ItemIO.OUTPUT)
    Dataset out2;
    @Parameter(type= ItemIO.OUTPUT)
    Dataset out3;
    @Parameter(type= ItemIO.OUTPUT)
    Dataset out4;
    @Parameter(type= ItemIO.OUTPUT)
    Dataset out5;
    @Parameter(type= ItemIO.OUTPUT)
    Dataset out6;
    @Parameter(type= ItemIO.OUTPUT)
    Dataset out7;
    @Parameter(type= ItemIO.OUTPUT)
    Dataset out8;


    @Override
    public void run() {
        Img<DoubleType> img = TicTacToeFinder.datasetToImgDouble(data);
        ArrayList<Img<DoubleType>> imgs = Spliting.split(img,Spliting.detection(img));
        out0 = ds.create(imgs.get(0));
        out1 = ds.create(imgs.get(1));
        out2 = ds.create(imgs.get(2));
        out3 = ds.create(imgs.get(3));
        out4 = ds.create(imgs.get(4));
        out5 = ds.create(imgs.get(5));
        out6 = ds.create(imgs.get(6));
        out7 = ds.create(imgs.get(7));
        out8 = ds.create(imgs.get(8));


    }
}
