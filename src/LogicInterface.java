public interface LogicInterface {
    void NL(); //Normal Level: 一般级别, 电脑随机出题，默认为27个已知值

    void GL(); //Godly: 超神级别，号称世界最难数独题

    boolean check(int[][] answer_player); // 检验玩家答案是否正确

    int[][] SOC(); //Solution of Computer, 该方法解出对应数独题的答案
}
