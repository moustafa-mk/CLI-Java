package CLI;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Parser {
    private String[] args = new String[]{};
    private String cmd;
    private Terminal terminal = new Terminal();
    private boolean Exit = false;

    private String parse(String input, String output) throws Exception {
        input = input.substring(0, input.length() - 1);
        String[] words = input.split(" ", 2);
        cmd = words[0];
        if(words.length > 1) args = words[1].split(" ");
        if (output != null) {
            cmd = args[0];
            args[0] = output;
        }
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
                    return "";
            }
        }
        return "";
    }

    void parseWrap(String input) throws Exception {
        String[] splitted = input.split(" ");
        StringBuilder sb = new StringBuilder();
        boolean pipe = false, redirect = false, app = false;
        String output = null;
        for (String word :
                splitted) {
            switch (word) {
                case ">":
                    if (pipe) {
                        output = parse(sb.toString(), output);
                        pipe = false;
                    } else {
                        output = parse(sb.toString(), null);
                    }
                    redirect = true;
                    app = false;
                    System.out.println(output);
                    break;
                case ">>":
                    if (pipe) {
                        output = parse(sb.toString(), output);
                        pipe = false;
                    } else {
                        output = parse(sb.toString(), null);
                    }
                    redirect = true;
                    app = true;
                    System.out.println(output);
                    break;
                case "|":
                    if (pipe) {
                        output = parse(sb.toString(), output);
                    } else if (redirect) {
                        output = redirect(sb.toString(), output, app);
                        redirect = false;
                        app = false;
                    } else output = parse(sb.toString(), null);
                    pipe = true;
                    sb = new StringBuilder();
                    sb.append(word).append(" ");
                    System.out.println(output);
                    break;
                default:
                    sb.append(word).append(" ");
                    break;
            }
        }
        if (pipe) {
            parse(sb.toString(), output);
        } else if (redirect) {
            redirect(sb.toString(), output, app);
        } else {
            parse(sb.toString(), null);
        }
    }

    private String redirect(String file, String output, boolean app) throws IOException {
        FileWriter fw = new FileWriter(terminal.setFilePath(file), app);
        fw.write(output);
        fw.close();
        return terminal.setFilePath(file);
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


    private String handleClear() {
        if (args.length > 0) {
            System.out.println("clear command doesn't take any arguments");
            return null;
        }

        return terminal.clear();
    }

    private String handlePwd() {
        if (args.length > 0) {
            System.out.println("pwd command doesn't take any arguments.");
            return null;
        }

        return terminal.pwd();
    }

    private String handleCd() throws Exception {
        if (args.length > 1) {
            System.out.println("cd command accepts at most one parameter");
            return null;
        } else {
            if(args.length == 0) {
                args = new String[1];
                args[0] = "C:\\";
            }

            return terminal.cd(args);
        }
    }


    private String handleLs() throws IOException {
        if(args.length > 1) {
            System.out.println("ERROR: command \"ls\" takes at most 1 argument.");
            return null;
        }

        if (args.length == 1) return terminal.ls(args[0]);
        else return terminal.ls();
    }

    private String handleCat() {
        if(args.length==0){
            System.out.println("cat command takes at least 1 argument");
            return null;
        }
        //ToDo handle CAT
        return null;
    }

    private String handleCp() throws Exception {
        if (args.length < 2) {
            System.out.println("cp command takes at least two arguments");
            return null;
        }

        if (args.length == 2)
            return terminal.cp(args[0], args[1]);
        else
            return terminal.cp(args);
    }

    private String handleRm() {
        if (args.length == 0) {
            System.out.println("rm command takes at least one argument");
            return null;
        }

        return terminal.rm(args);
    }

    private String handleMv() throws Exception {
        if (args.length != 2) {
            System.out.println("mv command takes at two arguments");
            return null;
        }

        return terminal.mv(args[0], args[1]);
    }

    private String handleMkdir() {
        if (args.length == 0) {
            System.out.println("mkdir command takes at least one argument");
            return null;
        }

        return terminal.mkdir(args);
    }

    private String handleRmdir() {
        if (args.length == 0) {
            System.out.println("rmdir command takes at least one argument");
            return null;
        }

        return terminal.rmdir(args);
    }

    private String handleDate() {
        if (args.length > 0) {
            System.out.println("date command doesn't take any arguments.");
            return null;
        }

        return terminal.Date();
    }

    public String getCmd() { return cmd; }
    public String[] getArgs() { return args; }

    boolean isExit() {
        return Exit;
    }
}
