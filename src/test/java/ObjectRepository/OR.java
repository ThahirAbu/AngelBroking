package ObjectRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Abuthahir AH on 27-02-2017.
 */
public class OR {
    public static Properties OR = new Properties();
    public static Properties ObjectRepo() throws IOException {
        InputStream in = new FileInputStream
                ("D:\\Projects\\IdeaProjects\\AngelBroking\\src\\test\\java\\ObjectRepository\\OR.properties");
        OR.load(in);
        return OR;
    }
}
