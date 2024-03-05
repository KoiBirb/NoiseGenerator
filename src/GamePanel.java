import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

        public final double scale = 0.07;

        public final int tileSize = 2;
        public final int screenWidth = 1024; // game screen width
        public final int screenHeight = 800; // game screen height
        public int seed  = (int) (Math.random() * 1000000);

        int FPS = 60; // frames per second

        Thread gameThread;
        PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator(seed);
        KeyInput keyInput = new KeyInput(this);
        MapGen mapGen = new MapGen(this);

        public GamePanel(){
            this.setPreferredSize(new Dimension(screenWidth, screenHeight));
            this.setBackground(Color.BLACK);
            this.addKeyListener(keyInput);
            this.setFocusable(true);
        }
        public void setupGame() {
            mapGen.setup();
        }
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
        public void update() {
            if(keyInput.randomPressed){
                seed = (int) (Math.random() * 1000000);
                perlinNoiseGenerator = new PerlinNoiseGenerator(seed);
            }
            mapGen.update();
        }

        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            mapGen.draw(g2);

            g2.dispose();
    }
}
