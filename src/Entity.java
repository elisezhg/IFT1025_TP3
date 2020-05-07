// Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Entity {

    protected Color color;
    protected double ax = 0, ay = 0;
    protected double vx = 0, vy = 0;
    protected double posX, posY;
    protected double hauteur, largeur;
    protected boolean visible = true;


    /**
     * Met à jour la position et la vitesse de l'entité
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        vx += dt * ax;
        vy += dt * ay;
        posX += dt * vx;
        posY += dt * vy;
    }

    /**
     * Dessine l'entité sur le canvas
     * @param context contexte graphique
     */
    public abstract void draw(GraphicsContext context);


    // Getters et setters

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
