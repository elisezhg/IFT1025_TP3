import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Jeu {
    public static int vie;
    public static int score;
    public static int level;

    private double cibleX = -100, cibleY = -100;
    private ArrayList<Poisson> poissons = new ArrayList<>();
    private ArrayList<Balle> balles = new ArrayList<>();
    private ArrayList<ArrayList<Bulle>> bulles = new ArrayList<>();

    public Jeu() {
        init();
    }


    public void init() {
        vie = 3;
        score = 0;
        level = 1;
        genererBulles();
    }

    public void genererPoisson() {

    }

    public void lancerBalle(double balleX, double balleY) {
        balles.add(new Balle(balleX, balleY));
    }



    public void updateCible(double x, double y) {
        this.cibleX = x;
        this.cibleY = y;
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


    private double timer = 0;
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
    }

}
