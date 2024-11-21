package client;

import server.GameState;
import server.Intro;
import server.Question;
import server.Waiting;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client {

    Client()  {
        String hostName = "172.20.206.165";
        int portNumber = 55555;

       /* try (
            Socket addressSocket = new Socket(hostName, portNumber);
            ObjectOutputStream oos = new ObjectOutputStream(addressSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(addressSocket.getInputStream());) {

            Object fromServer;
            while ((fromServer = ois.readObject()) != null) {
                System.out.println("hej");
                if (fromServer instanceof Intro) {
                    System.out.println("Anslutning upprättad ");
                    oos.writeObject(new Connected());
                    oos.writeObject(fromServer);
                } if (fromServer instanceof Waiting) {
                    oos.writeObject(fromServer); wait();
                } else if (fromServer instanceof List<Question>) {
                    //visa frågorna
                    //skicka tillbaka svaren
                } else if (fromServer instanceof GameState) {
                    //Visa resultatskärmen och vänta
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
    }*/

        try (
                Socket addressSocket = new Socket(hostName, portNumber);
                ObjectOutputStream oos = new ObjectOutputStream(addressSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(addressSocket.getInputStream())
        ) {
            while (true) {
                Object fromServer = ois.readObject();
                System.out.println("hej");

                if (fromServer instanceof Intro) {
                    System.out.println("Anslutning upprättad ");
                    oos.writeObject(new Connected());
                    oos.writeObject(fromServer);
                } else if (fromServer instanceof Waiting) {
                    System.out.println("Tjena");
                        wait();
                } else if (fromServer instanceof List<Question>) {
                    System.out.println("Han är lugn");
                }else if (fromServer instanceof GameState) {
                    System.out.println("Spelstatus");
            }
            }
        } catch( EOFException e){
            System.out.println("Servern stängde anslutningen.");
        }catch(Exception e){
            e.printStackTrace();
        }

        }
        public static void main(String[] args) {
        Client client = new Client();
    }
}


