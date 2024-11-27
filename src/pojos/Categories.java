package pojos;

import java.io.Serializable;
import java.util.*;

public class Categories implements Serializable {

    public static final int SPORT = 0;
    public static final int GEOGRAPHY = 1;
    public static final int SCIENCE = 2;
    public static final int HISTORY = 3;
    public static final int MUSIC = 4;
    public static final int MOVIE = 5;


    private List<Question> currentQuestions;
    private final List<Question> sportQuestions = new ArrayList<>();
    private final List<Question> geographyQuestions = new ArrayList<>();
    private final List<Question> scienceQuestions = new ArrayList<>();
    private final List<Question> historyQuestions = new ArrayList<>();
    private final List<Question> musicQuestions = new ArrayList<>();
    private final List<Question> movieQuestions = new ArrayList<>();
    private final List<List<Question>> allQuestions = new ArrayList<>();

    public Categories() {

        // Svarsalternativ för sportfrågor
        List<String> answersSport1 = Arrays.asList("Frankrike", "Sverige", "Tyskland", "Brasilien");
        List<String> answersSport2 = Arrays.asList("Hammarby", "Djurgården", "AIK", "Östersund");
        List<String> answersSport3 = Arrays.asList("Zlatan", "Messi", "Cristiano Ronaldo", "Neymar");
        List<String> answersSport4 = Arrays.asList("Usain Bolt", "Carl Lewis", "Michael Johnson", "Mo Farah");
        List<String> answersSport5 = Arrays.asList("Cristiano Ronaldo", "Lionel Messi", "Neymar", "Kylian Mbappé");
        List<String> answersSport6 = Arrays.asList("Serena Williams", "Venus Williams", "Steffi Graf", "Martina Navratilova");

        // Svarsalternativ för geografifrågor
        List<String> answersGeography1 = Arrays.asList("Ryssland", "Grekland", "USA", "Kina");
        List<String> answersGeography2 = Arrays.asList("Kebenekaise", "Hammarbybacken", "Kungsleden", "Överhogdal");
        List<String> answersGeography3 = Arrays.asList("Amazonas", "Nilen", "Yangtze", "Mississippi");
        List<String> answersGeography4 = Arrays.asList("Japan", "Sydkorea", "China", "Indien");
        List<String> answersGeography5 = Arrays.asList("Australien", "Nya Zeeland", "Papua Nya Guinea", "Sydafrika");
        List<String> answersGeography6 = Arrays.asList("Afrika", "Asien", "Europa", "Nordamerika");

        // Svarsalternativ för vetenskapsfrågor
        List<String> answersScience1 = Arrays.asList("Syre", "Koldioxid", "Väte", "Kväve");
        List<String> answersScience2 = Arrays.asList("Väte", "Helium", "Kväve", "Syre");
        List<String> answersScience3 = Arrays.asList("Jupiter", "Mars", "Venus", "Saturnus");
        List<String> answersScience4 = Arrays.asList("Celsius", "Fahrenheit", "Kelvin", "Rankine");
        List<String> answersScience5 = Arrays.asList("DNA", "RNA", "Proteiner", "Enzymer");
        List<String> answersScience6 = Arrays.asList("Ljus", "Värme", "Ljud", "Elektricitet");

        // Svarsalternativ för historiska frågor
        List<String> answersHistory1 = Arrays.asList("Napoleon", "Alexander den Store", "Julius Caesar", "Attila Hunnen");
        List<String> answersHistory2 = Arrays.asList("Andra världskriget", "Första världskriget", "Kalla kriget", "Koreakriget");
        List<String> answersHistory3 = Arrays.asList("1912", "1920", "1939", "1945");
        List<String> answersHistory4 = Arrays.asList("Kristoffer Columbus", "Marco Polo", "Vasco da Gama", "Erik den Röde");
        List<String> answersHistory5 = Arrays.asList("Rom", "Athen", "Sparta", "Karthago");
        List<String> answersHistory6 = Arrays.asList("Ryska revolutionen", "Franska revolutionen", "Amerikanska revolutionen", "Indiska revolutionen");

        // Svarsalternativ för musikfrågor
        List<String> answersMusic1 = Arrays.asList("The Beatles", "ABBA", "Queen", "Rolling Stones");
        List<String> answersMusic2 = Arrays.asList("Beyoncé", "Rihanna", "Lady Gaga", "Adele");
        List<String> answersMusic3 = Arrays.asList("Mozart", "Beethoven", "Chopin", "Bach");
        List<String> answersMusic4 = Arrays.asList("Michael Jackson", "Elvis Presley", "Freddie Mercury", "David Bowie");
        List<String> answersMusic5 = Arrays.asList("Rolling Stones", "Led Zeppelin", "The Who", "Pink Floyd");
        List<String> answersMusic6 = Arrays.asList("Taylor Swift", "Katy Perry", "Ariana Grande", "Billie Eilish");

        // Svarsalternativ för filmfrågor
        List<String> answersMovies1 = Arrays.asList("Star Wars", "Star Trek", "Battlestar Galactica", "Spaceballs");
        List<String> answersMovies2 = Arrays.asList("Inception", "Interstellar", "The Matrix", "The Prestige");
        List<String> answersMovies3 = Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Matt Damon", "George Clooney");
        List<String> answersMovies4 = Arrays.asList("The Godfather", "Scarface", "Goodfellas", "Casino");
        List<String> answersMovies5 = Arrays.asList("The Matrix", "Avatar", "Inception", "The Terminator");
        List<String> answersMovies6 = Arrays.asList("Marvel", "DC", "Star Wars", "Harry Potter");

        // Skapa sportfråge-objekt
        Question sport1 = new Question("Vilket lag vann VM 2018?", answersSport1, answersSport1.get(0));
        Question sport2 = new Question("Vilket fotbollslag är bäst i Stockholm?", answersSport2, answersSport2.get(0));
        Question sport3 = new Question("Vem är världens bästa fotbollsspelare?", answersSport3, answersSport3.get(0));
        Question sport4 = new Question("Vem är snabbast på 100 meter?", answersSport4, answersSport4.get(0));
        Question sport5 = new Question("Vem är bäst i världen genom tiderna?", answersSport5, answersSport5.get(0));
        Question sport6 = new Question("Vem har vunnit flest Grand Slam-titlar i tennis?", answersSport6, answersSport6.get(0));

        // Skapa geografifråge-objekt
        Question geography1 = new Question("Vilket land är störst av dessa?", answersGeography1, answersGeography1.get(0));
        Question geography2 = new Question("Vad heter Sveriges högsta berg?", answersGeography2, answersGeography2.get(0));
        Question geography3 = new Question("Vilken flod är längst?", answersGeography3, answersGeography3.get(0));
        Question geography4 = new Question("Vilket land ligger längst österut?", answersGeography4, answersGeography4.get(0));
        Question geography5 = new Question("Vilket land är en ö?", answersGeography5, answersGeography5.get(0));
        Question geography6 = new Question("Vilken kontinent är störst?", answersGeography6, answersGeography6.get(0));

        // Skapa vetenskapsfråge-objekt
        Question science1 = new Question("Vilken gas är viktig för oss att andas?", answersScience1, answersScience1.get(0));
        Question science2 = new Question("Vad är vattnets kemiska formel?", answersScience2, answersScience2.get(0));
        Question science3 = new Question("Vilken planet är störst?", answersScience3, answersScience3.get(0));
        Question science4 = new Question("Vad används för att mäta temperatur?", answersScience4, answersScience4.get(0));
        Question science5 = new Question("Vad står DNA för?", answersScience5, answersScience5.get(0));
        Question science6 = new Question("Vad är ljusets hastighet?", answersScience6, answersScience6.get(0));

        // Skapa historiska frågor
        Question history1 = new Question("Vem var Napoleon?", answersHistory1, answersHistory1.get(0));
        Question history2 = new Question("Vad orsakade första världskriget?", answersHistory2, answersHistory2.get(0));
        Question history3 = new Question("När började första världskriget?", answersHistory3, answersHistory3.get(0));
        Question history4 = new Question("Vem upptäckte Amerika?", answersHistory4, answersHistory4.get(0));
        Question history5 = new Question("Vilken var den största antika civilisationen?", answersHistory5, answersHistory5.get(0));
        Question history6 = new Question("Vad orsakade den franska revolutionen?", answersHistory6, answersHistory6.get(0));

        // Skapa musikfrågor
        Question music1 = new Question("Vilket band är mest kända?", answersMusic1, answersMusic1.get(0));
        Question music2 = new Question("Vem har mest Grammyvinster?", answersMusic2, answersMusic2.get(0));
        Question music3 = new Question("Vilken kompositör är mest känd?", answersMusic3, answersMusic3.get(0));
        Question music4 = new Question("Vem sång Michael Jackson?", answersMusic4, answersMusic4.get(0));
        Question music5 = new Question("Vilket band skapade mest musik?", answersMusic5, answersMusic5.get(0));
        Question music6 = new Question("Vilken artist är mest känd idag?", answersMusic6, answersMusic6.get(0));

        // Skapa filmfrågor
        Question movie1 = new Question("Vilken film handlar om en Jedi-riddare?", answersMovies1, answersMovies1.get(0));
        Question movie2 = new Question("Vilken film handlar om drömmar?", answersMovies2, answersMovies2.get(0));
        Question movie3 = new Question("Vem spelade huvudrollen i Titanic?", answersMovies3, answersMovies3.get(0));
        Question movie4 = new Question("Vilken film handlar om italienska maffian?", answersMovies4, answersMovies4.get(0));
        Question movie5 = new Question("Vilken film handlar om en virtuell värld?", answersMovies5, answersMovies5.get(0));
        Question movie6 = new Question("Vilken film handlar om superhjältar?", answersMovies6, answersMovies6.get(0));

        // Lägg till frågorna i respektive kategorier
        sportQuestions.add(sport1);
        sportQuestions.add(sport2);
        sportQuestions.add(sport3);
        sportQuestions.add(sport4);
        sportQuestions.add(sport5);
        sportQuestions.add(sport6);

        geographyQuestions.add(geography1);
        geographyQuestions.add(geography2);
        geographyQuestions.add(geography3);
        geographyQuestions.add(geography4);
        geographyQuestions.add(geography5);
        geographyQuestions.add(geography6);

        scienceQuestions.add(science1);
        scienceQuestions.add(science2);
        scienceQuestions.add(science3);
        scienceQuestions.add(science4);
        scienceQuestions.add(science5);
        scienceQuestions.add(science6);

        historyQuestions.add(history1);
        historyQuestions.add(history2);
        historyQuestions.add(history3);
        historyQuestions.add(history4);
        historyQuestions.add(history5);
        historyQuestions.add(history6);

        musicQuestions.add(music1);
        musicQuestions.add(music2);
        musicQuestions.add(music3);
        musicQuestions.add(music4);
        musicQuestions.add(music5);
        musicQuestions.add(music6);

        movieQuestions.add(movie1);
        movieQuestions.add(movie2);
        movieQuestions.add(movie3);
        movieQuestions.add(movie4);
        movieQuestions.add(movie5);
        movieQuestions.add(movie6);

        allQuestions.add(sportQuestions);
        allQuestions.add(geographyQuestions);
        allQuestions.add(scienceQuestions);
        allQuestions.add(historyQuestions);
        allQuestions.add(musicQuestions);
        allQuestions.add(movieQuestions);
    }

    public List<Question> getCategory(int category) {
        return allQuestions.get(category);
    }

    public String getCategoryString(int category) {
        return switch (category) {
            case SPORT -> "Sport";
            case GEOGRAPHY -> "Geography";
            case SCIENCE -> "Science";
            case HISTORY -> "History";
            case MUSIC -> "Music";
            case MOVIE -> "Movie";
            default -> "FAIL!";
        };
    }

    public int getCategoryInt(String category) {
        return switch(category){
            case "Sport" -> SPORT;
            case "Geography" -> GEOGRAPHY;
            case "Science" -> SCIENCE;
            case "History" -> HISTORY;
            case "Music" -> MUSIC;
            case "Movie" -> MOVIE;
            default -> 0;
        };
    }

    public void setCurrentCategory(List<Question> category) {
        currentQuestions = category;
    }

    public List<Question> getCurrentQuestions() {
        return currentQuestions;
    }

    public List<Question> getNQuestions(int amount, int category) {
        List<Question> questions = new ArrayList<>(amount);
        Collections.shuffle(allQuestions.get(category));

        for (int i = 0; i < amount; i++) {
            questions.add(allQuestions.get(category).get(i));
            Collections.shuffle(questions.get(i).getAnswers());
        }
        return questions;
    }

    public List<String> getCategoriesStringList (int categoriesToGenerate) {

        categoriesToGenerate = Math.min(categoriesToGenerate, allQuestions.size());

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
