import java.util.Arrays;

public class Minesweeper {
   public static void main(String[] args) {

      MinesweeperWindows mw = new MinesweeperWindows();
      //调用showDialog方法，显示选项对话框

      System.out.println(Arrays.deepToString(mw.getDifficultChoice()));

   }

}
