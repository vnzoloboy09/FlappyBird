import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

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

    public void Restart() {
        bird.y = birdY;
        velocityY = 0;
        pipes.clear();
        score = 0.0f;
        gameOver = false;
        gameLoop.start();
        placePipesTimer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -330.0f;
        }
        if(e.getKeyCode() == KeyEvent.VK_R) {
            if(gameOver) {
                Restart();
            }
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

    // Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image image;
        boolean passed = false;

        Pipe(Image img) {
            image = img;
        }
    }

    // Logics
    Bird bird;
    float velocityX = -200.0f;
    float velocityY = 0.0f;
    float gravity = 1000.0f;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    float score = 0.0f;

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
        pipes = new ArrayList<Pipe>();

        placePipesTimer = new Timer(1200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlacePipes();
            }
        });
        placePipesTimer.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    public void PlacePipes() {
        int yPos = (int)(pipeY - pipeHeight / 4 - Math.random() * (pipeHeight/2));
        Pipe topPipe = new Pipe(topPipeImage);
        topPipe.y = yPos;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImage);
        bottomPipe.y = topPipe.y + pipeHeight + pipeHeight / 4;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Draw(g);
    }

    public void Draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(birdImage, (int)bird.x, (int)bird.y, bird.width, bird.height, null);

        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.image, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setColor(Color.black);
        g.setFont(new Font("Fira Code", Font.PLAIN, 32));
        if(gameOver) {
            g.drawString("GAME OVER: " + String.valueOf((int)score), 50, 100);
        }
        else {
            g.drawString("Score: " + String.valueOf((int)score), 10, 50);
        }
    }

    public void Move(float dt) {
        // bird
        velocityY += gravity * dt;
        bird.y += velocityY * dt;
        bird.y = Math.max(0, bird.y);

        // pipes
        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX * dt;

            if(!pipe.passed && bird.x > pipe.x + pipe.width / 2) {
                pipe.passed = true;
                score += 0.5;
            }

            if(Collision(bird, pipe) == true) {
                gameOver = true;
            }
        }

        if(bird.y > boardHeight) {
            gameOver = true;
        }
    }

    public boolean Collision(Bird bird, Pipe pipe) {
        return  bird.x < pipe.x + pipe.width &&
                bird.y < pipe.y + pipe.height &&
                pipe.x < bird.x + bird.width &&
                pipe.y < bird.y + bird.height;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Move(1.0f / 60.0f);
        repaint();
        if(gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }
}
