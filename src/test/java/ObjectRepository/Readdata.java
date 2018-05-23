package ObjectRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Readdata
{
    public Properties fetchingdata() throws IOException {
        FileInputStream fileInput = new FileInputStream
                (new File("D:\\Projects\\IdeaProjects\\AngelBroking\\src\\test\\java\\ObjectRepository\\OR.properties"));
        // Create Properties object
        Properties read = new Properties();
        //load properties file
        read.load(fileInput);
        return read;
    }


}

