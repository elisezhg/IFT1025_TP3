// Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Jeu {

    // Variables globales
    public static int vie = 3;
    public static int score;
    public static int niveau;
    public static boolean switchScene = false;

    // Variables de l'instance
    private Image imgPoisson = new Image("/res/fish/00.png");
    private double cibleX = -100, cibleY = -100;
    private boolean affichageEcran = true;
    private double timerAffichage = 0;
    private double timerBulle = 0;
    private double timerPoisson = 0;
    private double timerPoissonSpe = 0;
    private int compteurPoissons = 0;

    // Entités du jeu
    private ArrayList<Poisson> poissons = new ArrayList<>();
    private ArrayList<Balle> balles = new ArrayList<>();
    private ArrayList<ArrayList<Bulle>> bulles = new ArrayList<>();


    /**
     * Constructeur du jeu
     */
    public Jeu() {
        vie = 3;
        score = 0;
        niveau = 1;
        switchScene = false;
        genererBulles();
    }


    /**
     * Incrémente le niveau
     */
    public void incrNiveau() {
        niveau++;
        timerAffichage = 0;
        affichageEcran = true;
        compteurPoissons = 0;
        poissons = new ArrayList<>();  // enlève les poissons de l'écran
    }


    /**
     * Incrémente le score
     */
    public void incrScore() {
        score++;
        compteurPoissons++;
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
        timerAffichage = 0;
        affichageEcran = true;
    }


    /**
     * Lance une balle si on a pas perdu
     * @param balleX abscisse de la balle
     * @param balleY ordonnée de la balle
     */
    public void lancerBalle(double balleX, double balleY) {
        if (vie != 0 && !affichageEcran) balles.add(new Balle(balleX, balleY));
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

        // MAJ des timers
        timerBulle += dt;
        if (!affichageEcran) timerPoisson += dt;
        if (!affichageEcran) timerPoissonSpe += dt;
        timerAffichage += dt;

        // Génère des bulles toutes les 3 sec
        if (timerBulle >= 3) {
            genererBulles();
            timerBulle = 0;
        }

        // Ajoute un poisson toutes les 3 sec
        if (timerPoisson >= 3 && !affichageEcran) {
            poissons.add(new Poisson());
            timerPoisson = 0;
        }

        // Ajoute un poisson spécial toutes les 5 sec
        if (timerPoissonSpe >= 5 && niveau > 1 && !affichageEcran) {
            if (Math.random() > 0.5) {
                poissons.add(new Crabe());
            } else {
                poissons.add(new EtoileDeMer());
            }

            timerPoissonSpe = 0;
        }

        // Termine l'affichage ou change de scène
        if (timerAffichage >= 3) {
            affichageEcran = false;
            timerAffichage = 0;
            if (vie == 0) {
                switchScene = true;
            }
        }

        // Augmente le niveau après 5 poissons attrapés
        if (compteurPoissons == 5) {
            incrNiveau();
        }

        // Fait perdre
        if (vie == 0 && !affichageEcran) {
            perdre();
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
                if (balle.collision(poisson)) {
                    Jeu.score++;
                    compteurPoissons++;
                }
            }

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

        // Reset le canvas en dessinant le background
        context.setFill(Color.rgb(0, 8, 144));
        context.fillRect(0, 0, FishHunt.WIDTH, FishHunt.HEIGHT);

        // Dessine les bulles
        for (ArrayList<Bulle> grpBulles : bulles) {
            for (Bulle bulle : grpBulles) {
                bulle.draw(context);
            }
        }

        // Dessine les poissons
        for (Poisson poisson : poissons) {
            poisson.draw(context);
        }

        // Dessine les balles
        for (Balle balle : balles) {
            balle.draw(context);
        }

        // Cible
        context.drawImage(new Image("/res/cible.png"), cibleX - 25, cibleY - 25, 50, 50);

        // Score
        context.setFill(Color.WHITE);
        context.setFont(new Font(25));
        context.setTextAlign(TextAlignment.CENTER);
        context.fillText(String.valueOf(score), FishHunt.WIDTH / 2, 50);

        // Vie
        for (int i = 0; i < vie; i++) {
            context.drawImage(imgPoisson, FishHunt.WIDTH / 2 - 65 + 50 * i, 70, 30, 30);
        }

        if (affichageEcran) {
            context.setFont(new Font(50));

            // Affiche numéro du niveau
            if (vie != 0) {
                context.setFill(Color.WHITE);
                context.fillText("Level " + niveau,FishHunt.WIDTH / 2, FishHunt.HEIGHT / 2);

            // Affiche game over
            } else {
                context.setFill(Color.RED);
                context.fillText("GAME OVER", FishHunt.WIDTH / 2, FishHunt.HEIGHT / 2);
            }
        }
    }
}
