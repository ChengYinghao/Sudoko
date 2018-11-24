import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Logic implements LogicInterface {
    private int[][] problem = {
            {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 9, 0, 2, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 4, 5, 7, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 3, 0},
            {0, 0, 1, 0, 0, 0, 0, 6, 8},
            {0, 0, 8, 5, 0, 0, 0, 1, 0},
            {0, 9, 0, 0, 0, 0, 4, 0, 0}};
    @Override
    public int[][] NL() {
        int[][] answer_origin = new int[9][9];
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        return new int[0][];
    }

    @Override
    public int[][] PL() {
        return new int[0][];
    }

    @Override
    public int[][] GL() {
        int[][] hardest = new int[9][9];
        hardest[0][2] = 5;
        hardest[0][3] = 3;
        hardest[1][0] = 8;
        hardest[1][7] = 2;
        hardest[2][1] = 7;
        hardest[2][4] = 1;
        hardest[2][6] = 5;
        hardest[3][0] = 4;
        hardest[3][5] = 5;
        hardest[3][6] = 3;
        hardest[4][1] = 1;
        hardest[4][4] = 7;
        hardest[4][8] = 6;
        hardest[5][2] = 3;
        hardest[5][3] = 2;
        hardest[5][7] = 8;
        hardest[6][1] = 6;
        hardest[6][3] = 5;
        hardest[6][8] = 9;
        hardest[7][2] = 4;
        hardest[7][7] = 3;
        hardest[8][5] = 9;
        hardest[8][6] = 7;
        return hardest;
    }

    @Override
    public boolean check(int[][] problem, int[][] answer_player) {
        int[][] answer_AI = SOC(problem);
        return isEqual(answer_player, answer_AI);
    }

    @Override
    public int[][] SOC(int[][] problem) {
        backTrace(0, 0);
        return problem;
    }

    private int[][] try_candidate(int[][] problem, int[] candidate) {
        if (isFull(problem)) {
            return problem;
        }
        ArrayList<Integer> candidates = calCandidate(problem, candidate[0], candidate[1]);
        if (candidates.size() == 0) {
            return null;
        }
        for (int c : candidates) {
            problem[candidate[0]][candidate[1]] = c;
            int[] cur_candidate = leastCandidates(problem);
            int[][] feasible = try_candidate(problem, cur_candidate);
            if (feasible != null) {
                return feasible;
            }
        }
        problem[candidate[0]][candidate[1]] = 0;
        return null;
    }

    private boolean isFull(int[][] cur_array) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (cur_array[row][column] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void backTrace(int i, int j) {
        if (i == 8 && j == 9) {
            //已经成功了，打印数组即可
            System.out.println("获取正确解");
            printArray(problem);
            return;
        }

        //已经到了列末尾了，还没到行尾，就换行
        if (j == 9) {
            i++;
            j = 0;
        }

        //如果i行j列是空格，那么才进入给空格填值的逻辑
        if (problem[i][j] == 0) {
            for (int k = 1; k <= 9; k++) {
                //判断给i行j列放1-9中的任意一个数是否能满足规则
                if (checkFeasible(i, j, k)) {
                    //将该值赋给该空格，然后进入下一个空格
                    problem[i][j] = k;
                    backTrace(i, j + 1);
                    problem[i][j] = 0;
                }
            }
        } else {
            //如果该位置已经有值了，就进入下一个空格进行计算
            backTrace(i, j + 1);
        }
    }


    private ArrayList<Integer> calCandidate(int[][] cur_array, int row, int column) {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            candidates.add(i);
        }
        for (int i = 0; i < 9; i++) {
            removeExist(candidates, cur_array[i][column]);
        }

        for (int j = 0; j < 9; j++) {
            removeExist(candidates, cur_array[row][j]);
        }
        int row0, column0;
        row0 = getX0_Y0(row);
        column0 = getX0_Y0(column);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                removeExist(candidates, cur_array[row0 + i - 1][column0 + i - 1]);
            }
        }
        return candidates;
    }

    private void printArray(int[][] problem) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(problem[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    private int getX0_Y0(int coordinate) {
        int coordinate0;
        if (coordinate + 1 == 9) {
            coordinate0 = 6;
        } else if (coordinate + 1 < 3) {
            coordinate0 = 1;
        } else coordinate0 = coordinate + 1 - (coordinate + 1) % 3;
        return coordinate0;
    }

    private void removeExist(ArrayList<Integer> candidates, int value) {
        if (candidates.contains(value)) {
            int index = candidates.indexOf(value);
            candidates.remove(index);
        }
    }

    private boolean isEqual(int[][] a1, int[][] a2) {
        int h1 = a1.length;
        int h2 = a2.length;
        int w1 = a1[0].length;
        int w2 = a2[0].length;
        if (h1 != h2 || w1 != w2) {
            return false;
        }
        for (int i = 0; i < h1; i++) {
            for (int j = 0; j < w1; j++) {
                if (a1[i][j] != a2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkFeasible(int row, int column, int number) {
        //判断该行该列是否有重复数字
        for (int i = 0; i < 9; i++) {
            if (problem[row][i] == number || problem[i][column] == number) {
                return false;
            }
        }
        //判断小九宫格是否有重复
        int tempRow = row / 3;
        int tempLine = column / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (problem[tempRow * 3 + i][tempLine * 3 + j] == number) {
                    return false;
                }
            }
        }
        return true;
    }


    private int[] leastCandidates(int[][] problem) {
        int num = 9;
        int r = -1;
        int c = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (problem[i][j] == 0) {
                    int cur_num = calCandidate(problem, i, j).size();
                    if (cur_num < num) {
                        num = cur_num;
                        r = i;
                        c = j;
                    }
                }
            }
        }
        int[] x_y = new int[2];
        x_y[0] = r;
        x_y[1] = c;
        return x_y;
    }

    public static void main(String[] args) {

        Logic logic = new Logic();
        logic.printArray(logic.problem);
        System.out.println();
        logic.SOC(logic.problem);
    }
}
