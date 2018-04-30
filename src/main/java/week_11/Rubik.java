package week_11;

import java.util.ArrayList;

class Rubik {
    
    RubikGUI gui;
    DBConfig db;
    
    public static void main(String[] args) {
        Rubik rubikProgram = new Rubik();
        rubikProgram.start();
    }
    
    
    public void start() {
        gui = new RubikGUI(this);

    }



    

}