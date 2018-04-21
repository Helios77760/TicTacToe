package tictactoefinder;

public class Synthesis extends Step {
    public static void showBoardAsMatrix(Integer[] matrix)
    {
        StringBuilder sb = new StringBuilder("\n");
        for(int i=0;i<3;i++)
        {
            for(int j=0; j<3;j++)
            {
                Integer value = matrix[i*3+j];
                sb.append(value == 0 ? " ": value > 0 ? "O":"X");
            }
            sb.append("\n");
        }
        log(Synthesis.class,INFO, sb.toString());
    }
    public static void whoIsWinning(Integer[] matrix)
    {
        String winning = "";
        if(matrix[0] != 0 && matrix[0].equals(matrix[1]) && matrix[0].equals(matrix[2])) //horizontal
        {
            winning = matrix[0] > 0 ? "O":"X";
        }else if(matrix[3] != 0 && matrix[3].equals(matrix[4]) && matrix[3].equals(matrix[5]))
        {
            winning = matrix[3] > 0 ? "O":"X";
        }else if(matrix[6] != 0 && matrix[6].equals(matrix[7]) && matrix[6].equals(matrix[8]))
        {
            winning = matrix[6] > 0 ? "O":"X";
        }else if(matrix[0] != 0 && matrix[0].equals(matrix[3]) && matrix[0].equals(matrix[6])) //vertical
        {
            winning = matrix[0] > 0 ? "O":"X";
        }else if(matrix[1] != 0 && matrix[1].equals(matrix[4]) && matrix[1].equals(matrix[7]))
        {
            winning = matrix[1] > 0 ? "O":"X";
        }else if(matrix[2] != 0 && matrix[2].equals(matrix[5]) && matrix[2].equals(matrix[8]))
        {
            winning = matrix[2] > 0 ? "O":"X";
        }else if(matrix[0] != 0 && matrix[0].equals(matrix[4]) && matrix[0].equals(matrix[8])) //diagonal
        {
            winning = matrix[0] > 0 ? "O":"X";
        }else if(matrix[2] != 0 && matrix[2].equals(matrix[4]) && matrix[2].equals(matrix[6]))
        {
            winning = matrix[2] > 0 ? "O":"X";
        }

        if(winning.equals(""))
        {
            log(Synthesis.class, INFO, "No winner yet\n");
        }else
        {
            log(Synthesis.class, INFO, winning + " player won\n");
        }

    }
}
