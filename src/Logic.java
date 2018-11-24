import java.util.*;

public class Logic implements LogicInterface {
    int[][] problem;
    private int[][] answer;

    Logic() {
        problem = new int[9][9];
        answer = new int[9][9];
    }

    @Override
    public void NL() {
        clearArray(problem);
        int[][] sampleArray = SeedSudokuMatrixFactory.retrieveSeedSudokuArrayByRandom();
        problem = generateSudokuArray(sampleArray);
        Random r = new Random();
        int grade = 6; //控制数独题难度
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < grade; j++) {
                int k = r.nextInt(9);
                if (problem[i][k] == 0) {
                    j--;
                }
                problem[i][k] = 0;
            }
        }
    }


    @Override
    public void GL() {
        problem = new int[][]{
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}};
    }

    @Override
    public boolean check(int[][] answer_player) {
        int[][] answer_AI = SOC();
        return isEqual(answer_player, answer_AI);
    }

    @Override
    public int[][] SOC() {
        backTrace(0, 0);
        return answer;
    }

    private void backTrace(int i, int j) {
        if (i == 8 && j == 9) {
            //特别要注意，这里要用数组浅拷贝
            shallowCopy(problem, answer);
//            System.out.println("获取正确解");
//            printArray(problem);
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


    void printArray(int[][] problem) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(problem[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
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

    private void shallowCopy(int[][] a1, int[][] a2) {
        if (a1.length != a2.length || a1[0].length != a2[0].length) {
            System.out.println("拷贝错误，数组尺寸不一");
        } else {
            for (int i = 0; i < a1.length; i++) {
                System.arraycopy(a1[i], 0, a2[i], 0, a1[0].length);
            }
        }
    }

    private int[][] generateSudokuArray(int[][] sampleArray) {
        List<Integer> randomList = buildRandomList();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (sampleArray[i][j] == randomList.get(k)) {
                        sampleArray[i][j] = randomList.get((k + 1) % 9);
                        break;
                    }
                }
            }
        }
        return sampleArray;
    }

    private List<Integer> buildRandomList() {
        List<Integer> result = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(result);
        return result;
    }

    private void clearArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = 0;
            }
        }
    }

    public static void main(String[] args) {
        //解最难数独题测试
//        Logic logic = new Logic();
//        logic.GL();
//        logic.printArray(logic.problem);
//        System.out.println();
//        logic.printArray(logic.SOC());

        //随机生成数独题测试
        Logic logic = new Logic();
        logic.NL();
        logic.printArray(logic.problem);
        System.out.println();
        logic.printArray(logic.SOC());//解随机数独题
    }
}
