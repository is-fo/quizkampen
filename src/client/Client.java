package client;

import pojos.Intro;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    private int roundsPerGame;

    Client() {
        String hostName = "localhost";
        int portNumber = 55555;

        try (
            Socket addressSocket = new Socket(hostName, portNumber);
            ObjectOutputStream oos = new ObjectOutputStream(addressSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(addressSocket.getInputStream())
        ) {
            while (true) {
                Object fromServer = ois.readObject();

                if (fromServer instanceof Intro) {
                    oos.writeObject(fromServer);
                } else if (fromServer instanceof String) { //TODO fromserver List<String> categories

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error reading object from server: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Client();
    }
}


