package client;

import server.Intro;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    Client()  {
        String hostName = "172.20.206.165";
        int portNumber = 55555;

        try (
            Socket addressSocket = new Socket(hostName, portNumber);
            ObjectOutputStream oos = new ObjectOutputStream(addressSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(addressSocket.getInputStream());) {

            Object fromServer;
            while ((fromServer = ois.readObject()) != null) {
                System.out.println("hej");
                if (fromServer instanceof Intro) {
                    System.out.println("Anslutning upprättad ");
                    oos.writeObject(new Connected());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
