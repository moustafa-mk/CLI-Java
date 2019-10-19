package CLI;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

class Terminal {
    private String currDirectory;
    private int lsSize;

    Terminal() {
        currDirectory = "C:\\";
    }

    String setFilePath(String FileName) {
        File file = new File(FileName);
        if (FileName.equals(file.getAbsolutePath())) return FileName;
        else return currDirectory + FileName;
    }

    String clear() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < 25; i++) ret.append("\n");
        return ret.toString();
    }

    String pwd() {
        return (currDirectory);
    }

    String cd(String[] args) throws Exception {
        File file = new File(args[0]);
        if (file.exists() && file.isDirectory()) currDirectory = args[0];
        else throw new Exception(args[0] + " is not a valid directory.");
        return currDirectory;
    }

    String ls(String dirName) throws IOException {
        AtomicInteger ret = new AtomicInteger();
        StringBuilder sb = new StringBuilder();
        Files.list(new File(dirName).toPath())
                .forEach(path -> {
                    System.out.println(path);
                    sb.append(path).append("\n");
                    ret.getAndIncrement();
                });
        lsSize = ret.intValue();
        return sb.toString();
    }

    String ls() throws IOException {
        return ls(currDirectory);
    }

    String cp(String SourcePath, String DestinationPath) throws Exception {
        SourcePath = setFilePath(SourcePath);
        DestinationPath = setFilePath(DestinationPath);

        File file = new File(SourcePath);

        if (!file.exists()) {
            throw new Exception("Couldn't find this file/directory");
        }

        file = new File(DestinationPath);

        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("Second file didn't exist, creating file now");
            } else {
                throw new Exception("Failed to create file");
            }
        }

        FileReader fr = new FileReader(SourcePath);
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter(DestinationPath, true);
        String s;

        while ((s = br.readLine()) != null) {
            fw.write(s);
            fw.flush();
        }

        fr.close();
        fw.close();
        return DestinationPath;
    }

    String cp(String[] args) throws Exception {
        String dir = args[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            cp(args[i], dir + args[i]);
        }
        return dir;
    }

    String rm(String[] args) {
        for (String arg : args) {
            arg = setFilePath(arg);
            File file = new File(arg);
            if (!file.delete()) return ("Error deleting file.");
        }
        return args[args.length - 1];
    }

    String mv(String SourcePath, String DestinationPath) throws Exception {
        cp(SourcePath, DestinationPath);
        rm(new String[]{SourcePath});
        return DestinationPath;
    }

    String mkdir(String[] args) {
        for (String arg : args) {
            arg = setFilePath(arg);
            File file = new File(arg);
            if (!file.mkdir()) return ("Error making directory " + arg);
        }
        return args[args.length - 1];
    }

    String rmdir(String[] args) {
        for (String arg : args) {
            arg = setFilePath(arg);
            Integer directoryFiles = lsSize;
            if (directoryFiles > 0)
                return ("Error removing directory \"" + arg + "\", the directory is not empty.");
            else rm(new String[]{arg});
        }
        return args[args.length - 1];
    }

    //ToDo Make Cat function

    //ToDo Make Args function

    String Date() {
        return (LocalDateTime.now().toString());
    }
}