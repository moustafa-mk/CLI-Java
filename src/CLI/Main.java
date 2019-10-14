package CLI;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Parser parser = new Parser();
        try {
            parser.parse(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
