package server;

public class ServerProtocol {
    private static final int WAITING = 0;
    private static final int CHOOSE_CATEGORY = 1;
    private static final int PLAY_ROUND = 2;

    private int state = WAITING;

}
