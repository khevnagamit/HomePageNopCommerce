import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProp {
    static Properties props;
    static FileInputStream inputStream;

    public String getProperty(String key){
        props = new Properties();

        try {
            inputStream = new FileInputStream("src\\Resourses\\TestDataConfig.properties");

            props.load(inputStream);

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  props.getProperty(key);

    }
}
