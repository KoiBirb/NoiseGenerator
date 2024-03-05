import java.awt.*;

public class MapGen {
    GamePanel gp;
    int tileSize, width, height;
    double scale;

    public MapGen (GamePanel gp){
        this.gp = gp;
        this.tileSize = gp.tileSize;
        this.scale = gp.scale;
        this.width = (int) (gp.screenWidth/tileSize*1.5);
        this.height = (int) (gp.screenHeight/tileSize*1.5);
    }

    double relativeXOffset, relativeYOffset, xTileOffset, yTileOffset, x, y;

    double speed = 5;
    int buffer = 10;

    int[] tiles;

    void setup() {
        tiles = new int[(width) * (height)];
    }

    void update() {
        if(gp.keyInput.upPressed) y -= speed;
        if(gp.keyInput.downPressed) y += speed;
        if(gp.keyInput.leftPressed) x -= speed;
        if(gp.keyInput.rightPressed) x += speed;
    }

    void draw(Graphics2D g2) {
        relativeXOffset = x % tileSize;
        relativeYOffset = y % tileSize;
        xTileOffset = x/tileSize;
        yTileOffset = y/tileSize;
        for(int i = 0; i < width; i ++) {
            for(int j = 0; j < height; j ++) {
                tiles[i + j * width] = getTile(i, j);
            }
        }
        for(int i = 0; i < width; i ++) {
            for(int j = 0; j < height; j ++) {
                switch (tiles[i + j * width]){
                    case 0:
                        g2.setColor(Color.BLUE);
                        break;
                    case 1:
                        g2.setColor(Color.YELLOW);
                        break;
                    case 2:
                        g2.setColor(Color.GREEN);
                        break;
                    case 3:
                        g2.setColor(Color.decode("#228B22")); //Forest green
                        break;
                }
                g2.fillRect((int) ((i - buffer/2) * tileSize - relativeXOffset), (int) ((j - buffer/2) * tileSize - relativeYOffset), tileSize, tileSize);
            }
        }
    }

    int getTile(int x, int y) {
        double v = gp.perlinNoiseGenerator.noise2((float)((xTileOffset + x) * scale), (float)((yTileOffset + y) * scale));
        if(v < 0.05) {
            //water
            return 0;
        } else if(v < 0.35) {
            //sand
            return 1;
        } else if(v < 0.5) {
            //grass
            return 2;
        } else {
            //forest
            return 3;
        }
    }
}
