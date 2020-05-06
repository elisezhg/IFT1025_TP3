import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Poisson extends Entity {

    // Charge les différentes images des poissons dans un tableau
    private Image[] imgPoissons = new Image[]{
            new Image("res/fish/00.png"),
            new Image("res/fish/01.png"),
            new Image("res/fish/02.png"),
            new Image("res/fish/03.png"),
            new Image("res/fish/04.png"),
            new Image("res/fish/05.png"),
            new Image("res/fish/06.png"),
            new Image("res/fish/07.png")
    };

    protected Image img = imgPoissons[(int) (Math.random() * 8)];
    protected boolean gauche;

    public Poisson() {
        this.gauche = Math.random() < 0.5;
        this.hauteur = Math.random() * 40 + 80; // entre 80 et 120
        this.largeur = hauteur;
        this.ay = 100;
        this.vy = - (Math.random() * 100 + 100); // entre 100 et 200
        this.vx = 100 * Math.pow(Jeu.niveau, (float) 1 / 3) + 200;

        this.posY = Math.random() * 3 * FishHunt.HEIGHT / 5 + (float) FishHunt.HEIGHT / 5;

        if (gauche) {
            this.posX = - largeur;
        } else {
            vx *= -1;
            this.img = ImageHelpers.flop(img);
            this.posX = FishHunt.WIDTH;
        }

        this.color = new Color(Math.random(), Math.random(), Math.random(), 1);
        this.img = ImageHelpers.colorize(img, color);
    }


    @Override
    public void update(double dt) {
        super.update(dt);

        if (posX + largeur < 0 || posX > FishHunt.WIDTH) {
            visible = false;
            if (Jeu.vie > 0) Jeu.vie--;
        }
    }


    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(img, posX, posY, largeur, hauteur);
    }
}
