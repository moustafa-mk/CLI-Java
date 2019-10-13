package CLI;

import java.io.*;

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

        FileReader fr = new FileReader(SourcePath);
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter(DestinationPath, true);
        String s;

        while ((s = br.readLine()) != null) {
            fw.write(s);
            fw.flush();
        }
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
}