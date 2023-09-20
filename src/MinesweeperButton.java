import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinesweeperButton extends JButton {
    private final int status; // -1是雷，0-8为附近的雷数
    private final int xLocation;
    private final int yLocation;
    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            //左键点击
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (status == -1) {
                    //点击后设置为爆炸的雷的图标
                    setIcon(new ImageIcon("./src/Themes/Classic/Button_Mine_exploded.png"));
                } else {
                    //设置为0-8雷数的图标
                    setIcon(new ImageIcon("./src/Themes/Classic/Button_" + status + ".png"));
                }
            //右键点击
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                //旗子图标
                setIcon(new ImageIcon("./src/Themes/Classic/Button_flag.png"));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };
    private boolean minesVisible = false;//false为不可见，true为可见

    public MinesweeperButton(int xLocation, int yLocation, boolean[][] status) {
        //调用JButton类的无参构造方法，创建一个没有文本或图标的按钮
        super();
        //默认为未点击图标
        setIcon(new ImageIcon("./src/Themes/Classic/Button.png"));
        //传入按钮的xy坐标，记录按钮位置
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        if (status[yLocation][xLocation]) {
            this.status = -1;
        } else {
            this.status = calculateNearbyMines(status);
        }
        //添加按钮的点击事件，可以根据自己的逻辑来实现
        addMouseListener(mouseListener);
    }

    public void setMinesVisible(boolean minesVisible) {
        this.minesVisible = minesVisible;
    }

    //计算周围雷的数量的方法
    private int calculateNearbyMines(boolean[][] mines) {
        //计数器
        int numbers = 0;
        //计算九宫格内的地雷数量
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