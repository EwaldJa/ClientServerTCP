package src;

public class MainServeur {
    public static void main(String[] args){
        Serveur serv = new Serveur(1060);
        new Thread(serv).start();
    }
}
