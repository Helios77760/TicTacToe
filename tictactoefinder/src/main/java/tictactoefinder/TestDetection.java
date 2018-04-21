package tictactoefinder;

import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.ByteType;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.DoubleType;

import java.util.Arrays;
import java.util.Random;

@Plugin(type = Command.class, menuPath = "Plugins>TicTacToe Test>Test Detection")
public class TestDetection implements Command {

    @Parameter
    DatasetService ds;

    @Parameter(label = "Image du TicTacToe")
    Dataset img;

    @Parameter(type= ItemIO.OUTPUT)
    Dataset out;

    @Parameter(label = "Fonction à tester", choices = {"isEmpty", "isCircleByRegistration", "isCrossByAngleProjection", "isCircleByFlooding"})
    String testChoice;

    static boolean value;

    public static String[] choices = {"isEmpty", "isCircleByRegistration", "isCrossByAngleProjection", "isCircleByFlooding"};

    public Test[] functions = {
            new Test(){public void run(Img<DoubleType> img){value = DetectContent.isEmpty(img);}},
            new Test(){public void run(Img<DoubleType> img){value = DetectContent.isCircleByRegistration(img);}},
            new Test(){public void run(Img<DoubleType> img){value = DetectContent.isCrossByAngledProjection(img);}},
            new Test(){public void run(Img<DoubleType> img){value = DetectContent.isCircleByFlooding(img);}},
    };

    @Override
    public void run() {
        Img<DoubleType> testImg = TicTacToeFinder.datasetToImgDouble(img);
        int index = Arrays.asList(choices).indexOf(testChoice);

        if(index == -1)
        {
            out = ds.create(ArrayImgs.doubles(100,100));
        }else
        {
            Img<ByteType> res = ArrayImgs.bytes(100,100);
            RandomAccess<ByteType> cur = res.randomAccess();
            long[] pos = {0,0};
            functions[index].run(testImg);
            Random rand = new Random();
            for(int i=0; i<100; i++)
            {
                for(int j=0; j<100;j++)
                {
                    pos[0]=i;
                    pos[1]=j;
                    cur.setPosition(pos);
                    //cur.get().set((byte)rand.nextInt(2));
                    cur.get().set(value ? (byte)1 : (byte)0);
                }
            }
            pos[0] = pos[1] = 0;
            cur.setPosition(pos);
            cur.get().set(value ? (byte)0 : (byte)1);
            out = ds.create(res);
        }
    }

}
