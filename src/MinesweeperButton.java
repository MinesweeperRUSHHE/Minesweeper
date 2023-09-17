import javax.swing.*;

public class MinesweeperButton extends JButton {
    private final int status; // -1是雷，0-8为附近的雷数
    private final int xLocation;
    private final int yLocation;
    private boolean minesVisible = false;//false为不可见，true为可见

    public MinesweeperButton(int xLocation, int yLocation, boolean[][] status) {
        //调用JButton类的无参构造方法，创建一个没有文本或图标的按钮
        super();
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        if (status[yLocation][xLocation]) {
            this.status = -1;
        } else {
            this.status = calculateNearbyMines(status);
        }
        //添加按钮的点击事件，可以根据自己的逻辑来实现
        this.addActionListener(e -> {
            //TODO: 按钮被点击时要执行的操作,如果是雷就全部爆炸，如果为空，如果周围有雷，显示雷的数量
            if (this.status != -1) {
                super.setText(String.valueOf(this.status));
            } else {
                super.setText("*");
                super.setEnabled(false);
            }
        });
    }

    public void setMinesVisible(boolean minesVisible) {
        this.minesVisible = minesVisible;
    }

    private int calculateNearbyMines(boolean[][] mines) {
        int numbers = 0;

        for (int i = yLocation - 1; i <= yLocation + 1; i++) {
            for (int j = xLocation - 1; j <= xLocation + 1; j++) {
                if (!((i < 0 || i >= mines.length) || (j < 0 || j >= mines[0].length)) && mines[i][j]) {
                    numbers++;
                }
            }
        }
        return numbers;
    }
}

