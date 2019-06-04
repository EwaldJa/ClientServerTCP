package src.controlers;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.LocalClient;

import java.io.File;
import java.io.IOException;

public class ViewControler {

    public AnchorPane mainPane;
    public Button buttonValiderIP, buttonQuitter, buttonParcourir, buttonEnvoyer, buttonTelecharger;
    public TextField TFadresseIP, TFport, TFnomFichier;
    public Label labelNomFichier;
    private static String IP, port, fichier;
    private static int retourFonction = 0;

    private final String erreurTitre = "Erreur!";
    private final String ipTitre = "Destination confirmée!";
    private final String ipTexte = "Adresse IP et ports enregistrés.";
    private final String succesTitre = "Transfert réussi";
    private final String succesTexte = "Transfert effectué avec succès";

    private LocalClient client;

    @FXML
    private void initialize() {
        setupButtonValiderIP();
        setupButtonParcourir();
        setupButtonEnvoyer();
        setupButtonTelecharger();
        setupButtonQuit();
        setupTextField();
    }

    private void setupButtonValiderIP(){
        buttonValiderIP.setOnAction(event ->{
            if (!TFadresseIP.getText().equals("") && TFadresseIP != null && !TFport.getText().equals("") && TFport.getText() != null) {
                IP = TFadresseIP.getText();
                port = TFport.getText();
                newPopUp(ipTitre,ipTexte, PopUpType.INFO);
                try {
                    client = new LocalClient(IP, port);
                } catch (Exception e) {
                    newPopUp("Impossible d'initialiser", e.getMessage(), PopUpType.ERROR);
                }
            }
        });

        buttonValiderIP.disableProperty().bind(new BooleanBinding()
        {
            {
                super.bind(TFadresseIP.textProperty(),
                        TFport.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (TFadresseIP.getText().isEmpty()
                        || TFport.getText().isEmpty());
            }
        });
    }

    private void setupButtonParcourir() {
        buttonParcourir.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir le fichier à transferer");
            File file = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
            if (file != null) {
                fichier = file.getAbsolutePath();
                labelNomFichier.setText(fichier);
                if (!IP.equals("") && !port.equals(""))
                    buttonEnvoyer.setDisable(false);
            }
        });
    }

    private void setupButtonEnvoyer(){
        buttonEnvoyer.setOnAction(event ->{
            if (testReadyE()) {
                retourFonction = client.SendFile(IP, port, fichier);
                if (retourFonction != LocalClient.transfer_successful) {
                    newPopUp(erreurTitre, getErrorText(retourFonction), PopUpType.ERROR);
                    retourFonction = 0;
                }
                else
                    newPopUp(succesTitre,succesTexte, PopUpType.INFO);
            }
        });
    }

    private void setupButtonTelecharger(){
        buttonTelecharger.setOnAction(event ->{
            if (testReadyR()) {
                retourFonction = client.ReceiveFile(IP, port, TFnomFichier.getText());
                if (retourFonction != LocalClient.transfer_successful) {
                    newPopUp(erreurTitre, getErrorText(retourFonction), PopUpType.ERROR);
                    retourFonction = 0;
                }
                else
                    newPopUp(succesTitre,succesTexte, PopUpType.INFO);
            }
        });
    }

    private void setupButtonQuit() {
        buttonQuitter.setOnAction(event -> {
            retourFonction = client.quitter(IP, port);
            if (retourFonction != LocalClient.succesfully_turned_off) {
                newPopUp(erreurTitre, getErrorText(retourFonction), PopUpType.ERROR);
                retourFonction = 0;
            }
            else
                newPopUp("Au revoir !","Déconnexion réussie", PopUpType.INFO);
            ((Stage) mainPane.getScene().getWindow()).close();
        });
    }

    private void setupTextField(){
        buttonTelecharger.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(TFnomFichier.textProperty(),
                        TFadresseIP.textProperty(),
                        TFport.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (TFnomFichier.getText().isEmpty()
                        || TFadresseIP.getText().isEmpty()
                        || TFport.getText().isEmpty());
            }
        });
    }

    private void newPopUp(String nom, String text, PopUpType type){
        Stage stageNewWindow = new Stage();
        try {
            if (type == PopUpType.ERROR)
                PopUpControler.setTextPopUp("Erreur: " + text);
            else
                PopUpControler.setTextPopUp(text);
            AnchorPane root = FXMLLoader.load(getClass().getResource("../Vue/popUp.fxml"));
            setupNewWindow(stageNewWindow, root,nom);
            stageNewWindow.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setupNewWindow(Stage stage, AnchorPane mainPane, String title){
        stage.setTitle(title);
        stage.setScene(new Scene(mainPane, 300, 150));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    private boolean testReadyE(){
        return (!IP.equals("") && !port.equals("") &&
                (fichier != null && !fichier.equals("")));
    }

    private boolean testReadyR(){
        return (!IP.equals("") && !port.equals("")) &&
                (TFnomFichier.getText() != null && !TFnomFichier.getText().equals(""));
    }

    private String getErrorText(int errorCode){
        switch (errorCode){
            case LocalClient.local_error: return "Erreur locale inconnue";
            case LocalClient.unknown_server: return "Serveur inexistant";
            case 400: return "400 : Bad Request";
            case 404: return "404 : Not Found";
            case 500: return "500 : Internal Server Error";
            case 505: return "505 : HTTP Version not supported";
            default: return "Erreur inconnue" + errorCode;
        }
    }
}