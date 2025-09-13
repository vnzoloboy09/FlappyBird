import javax.swing.*;
import java.awt.*;

public class FlappyBird extends JPanel {
    int boardWidth = 360;
    int boardHeight = 640;

    Image backgroundImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.black);

        // load images
        birdImage       = new ImageIcon(getClass().getResource("res/flappybird.png")).getImage();
        topPipeImage    = new ImageIcon(getClass().getResource("res/toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("res/bottompipe.png")).getImage();
        backgroundImage = new ImageIcon(getClass().getResource("res/flappybirdbg.png")).getImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Draw(g);
    }

    public void Draw(Graphics g) {
        // background
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);
    }
}
