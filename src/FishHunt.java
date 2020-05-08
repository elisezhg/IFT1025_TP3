// Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class FishHunt extends Application {
    private Stage primaryStage;
    private Controleur controleur = new Controleur();
    public static float WIDTH = 640, HEIGHT = 480;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setScene(creerAccueil());

        primaryStage.setResizable(false);
        primaryStage.setTitle("Fish Hunt");
        primaryStage.show();
    }


    /**
     * Créer la scène d'accueil
     * @return scène d'accueil
     */
    private Scene creerAccueil() {
        Pane root = new Pane();
        Scene accueil = new Scene(root);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();

        // Dessine le background
        context.setFill(Color.rgb(0, 0, 139));
        context.fillRect(0, 0, WIDTH, HEIGHT);

        // Dessine le logo
        Image logo = new Image("res/logo.png");
        context.drawImage(logo, WIDTH / 2 - 200, HEIGHT / 2 - 200, 400, 300);

        // Ajoute les 2 boutons
        Button btn1 = new Button("Nouvelle partie!");
        Button btn2 = new Button("Meilleurs scores");
        btn1.relocate(WIDTH / 2 - 50, HEIGHT / 2 + 130);
        btn2.relocate(WIDTH / 2 - 50, HEIGHT / 2 + 170);

        btn1.setOnAction((e) -> primaryStage.setScene(creerFenetreJeu()));

        btn2.setOnAction((e) -> {
            try {
                primaryStage.setScene(creerMeilleursScores());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        root.getChildren().addAll(btn1, btn2);

        return accueil;
    }


    /**
     * Créer la scène de jeu
     * @return scène de jeu
     */
    private Scene creerFenetreJeu() {
        Pane root = new Pane();
        Scene fenetreJeu = new Scene(root);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();
        controleur.initJeu();

        // Dessine la cible
        fenetreJeu.setOnMouseMoved((e) -> {
            double cibleX = e.getX();
            double cibleY = e.getY();
            controleur.updateCible(cibleX, cibleY);
        });

        fenetreJeu.setOnMouseClicked((e) -> {
            double balleX = e.getX();
            double balleY = e.getY();
            controleur.lancerBalle(balleX, balleY);
        });

        fenetreJeu.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
                case H:
                    controleur.incrNiveau();
                    break;
                case J:
                    controleur.incrScore();
                    break;
                case K:
                    controleur.incrVie();
                    break;
                case L:
                    controleur.perdre();
            }
        });

        // Crée l'animation du jeu
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double dt = (now - lastTime) * 1e-9;

                // Met à jour le jeu et dessine
                controleur.update(dt);
                controleur.draw(context);

                lastTime = now;

                if (Jeu.switchScene) {
                    try {
                        primaryStage.setScene(creerMeilleursScores());
                        this.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.start();

        return fenetreJeu;
    }


    /**
     * Créer la scène des meilleurs scores
     * @return scène des meilleurs scores
     */
    private Scene creerMeilleursScores() throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene meilleursScores = new Scene(root, WIDTH, HEIGHT);

        // Titre
        Text titre = new Text("Meilleurs scores");
        titre.setFont(new Font(35));

        // Score
        ListView<String> scores = new ListView<>();
        scores.setMaxWidth(WIDTH - 60);
        scores.setMaxHeight(300);

        ArrayList<String[]> tabScores = new ArrayList<>();

        // Affiche score à partir du fichier
        FileReader fr = new FileReader("src/scores.txt");
        BufferedReader reader = new BufferedReader(fr);
        String ligne;
        int index = 1;
        while ((ligne = reader.readLine()) != null) {
            String[] score = ligne.split(",");
            scores.getItems().add("#" + index + " - " + score[0] + " - " + score[1]);
            index++;
            tabScores.add(score);
        }

        // Bouton de retour au menu
        Button btnMenu = new Button("Menu");
        btnMenu.setOnAction((e) -> primaryStage.setScene(creerAccueil()));

        root.setSpacing(10);
        root.getChildren().addAll(titre, scores, btnMenu);

        // Score minimun à atteindre pour entrer dans les meilleurs scores
        int scoreMin = 0;
        if (tabScores.size() != 0) {
            scoreMin = Integer.parseInt(tabScores.get(tabScores.size() - 1)[1]);
        }

        // Ajout d'un nouveau meilleur score
        if (Jeu.vie == 0 && (tabScores.size() < 10 || Jeu.score > scoreMin)) {
            HBox ajouterScore = new HBox();
            ajouterScore.setAlignment(Pos.CENTER);

            Text str1 = new Text("Votre nom: ");
            TextField inputNom = new TextField();
            Text str2 = new Text("a fait " + Jeu.score + " points! ");
            Button btnAddScore = new Button("Ajouter!");
            FileWriter fw = new FileWriter("src/scores.txt");
            BufferedWriter writer = new BufferedWriter(fw);

            btnAddScore.setOnAction((e) -> {
                // Check si y'a une virgule dans le nom entré
                if (inputNom.getText().contains(",")) {
                    inputNom.setText("Pas de virgule!");
                    return;
                }

                try {
                    boolean scoreAjoute = false;
                    String[] nvScore = {inputNom.getText(), String.valueOf(Jeu.score)};

                    // Ajoute le nouveau score à la bonne position
                    for (int i = 0; i < tabScores.size(); i++) {
                        if (Jeu.score > Integer.parseInt(tabScores.get(i)[1])) {
                            tabScores.add(i, nvScore);
                            scoreAjoute = true;
                            break;
                        }
                    }

                    // Si le score est à ajouter à la fin (quand il y a pas encore 10 scores)
                    if (!scoreAjoute) tabScores.add(nvScore);

                    // Ajoute les 10 meilleurs scores
                    for (int i = 0; i < 10 && i < tabScores.size(); i++) {
                        String[] score = tabScores.get(i);
                        writer.write(score[0] + "," + score[1] + "\n");
                    }

                    writer.close();
                    controleur.initJeu();
                    primaryStage.setScene(creerAccueil());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            ajouterScore.setSpacing(5);
            ajouterScore.getChildren().addAll(str1, inputNom, str2, btnAddScore);
            root.getChildren().add(2, ajouterScore);
        }

        reader.close();
        return meilleursScores;
    }
}
