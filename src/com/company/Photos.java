package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
/**
 * Created by Oussama on 21/01/2017.
 */
public class Photos {

    Image[] image = new Image[1];

    String[] links = new String[1];

    String[] words = new String[1];

    public void gettingLinks(){
        for (int i = 0; i < links.length; i++) {
            try {
                image[i] = new Image(new FileInputStream(links[i]), 350, 350, false, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }




}
