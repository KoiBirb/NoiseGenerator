import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

        final int originalTileSize = 16; // 32x32 tile size
        public final int scale = 2;

        public final int tileSize = originalTileSize * scale; // real tile size
        public final int maxScreenCol = 16; // tiles per col
        public final int maxScreenRow = 12; // tiles per row
        public final int screenWidth = tileSize * maxScreenCol; // game screen width
        public final int screenHeight = tileSize * maxScreenRow; // game screen height

        int FPS = 60; // frames per second

        Thread gameThread;

        public GamePanel(){
            this.setPreferredSize(new Dimension(screenWidth, screenHeight));
            this.setBackground(Color.BLACK);
            this.setFocusable(true);
        }
        public void setupGame() {}

        public void startGameThread() {
            gameThread = new Thread(this);
            gameThread.start();
        }

        @Override
        public void run() {

            // Delta method FPS clock
            double drawInterval = 1000000000.0/FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int drawCount = 0;

            while (gameThread != null) {

                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if(delta >= 1) {
                    update();
                    repaint();
                    delta--;
                    drawCount++;
                }
                if(timer>= 1000000000) {
                    System.out.println("FPS:" + drawCount);
                    drawCount = 0;
                    timer = 0;
                }
            }
        }
        public void update() {}

        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.dispose();
    }
}
