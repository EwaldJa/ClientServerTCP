package src;

public class MainServeur {
    public static void main(String[] args){
        Serveur serv = new Serveur(80);
        new Thread(serv).start();
    }
}
