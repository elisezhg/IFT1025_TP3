// Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.canvas.GraphicsContext;

public class Controleur {
    private Jeu jeu;

    /**
     * Constructeur du contrôleur
     */
    public Controleur() {
        this.jeu = new Jeu();
    }


    /**
     * Réinitialise le jeu
     */
    public void initJeu() {
        this.jeu = new Jeu();
    }


    /**
     * Incrémente le niveau du jeu
     */
    public void incrNiveau() {
        jeu.incrNiveau();
    }


    /**
     * Incrémente le score du jeu
     */
    public void incrScore() {
        jeu.incrScore();
    }


    /**
     * Incrémente le nombre de vie
     */
    public void incrVie() {
        jeu.incrVie();
    }


    /**
     * Fait perdre le jeu
     */
    public void perdre() {
        jeu.perdre();
    }


    /**
     * Lance une balle dans le jeu
     * @param balleX abscisse de la balle
     * @param balleY ordonnée de la balle
     */
    public void lancerBalle(double balleX, double balleY) {
        jeu.lancerBalle(balleX, balleY);
    }


    /**
     * Met à jour les coordonnées de la cible du jeu
     * @param cibleX abscisse de la cible
     * @param cibleY ordonnée de la cible
     */
    public void updateCible(double cibleX, double cibleY) {
        jeu.updateCible(cibleX, cibleY);
    }


    /**
     * Met à jour les positions, vitesses, accélérations de toutes les entités du jeu
     * @param deltaTime Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double deltaTime) {
        jeu.update(deltaTime);
    }


    /**
     * Dessine les entités du jeu sur le canvas
     * @param context contexte graphique
     */
    public void draw(GraphicsContext context) {
        jeu.draw(context);
    }
}
