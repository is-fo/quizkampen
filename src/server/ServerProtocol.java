package server;

import pojos.*;

import java.util.ArrayList;
import java.util.List;

public class ServerProtocol {

    private final int WAITING = 0;
    private final int CHOOSE_CATEGORY = 1;
    private final int CATEGORY_CHOSEN = 11;
    private final int PLAY_ROUND = 2;
    private final int SHOW_RESULTS = 3;
    private final int ANSWER_QUESTION = 4;

    private int state = WAITING;
    private Categories categories = new Categories();
    private  List<Question> questions = new ArrayList<>(2);

    @SuppressWarnings("unchecked")
    public synchronized Object processInput(Object input, int player, Intro intro) {
        Object output = null;
        int currentCategory;

        int CATEGORIES_TO_GENERATE = 3;

        if (state == WAITING) {
            System.out.println("STATE == WAITING: " + player);
            state = (intro.getGameState().getCurrentRound() != 0 || player == 1) ? ANSWER_QUESTION : CHOOSE_CATEGORY;
            output = (intro.getGameState().getCurrentRound() != 0 || player == 1) ? questions : new Waiting();
        } else if (state == CHOOSE_CATEGORY) {
            System.out.println("STATE == CHOOSE_CATEGORY: " + player);

            output = categories.getCategoriesStringList(CATEGORIES_TO_GENERATE); //antal kategorival

            state = CATEGORY_CHOSEN;
        } else if (state == CATEGORY_CHOSEN) {
            System.out.println("STATE == CATEGORY_CHOSEN: " + player);
            currentCategory = categories.getCategoryInt((String)input); //String -> int conversion
            intro.getGameState().addCategory((String)input); //lagrar vald kategori

            questions = categories.getNQuestions(intro.getQuestionsPerRound(), currentCategory); //hämtar frågor från kategorin
            output = questions;
            categories.setCurrentCategory(questions);

            intro.getGameState().incrementRound();
            state = PLAY_ROUND;
        } else if (state == PLAY_ROUND) {
            System.out.println("STATE == PLAY_ROUND: " + player);
            if (input instanceof List l) {
                int score = intro.getGameState().calculateScore(l, questions);
                intro.getGameState().addPlayerScore(score, player);
            } else {
                System.err.println(input.getClass().getSimpleName());
                throw new RuntimeException("Not a list");
            }
            synchronized (intro.getGameState()) {
                output = intro.getGameState();
            }

            System.out.println(intro.getGameState() + "<--- lbalblabllalabllalabl");
            System.out.println(intro.getGameState().getScoreForPlayer(player));
            state = SHOW_RESULTS;
        } else if (state == SHOW_RESULTS) {
            System.out.println("STATE == SHOW_RESULTS: " + player);

            output = new Waiting();
            state = WAITING;

        } else if (state == ANSWER_QUESTION) { //input == svar
            System.out.println("ANSWER_QUESTION: " + player);
            if (input instanceof List l) {
                int score = intro.getGameState().calculateScore(l, questions);
                intro.getGameState().addPlayerScore(score, player);
            } else {
                System.err.println(input.getClass().getSimpleName());
                throw new RuntimeException("Not a list");
            }
            if (intro.getGameState().getCurrentRound() >= intro.getRoundsPerGame()) {
                System.out.println("SPECIAL END GAME ANSWER LAST");
                return new EndGame(intro.getGameState());
            }
            output = categories.getCategoriesStringList(CATEGORIES_TO_GENERATE);
            state = CATEGORY_CHOSEN;
        }

        return output;
    }
}