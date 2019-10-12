package CLI;

import java.util.Vector;

public class Parser {
    private Vector<String> args = new Vector<>();
    private String cmd;

    public boolean parse(String input) {
        String[] words = input.split(" ");
        String command = words[0];
        if (isCommand(command)) {
            if (command.equals("cd")) {
                return __handleCd(words);
            }
        }
        return false;
    }


    private boolean isCommand(String input) {
        return input.equals("cd") || input.equals("ls") || input.equals("cp") || input.equals("cat") || input.equals("more") ||
                input.equals("mkdir") || input.equals("rmdir") || input.equals("mv") || input.equals("rm") ||
                input.equals("args") || input.equals("date") || input.equals("help")
                || input.equals("pwd") || input.equals("clear");
    }

    private boolean __handleCd(String[] words) {
        if (words.length - 1 > 1) {
            System.out.println("cd command accepts at most one parameter");
            return false;
        }
        if (words.length - 1 == 0)                  /// only cd found, Default directory
        {
            cmd = "cd";
            args.add("C:\\");
            return true;
        } else {
            cmd = "cd";
            args.add(words[1]);
            return true;
        }
    }
}
