package server;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import sun.misc.IOUtils;

public class Juego {

    private BufferedInputStream in;
    private BufferedOutputStream out;
    private String chosenWord;
    private int lives;
    private int correctLetters;
    private int score;

    public Juego(BufferedInputStream in, BufferedOutputStream out) {
        this.in = in;
        this.out = out;
        this.chosenWord = "";
        lives = 6;
        correctLetters = 0;
        score = 0;

        ChooseAction();
    }

    private void ChooseAction() {
        String msg = ReceiveMessage();
        if (msg.startsWith("ERROR")) {
            System.out.println("Ocurrió un error seleccionando la acción");
        } else if (msg.startsWith("START")) {
            StartGame();
        } else if (msg.startsWith("LETTER")) {
            CheckLetter(msg.charAt(6));
        } else if (msg.startsWith("WORD")) {
            String[] splitted;
            splitted = msg.split("~");
            CheckWord(splitted[1]);
        }
    }

    private void StartGame() {
        
        Palabra word = new Palabra();
        chosenWord = word.getWord();
        chosenWord = chosenWord.toUpperCase();
        int noOfLetters = chosenWord.length();
        lives = 6;
        correctLetters = 0;

        System.out.println(chosenWord);
        
        SendMessage(Integer.toString(noOfLetters) + "~" + Integer.toString(score) + "~");

        
        ChooseAction();
    }

    private void CheckLetter(char charAt) {
        if (chosenWord.contains(String.valueOf(charAt))) {
            
            char[] positions = new char[chosenWord.length()];
            for (int i = 0; i < chosenWord.length(); i++) {
                if (chosenWord.charAt(i) == charAt) {
                    positions[i] = '1';
                    correctLetters += 1;
                } else {
                    positions[i] = '0';
                }
            }
            String positionsS = new String(positions);
            if (correctLetters == chosenWord.length()) {
                
                score = score + 1;
                SendMessage("WON" + "~" + Integer.toString(score) + "~");
            } else {
                SendMessage("EXIST~" + positionsS + "~");
            }
        } else {
            lives--;
            if (lives > 0) {
                SendMessage("NOT_EXIST~" + Integer.toString(lives));
            } else {
                score = score-1;
                SendMessage("LOST" + "~" + Integer.toString(score) + "~");
            }
        }
        ChooseAction();
    }

    private void SendMessage(String msg) {
        try {
            byte[] toClient = msg.getBytes();
            out.write(toClient, 0, toClient.length);
            out.flush();
        } catch (IOException ioExeption) {
            System.out.println("Unable to Send Message to Client:" + ioExeption.toString());
        }
    }

    private String ReceiveMessage() {
        try {
            byte[] msg = new byte[4096];
            int n = in.read(msg, 0, msg.length);
            return (new String(msg));
        } catch (IOException ioExeption) {
            System.out.println("Unable to Recieve Message from Client:" + ioExeption.toString());
            return "ERROR";
        }
    }

    private void CheckWord(String string) {
        System.out.println(string);
        if (string.toUpperCase().equals(chosenWord)){
             score = score + 1;
             SendMessage("WON" + "~" + Integer.toString(score) + "~");
        }
        else {
             SendMessage("LOST" + "~" + Integer.toString(score) + "~");
        }
        ChooseAction();
    }
}