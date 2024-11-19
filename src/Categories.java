import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Categories {

    public static final int SPORT = 0;
    public static final int GEOGRAPHY = 1;

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

    public List<Question> getCategory(int category) {
        return allQuestions.get(category);
    }
}