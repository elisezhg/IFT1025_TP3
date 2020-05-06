import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class FishHunt extends Application {
    private Stage primaryStage;
    public static int WIDTH = 640, HEIGHT = 480;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setScene(creerAccueil());

        primaryStage.setResizable(false);
        primaryStage.setTitle("Fish Hunt");
        primaryStage.show();
    }


    // Crée la scène d'accueil
    private Scene creerAccueil() {
        Pane root = new Pane();
        //root.setAlignment(Pos.CENTER);
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

        btn1.setOnAction((e) -> {
            primaryStage.setScene(creerFenetreJeu());
        });

        btn2.setOnAction((e) -> {
            try {
                primaryStage.setScene(creerMeilleursScores());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        root.getChildren().addAll(btn1, btn2);

        return accueil;
    }


    // Crée la fenêtre de jeu
    private Scene creerFenetreJeu() {
        Pane root = new Pane();
        Scene fenetreJeu = new Scene(root);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        Controleur controleur = new Controleur();

        GraphicsContext context = canvas.getGraphicsContext2D();

        // Dessine le background
        context.setFill(Color.rgb(0, 244, 144));
        context.fillRect(0, 0, WIDTH, HEIGHT);

        // Dessine la cible
        root.setOnMouseMoved((e) -> {
            double cibleX = e.getX();
            double cibleY = e.getY();
            controleur.updateCible(cibleX, cibleY);
        });

        root.setOnMouseClicked((e) -> {
            double balleX = e.getX();
            double balleY = e.getY();
            controleur.lancerBalle(balleX, balleY);
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

                // Reset le canvas en dessinant le background
                context.setFill(Color.rgb(0, 8, 144));
                context.fillRect(0, 0, WIDTH, HEIGHT);

                // Met à jour le jeu et dessine
                controleur.update(dt);
                controleur.draw(context);

                lastTime = now;
            }
        };


        timer.start();

        return fenetreJeu;
    }


    // Crée la fenêtre des meilleurs scores
    private Scene creerMeilleursScores() throws IOException {
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

//        FileReader fr = new FileReader("/scores.txt");
//        BufferedReader reader = new BufferedReader(fr);
//        String ligne;
//        while ((ligne = reader.readLine()) != null) {
//            scores.getItems().add(ligne);
//        }
//        reader.close();

        // Bouton de retour au menu
        Button btnMenu = new Button("Menu");
        btnMenu.setOnAction((e) -> {
            primaryStage.setScene(creerAccueil());
        });

        root.setSpacing(10);
        root.getChildren().addAll(titre, scores, btnMenu);


        // Ajout d'un nouveau meilleur score
//        if (true) {
//            HBox ajouterScore = new HBox();
//            ajouterScore.setAlignment(Pos.CENTER);
//            FileWriter fw = new FileWriter("/scores.txt");
//            BufferedWriter writer = new BufferedWriter(fw);
//
//            int score = 0;
//            Text str1 = new Text("Votre nom: ");
//            TextField inputNom = new TextField();
//            Text str2 = new Text("a fait "+ score +" points! ");
//            Button btnAddScore = new Button("Ajouter!");
//            btnAddScore.setOnAction((e) -> {
//                try {
//                    writer.append(inputNom.getText() + "," + score);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            });
//
//            writer.close();
//
//            ajouterScore.setSpacing(5);
//            ajouterScore.getChildren().addAll(str1, inputNom, str2, btnAddScore);
//            root.getChildren().add(2, ajouterScore);
//        }

        return meilleursScores;
    }
}
