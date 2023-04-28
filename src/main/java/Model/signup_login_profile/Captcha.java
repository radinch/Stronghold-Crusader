package Model.signup_login_profile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Captcha {


    // TO USE THIS CLASS YOU SHOULD FIRST CALL GENERATE CAPTCHA METHOD (PRINTS OUT THE CODE)
// THEN GET USER INPUT (WHAT USER THINKS IS CAPTCHA)
// THEN CALL CHECK CAPTCHA METHOD TO VALIDATE THE USER INPUT (INPUT IS A STRING AND OUTPUT IS A BOOLEAN)
    private static int spaceNoiseValue = 2; // THIS SHOWS THE AMOUNT OF NOISE BETWEEN NUMBERS
    private static int textNoiseValue = 8; // THIS SHOWS THE AMOUNT OF NOISE ON NUMBERS
    // YOU CAN CHANGE THESE --> HIGHER NUMBER MEANS LOWER NOISE (BOTH NUMBERS SHOULD BE >= 2)
    private static String theLastGenerated;

    public static void generateCaptcha() {
        Random random = new Random();
        int upperBound = 10000000;
        int lowerBound = 1000;
        Integer randomNumber = random.nextInt(upperBound - lowerBound) + lowerBound;
        printAscii(randomNumber.toString());
        theLastGenerated = randomNumber.toString();
    }

    private static void printAscii(String generatedNumber) {
        generatedNumber = generatedNumber.replace("", " ").trim();
        Random random = new Random();
        int randomDummy;
        String ANSI_RESET = "\u001B[0m";
        String ANSI_YELLOW = "\u001B[33m";
        BufferedImage image = new BufferedImage(105, 13, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Dialog", Font.PLAIN, 13));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString(generatedNumber, 0, 13);
        for (int y = 0; y < 13; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < 85; x++) {
                if (y > 2) {
                    randomDummy = random.nextInt(100) + 1;
                    sb.append(image.getRGB(x, y) == -16777216 ? (randomDummy % spaceNoiseValue == 0 ? "." : " ") : (randomDummy % textNoiseValue == 0 ? "." : ANSI_YELLOW + "|" + ANSI_RESET));
                }
            }
            if (sb.toString().trim().isEmpty()) continue;
            System.out.println(sb);
        }
        System.out.println("please enter the Captcha Code above : ");
    }

    public static boolean checkCaptcha(String userInput) {
        return userInput.equals(theLastGenerated);
    }
}
