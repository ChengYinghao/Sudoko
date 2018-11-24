public interface LogicInterface {
    int[][] NL(); //Novice Level: 业余级别, 电脑随机出题

    int[][] PL(); // Professional Level: 高手级别，从题库中随机抽取

    int[][] GL(); //Godly: 超神级别，号称世界最难数独题

    boolean check(int[][] problem, int[][] answer_player); // 检验玩家答案是否正确

    int[][] SOC(int[][] problem); //Solution of Computer, 该方法解出对应数独题的答案
}
