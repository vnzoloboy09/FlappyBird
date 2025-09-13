import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    Image backgroundImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;

    // Bird
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -200.0f;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    class Bird {
        float x = birdX;
        float y = birdY;
        int width =  birdWidth;
        int height = birdHeight;
        Image image;

        Bird(Image img) {
            image = img;
        }
    }

    // Logics
    Bird bird;
    float velocityX = -200.0f;
    float velocityY = 0.0f;
    float gravity = 550.0f;

    Timer gameLoop;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        setFocusable(true);
        addKeyListener(this);

        // load images
        birdImage       = new ImageIcon(getClass().getResource("res/flappybird.png")).getImage();
        topPipeImage    = new ImageIcon(getClass().getResource("res/toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("res/bottompipe.png")).getImage();
        backgroundImage = new ImageIcon(getClass().getResource("res/flappybirdbg.png")).getImage();

        bird = new Bird(birdImage);

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Draw(g);
    }

    public void Draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(birdImage, (int)bird.x, (int)bird.y, bird.width, bird.height, null);
    }

    public void Move(float dt) {
        velocityY += gravity * dt;
        bird.y += velocityY * dt;
        bird.y = Math.max(0, bird.y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Move(1.0f / 60.0f);
        repaint();
    }
}
