import javax.swing.*;

public class MinesweeperButton extends JButton {
    private boolean status = false;//false为空，true为雷
    private boolean minesVisible = false;//false为不可见，true为可见

    public MinesweeperButton(boolean status) {
        //调用JButton类的无参构造方法，创建一个没有文本或图标的按钮
        super();
        this.status = status;
        //添加按钮的点击事件，可以根据自己的逻辑来实现
        this.addActionListener(e -> {
            //TODO: 按钮被点击时要执行的操作,如果是雷就全部爆炸，如果为空，如果周围有雷，显示雷的数量
        });
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMinesVisible(boolean minesVisible) {
        this.minesVisible = minesVisible;
    }
}

