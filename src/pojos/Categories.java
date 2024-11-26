package pojos;

import java.io.Serializable;
import java.util.*;

public class Categories implements Serializable {

    public static final int SPORT = 0;
    public static final int GEOGRAPHY = 1;

    private List<Question> currentQuestions;
    private List<Question> sportQuestions = new ArrayList<>();
    private List<Question> geographyQuestions = new ArrayList<>();
    private List<List<Question>> allQuestions = new ArrayList<>();

    public Categories() {

        //Svarsalternativ sportfrågor
        List<String> answersSport1 = Arrays.asList("Frankrike", "Sverige");
        List<String> answersSport2 = Arrays.asList("Hammarby", "Djurgården");

        //Svarsalternativ geografifrågor
        List<String> answersGeography1 = Arrays.asList("Ryssland", "Grekland");
        List<String> answersGeography2 = Arrays.asList("Kebenekaise", "Hammarbybacken");

        //Skapa sportfråge-objekt
        Question sport1 = new Question("Vilket lag vann VM 2018?", answersSport1, answersSport1.get(0));
        Question sport2 = new Question("Vilket fotbollslag är bäst i Stockholm?", answersSport2, answersSport2.get(0));

        //Skapa geografifråge-objekt
        Question geography1 = new Question("Vilket land är störst av dessa?", answersGeography1, answersGeography1.get(0));
        Question geography2 = new Question("Vad heter sveriges högsta berg?", answersGeography2, answersGeography2.get(0));

        sportQuestions.add(sport1);
        sportQuestions.add(sport2);
        geographyQuestions.add(geography1);
        geographyQuestions.add(geography2);
        allQuestions.add(sportQuestions);
        allQuestions.add(geographyQuestions);
    }

    public List<Question> getTempQuestion() {
        List<String> answersSport1 = Arrays.asList("Frankrike", "Sverige");

        Question q1 = new Question("Vilket lag vann VM 2018??", answersSport1, "Frankrike");
        List<Question> r = Arrays.asList(q1);
        return r;
    }

    public List<Question> getCategory(int category) {
        return allQuestions.get(category);
    }

    public String getCategoryString(int category) {
        return switch (category) {
            case SPORT -> "Sport";
            case GEOGRAPHY -> "Geography";
            default -> "FAIL!";
        };
    }

    public int getCategoryInt(String category) {
        return switch(category){
            case "Sport" -> SPORT;
            case "Geography" -> GEOGRAPHY;
            default -> 0;
        };
    }

    public void setCurrentCategory(List<Question> category) {
        currentQuestions = category;
    }

    public List<Question> getCurrentCategory() {
        return currentQuestions;
    }

    public List<Question> getNQuestions(int amount, int category) {
        List<Question> questions = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            questions.set(i, allQuestions.get(category).get(i));
        }
        return questions;
    }

    public List<String> getCategoriesStringList ( int categoriesToGenerate){

        Random rand = new Random();

        HashSet<Integer> s = new HashSet<Integer>();
        while (s.size() < categoriesToGenerate) {
            s.add(rand.nextInt(allQuestions.size()));
        }
        List<String> categories = new ArrayList<>();

        for (Integer i : s) {
            categories.add(getCategoryString(i));
        }
        return categories;
    }


}
