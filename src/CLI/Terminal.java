package CLI;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

class Terminal {
    private String currDirectory;

    Terminal() {
        currDirectory = "C:\\";
    }

    private String setFilePath(String FileName) {
        File file = new File(FileName);
        if (FileName.equals(file.getAbsolutePath())) return FileName;
        else return currDirectory + FileName;
    }

    void clear() {
        for (int i = 0; i < 10; i++) System.out.println();
    }

    void pwd() {
        System.out.println(currDirectory);
    }

    void cd(String[] args) {
        File file = new File(args[0]);
        if (file.exists() && file.isDirectory()) currDirectory = args[0];
        else System.out.println(args[0] + " is not a valid directory.");
    }

    Integer ls(String dirName) throws IOException {
        AtomicInteger ret = new AtomicInteger();
        Files.list(new File(dirName).toPath())
                .forEach(path -> {
                    System.out.println(path);
                    ret.getAndIncrement();
                });
        return ret.intValue();
    }

    Integer ls() throws IOException {
        return ls(currDirectory);
    }

    void cp(String SourcePath, String DestinationPath) throws IOException // Didn't check if they already have directory or not, need the path helper func
    {
        SourcePath = setFilePath(SourcePath);
        DestinationPath = setFilePath(DestinationPath);

        File file = new File(SourcePath);

        if (!file.exists()) {
            System.out.println("Couldn't find this file/directory");
            return;
        }

        file = new File(DestinationPath);

        if (!file.exists()) {
            Boolean check = file.createNewFile();
            if (check) {
                System.out.println("Second file didn't exit, creating file now");
            } else {
                System.out.print("Failed to create file");
                return;
            }
        }

//        Files.copy(SourcePath, DestinationPath, StandardCopyOption.REPLACE_EXISTING);

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
    }

    void cp(String[] args) throws IOException {
        String dir = args[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            cp(args[i], dir + args[i]);
        }
    }

    void rm(String[] args) {
        for (String arg : args) {
            arg = setFilePath(arg);
            File file = new File(arg);
            if (!file.delete()) System.out.println("Error deleting file.");
        }
    }

    void mv(String SourcePath, String DestinationPath) throws IOException {
        cp(SourcePath, DestinationPath);
        rm(new String[]{SourcePath});
    }

    void mkdir(String[] args) {
        for (String arg : args) {
            arg = setFilePath(arg);
            File file = new File(arg);
            if (!file.mkdir()) System.out.println("Error making directory " + arg);
        }
    }

    void rmdir(String[] args) throws IOException {
        for (String arg : args) {
            arg = setFilePath(arg);
            Integer directoryFiles = ls(arg);
            if (directoryFiles > 0)
                System.out.println("Error removing directory \"" + arg + "\", the directory is not empty.");
            else rm(new String[]{arg});
        }
    }

    void Date() {
        System.out.println(LocalDateTime.now());
    }
}