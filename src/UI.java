import javax.swing.*;
import java.awt.*;


public class UI extends JFrame {
    private JTextField problem[][] = new JTextField[9][9];
    private int answer_player[][] = new int[9][9];
    private Logic logic = new Logic();

    public UI() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout(2, 1));
        JMenuItem verify = new JMenuItem("验证");
        JMenuItem message = new JMenuItem("介绍");
        JMenuItem answer_computer = new JMenuItem("答案");
        JPanel menuPanel = new JPanel();
        menuPanel.add(verify);
        menuPanel.add(message);
        menuPanel.add(answer_computer);
        JPanel boardPanel = new JPanel(new GridLayout(9, 9, 5, 5));
        add(menuPanel, BorderLayout.SOUTH);
        add(boardPanel, BorderLayout.CENTER);
        int[][] problem_array = logic.GL();
        updateBoard(problem_array, boardPanel);
        add(boardPanel);
        verify.addActionListener(e -> {
            if (checkInput()) {
                if (logic.check(problem_array, answer_player))
                    JOptionPane.showMessageDialog(null, "太棒了，完全正确！", "结果", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(null, "很遗憾，你的答案不正确。", "结果", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        message.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "该数独游戏分为三个难度等级，新手，专业和世界最难，可在模式里切换", "介绍", JOptionPane.INFORMATION_MESSAGE);
        });
        answer_computer.addActionListener(e -> {
            int[][] answerAI = logic.SOC(problem_array);
            updateBoard(answerAI, boardPanel);
        });
    }

    private boolean checkInput() {
        clearArray(answer_player);
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                try {
                    answer_player[row][column] = Integer.parseInt(problem[row][column].getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "输入中有非数字项，请重新输入。");
                    return false;
                }
            }
        }
        return true;
    }

    private void clearArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = 0;
            }
        }
    }

    private void updateBoard(int[][] new_board, JPanel boardPanel){
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (new_board[row][column] != 0) {
                    problem[row][column] = new JTextField(" " + new_board[row][column]);
                    problem[row][column].setHorizontalAlignment(JTextField.CENTER);
                    problem[row][column].setEditable(false);
                    boardPanel.add(problem[row][column]);
                } else {
                    problem[row][column] = new JTextField();
                    problem[row][column].setHorizontalAlignment(JTextField.CENTER);
                    boardPanel.add(problem[row][column]);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new UI();
        frame.setTitle("数独小游戏");
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}