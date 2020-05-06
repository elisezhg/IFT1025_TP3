import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Jeu {

    // Variables globales
    public static int vie;
    public static int score;
    public static int niveau;

    // Variables de l'instance
    private double timer = 0;
    private Image imgPoisson = new Image("/res/fish/00.png");
    private double cibleX = -100, cibleY = -100;

    // Entités du jeu
    private ArrayList<Poisson> poissons = new ArrayList<>();
    private ArrayList<Balle> balles = new ArrayList<>();
    private ArrayList<ArrayList<Bulle>> bulles = new ArrayList<>();


    /**
     * Constructeur du jeu
     */
    public Jeu() {
        init();
    }


    /**
     * Initialise les valeurs du jeu
     */
    public void init() {
        vie = 3;
        score = 0;
        niveau = 1;
        genererBulles();
    }


    /**
     * Incrémente le niveau
     */
    public void incrNiveau() {
        niveau++;
    }


    /**
     * Incrémente le score
     */
    public void incrScore() {
        score++;
    }


    /**
     * Incrémente la vie jusqu'à 3 vies max
     */
    public void incrVie() {
        if (vie < 3) vie++;
    }


    /**
     * Fait perdre le jeu
     */
    public void perdre() {
        vie = 0;
    }


    /**
     * Lance une balle si on a pas perdu
     * @param balleX abscisse de la balle
     * @param balleY ordonnée de la balle
     */
    public void lancerBalle(double balleX, double balleY) {
        if (vie != 0) balles.add(new Balle(balleX, balleY));
    }


    /**
     * Met à jour les coordonnées de la cible
     * @param cibleX abscisse de la cible
     * @param cibleY ordonnée de la cible
     */
    public void updateCible(double cibleX, double cibleY) {
        this.cibleX = cibleX;
        this.cibleY = cibleY;
    }


    /**
     * Génère 3 groupes de 5 bulles ayant une baseX aléatoire
     */
    public void genererBulles() {
        bulles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            bulles.add(new ArrayList<>());
            double baseX = Math.random() * FishHunt.WIDTH; // entre 0 et width
            for (int j = 0; j < 5; j++) {
                bulles.get(i).add(new Bulle(baseX));
            }
        }
    }


    /**
     * Met à jour les positions, vitesses, accélérations de toutes les entités
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        timer += dt;

        if (timer >= 3) {
            poissons.add(new Poisson());
            genererBulles();
            timer = 0;
        }


        // MAJ poissons
        for (int i = 0; i < poissons.size(); i++) {
            Poisson poisson = poissons.get(i);
            poisson.update(dt);
            if (!poisson.isVisible()) {
                poissons.remove(poisson);
                i--;
            }
        }


        // MAJ balles
        for (int i = 0; i < balles.size(); i++) {
            Balle balle = balles.get(i);
            balle.update(dt);
            for (Poisson poisson : poissons) {
                balle.testCollision(poisson);
            }

            // comme poisson; mettre dans entity?
            if (!balle.isVisible()) {
                balles.remove(balle);
                i--;
            }
        }

        // MAJ bulles
        for (ArrayList<Bulle> grpBulles : bulles) {
            for (Bulle bulle : grpBulles) {
                bulle.update(dt);
            }
        }
    }


    /**
     * Dessine les entités sur le canvas
     * @param context contexte graphique
     */
    public void draw(GraphicsContext context) {

        // Dessine les bulles
        for (ArrayList<Bulle> grpBulles : bulles) {
            for (Bulle bulle : grpBulles) {
                bulle.draw(context);
            }
        }

        for (Poisson poisson : poissons) {
            poisson.draw(context);
        }

        for (Balle balle : balles) {
            balle.draw(context);
        }

        // Cible
        context.drawImage(new Image("/res/cible.png"), cibleX - 25, cibleY - 25, 50, 50);


        // Score
        context.setFill(Color.WHITE);
        context.setFont(new Font(25));
        context.setTextAlign(TextAlignment.CENTER);
        context.fillText(String.valueOf(score), (double) FishHunt.WIDTH / 2, 50);

        // Vie
        for (int i = 0; i < vie; i++) {
            context.drawImage(imgPoisson, FishHunt.WIDTH / 2 - 65 + 50 * i, 70, 30, 30);
        }

        // Game over
        if (vie == 0) {
            context.setFill(Color.RED);
            context.setFont(new Font(50));
            context.fillText("GAME OVER", (double) FishHunt.WIDTH / 2, (double) FishHunt.HEIGHT / 2);
        }
    }

}
