import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;

public class consumerConstants {

    public String TOPIC;
    public String BOOTSTRAP_SERVERS;
    public String GROUP;
    public int MAX;
    public String KEY;
    public String VALUE;
    public Integer POLL_TIME;

    public consumerConstants(){

    }

    public consumerConstants(String filename){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            consumerConstants PROPS_FROM_FILE = new consumerConstants();
            PROPS_FROM_FILE = mapper.readValue(new File(filename), consumerConstants.class);
            GROUP=PROPS_FROM_FILE.GROUP;
            MAX=PROPS_FROM_FILE.MAX;
            BOOTSTRAP_SERVERS=PROPS_FROM_FILE.BOOTSTRAP_SERVERS;
            KEY=PROPS_FROM_FILE.KEY;
            TOPIC=PROPS_FROM_FILE.TOPIC;
            VALUE=PROPS_FROM_FILE.VALUE;
            POLL_TIME=PROPS_FROM_FILE.POLL_TIME;
        }
        catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
        }
    }
}
