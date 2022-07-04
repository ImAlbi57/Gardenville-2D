package it.unibs.pajc.salm2d;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class KeyObj extends SuperObject{
    public KeyObj(){
        name = "Key";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/object/key.png")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
