import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;

public class consumerConstants {

    public String TOPIC;
    public String BOOTSTRAP_SERVERS;
    public String group;
    public int max;
    public String key;
    public String value;
    public Integer poll_time;

    public consumerConstants(){

    }

    public consumerConstants(String filename){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            consumerConstants tmp = new consumerConstants();
            tmp = mapper.readValue(new File(filename), consumerConstants.class);
            group=tmp.group;
            max=tmp.max;
            BOOTSTRAP_SERVERS=tmp.BOOTSTRAP_SERVERS;
            key=tmp.key;
            TOPIC=tmp.TOPIC;
            value=tmp.value;
            poll_time=tmp.poll_time;
        }
        catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
        }
    }
}
