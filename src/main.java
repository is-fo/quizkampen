import pojos.GameState;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        GameState original = new GameState();
        original.addPlayerScore(5, 0);

        GameState copy = new Main().deepCopy(original);
        System.out.println(copy.getPlayerScores());
    }

    private <T> T deepCopy(T object) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                return (T) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
