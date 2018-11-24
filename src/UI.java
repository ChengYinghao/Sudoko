import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class UI extends JFrame {
    private JTextField problem[][] = new JTextField[9][9];
    private int answer_player[][] = new int[9][9];
    private Logic logic = new Logic();

    private UI() {
        logic.NL(); //默认为随机出题
        int[][] problem_array = logic.problem;
        //调试部分
//        logic.printArray(problem_array);
//        logic.printArray(logic.SOC());
        update(problem_array);
    }

    private void update(int[][] new_board) {
        Container container = getContentPane();
        container.setLayout(new BorderLayout(2, 1));
        JMenuItem verify = new JMenuItem("验证");
        JMenuItem message = new JMenuItem("介绍");
        JMenuItem answer_computer = new JMenuItem("答案");
        JMenuItem challenge = new JMenuItem("挑战世界最难题");
        JPanel menuPanel = new JPanel();
        menuPanel.add(verify);
        menuPanel.add(message);
        menuPanel.add(answer_computer);
        menuPanel.add(challenge);
        JPanel boardPanel = new JPanel(new GridLayout(9, 9, 10, 10));
        add(menuPanel, BorderLayout.SOUTH);
        add(boardPanel, BorderLayout.CENTER);
        updateBoard(new_board, boardPanel);
        add(boardPanel);
        verify.addActionListener(e -> {
            if (checkInput()) {
                if (logic.check(answer_player))
                    JOptionPane.showMessageDialog(null, "太棒了，完全正确！", "结果", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(null, "很遗憾，你的答案不正确。", "结果", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        message.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "1.0版数独小游戏。有基础模式和一个挑战题（世界最难）", "介绍", JOptionPane.INFORMATION_MESSAGE);
        });
        answer_computer.addActionListener(e -> {
            int[][] answerAI = logic.SOC();
            update(answerAI);
        });
        challenge.addActionListener(e -> {
            logic.GL();
            int[][] gl = logic.problem;
            update(gl);
        });
        container.validate();

    }

    private boolean checkInput() {
        clearArray(answer_player);

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                String s = problem[row][column].getText();
                if (s.equals("")) {
                    JOptionPane.showMessageDialog(null, "尚未填满。");
                }
//                System.out.println(s+";"); //调试
                try {
                    answer_player[row][column] = Integer.parseInt(s);
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

    private void updateBoard(int[][] new_board, JPanel boardPanel) {
        problem = new JTextField[9][9];
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (new_board[row][column] != 0) {
                    problem[row][column] = new JTextField("" + new_board[row][column]);
                    problem[row][column].setHorizontalAlignment(JTextField.CENTER);
                    problem[row][column].setEditable(false);
//                    problem[row][column].setEnabled(false);
                    problem[row][column].setBackground(new Color(255, 255, 255));
                    problem[row][column].setForeground(new Color(0, 0, 0));
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
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}