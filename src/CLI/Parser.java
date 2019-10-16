package CLI;

import java.io.IOException;
import java.util.Vector;

public class Parser {
    private String[] args = new String[]{};
    private String cmd;
    private Terminal terminal = new Terminal();
    private boolean Exit = false;

    boolean parse(String input) throws IOException {
        String[] words = input.split(" ", 2);
        cmd = words[0];
        if(words.length > 1) args = words[1].split(" ");
        if (isCommand()) {
            switch (cmd) {
                case "clear":
                    return handleClear();
                case "pwd":
                    return handlePwd();
                case "cd":
                    return handleCd();
                case "ls":
                    return handleLs();
                case "cp":
                    return handleCp();
                case "cat":
                    return handleCat();
                case "rm":
                    return handleRm();
                case "mv":
                    return handleMv();
                case "mkdir":
                    return handleMkdir();
                case "rmdir":
                    return handleRmdir();
                case "date":
                    return handleDate();
                case "exit":
                    Exit = true;
                    return true;
            }
        }
        return false;
    }


    boolean pipe(String input) throws IOException {

        Vector<String> line = new Vector();
        String[] check = input.split("|");
        String a = new String();
        for (String tmp : check) {

            if (tmp.equals("|")) {
                line.add(a);
                a = new String();
                continue;
            }
            a += tmp;

        }
        line.add(a);
        for (int i = 0; i < line.size(); i++) {
            parse(line.elementAt(i));
            args = new String[]{};
        }

        return true;
    }



    private boolean isCommand() {
        return cmd.equals("cd") || cmd.equals("ls") || cmd.equals("cp") || cmd.equals("cat") || cmd.equals("more") ||
                cmd.equals("mkdir") || cmd.equals("rmdir") || cmd.equals("mv") || cmd.equals("rm") ||
                cmd.equals("args") || cmd.equals("date") || cmd.equals("help")
                || cmd.equals("pwd") || cmd.equals("clear");
    }


    private boolean handleClear() {
        if (args.length > 0) {
            System.out.println("clear command doesn't take any arguments");
            return false;
        }

        terminal.clear();
        return true;
    }

    private boolean handlePwd() {
        if (args.length > 0) {
            System.out.println("pwd command doesn't take any arguments.");
            return false;
        }

        terminal.pwd();
        return true;
    }

    private boolean handleCd() {
        if (args.length > 1) {
            System.out.println("cd command accepts at most one parameter");
            return false;
        } else {
            if(args.length == 0) {
                args = new String[1];
                args[0] = "C:\\";
            }

            terminal.cd(args);
            return true;
        }
    }


    private boolean handleLs() throws IOException {
        if(args.length > 1) {
            System.out.println("ERROR: command \"ls\" takes at most 1 argument.");
            return false;
        }

        if (args.length == 1) terminal.ls(args[0]);
        else terminal.ls();
        return true;
    }

    private boolean handleCat(){
        if(args.length==0){
            System.out.println("cat command takes at least 1 argument");
            return false;
        }
        return true;
    }

    private boolean handleCp() throws IOException {
        if (args.length < 2) {
            System.out.println("cp command takes at least two arguments");
            return false;
        }

        if (args.length == 2)
            terminal.cp(args[0], args[1]);
        else
            terminal.cp(args);

        return true;
    }

    private boolean handleRm() {
        if (args.length == 0) {
            System.out.println("rm command takes at least one argument");
            return false;
        }

        terminal.rm(args);
        return true;
    }

    private boolean handleMv() throws IOException {
        if (args.length != 2) {
            System.out.println("mv command takes at two arguments");
            return false;
        }

        terminal.mv(args[0], args[1]);
        return true;
    }

    private boolean handleMkdir() {
        if (args.length == 0) {
            System.out.println("mkdir command takes at least one argument");
            return false;
        }

        terminal.mkdir(args);
        return true;
    }

    private boolean handleRmdir() throws IOException {
        if (args.length == 0) {
            System.out.println("rmdir command takes at least one argument");
            return false;
        }

        terminal.rmdir(args);
        return true;
    }

    private boolean handleDate() {
        if (args.length > 0) {
            System.out.println("date command doesn't take any arguments.");
            return false;
        }

        terminal.Date();
        return true;
    }

    public String getCmd() { return cmd; }
    public String[] getArgs() { return args; }

    public boolean isExit() {
        return Exit;
    }
}
