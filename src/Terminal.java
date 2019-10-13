import sun.security.krb5.internal.crypto.Des;

import java.io.*;

public class Terminal {
    private String currDirectory;
    Terminal()
    {
        currDirectory = "C:\\";
    }
    public void cp(String SourthPath, String DestinationPath) throws IOException // Didn't check if they already have directory or not, need the path helper func
    {
        File file = new File(currDirectory+SourthPath);
        if(!file.exists())
        {
            System.out.println("Couldn't find this file/directory");
            return;
        }
        file = new File(currDirectory+DestinationPath);
        if(!file.exists())
        {
            Boolean check = file.createNewFile();
            if(check)
            {
                System.out.println("Second file didn't exit, creating file now");
            }
            else{
                System.out.print("Failed to create file");
                return;
            }
        }
        FileReader fr = new FileReader(currDirectory+SourthPath);
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter(currDirectory+ DestinationPath,true);
        String s;
        while((s=br.readLine())!=null){
            fw.write(s); fw.flush();
        }
    }
}
