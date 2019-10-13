package CLI;

public class Parser {
    private String[] args;
    private String cmd;

    public boolean parse(String input) {
        String[] words = input.split(" ", 2);
        cmd = words[0];
        if(words.length > 1) args = words[1].split(" ");
        if (isCommand()) {
            if (cmd.equals("cd")) return handleCd();
            else if(cmd.equals("ls")) return handleLs();
        }
        return false;
    }


    private boolean isCommand() {
        return cmd.equals("cd") || cmd.equals("ls") || cmd.equals("cp") || cmd.equals("cat") || cmd.equals("more") ||
                cmd.equals("mkdir") || cmd.equals("rmdir") || cmd.equals("mv") || cmd.equals("rm") ||
                cmd.equals("args") || cmd.equals("date") || cmd.equals("help")
                || cmd.equals("pwd") || cmd.equals("clear");
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
            return true;
        }
    }


    private boolean handleLs() {
        if(args.length > 1) {
            System.out.println("ERROR: command \"ls\" takes at most 1 argument.");
            return false;
        }
        return true;
    }

    private boolean handlePwd()
    {
        if(args.length==0) return true;
        System.out.println("pwd command takes no arguments");
        return false;
    }


    private boolean handleCat(){
        if(args.length==0){
            System.out.println("car command takes at least 1 argument");
            return false;
        }
        return true;
    }
    public String getCmd() { return cmd; }
    public String[] getArgs() { return args; }
}
