import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;

public class producerConstants {
    public String TOPIC;
    public String BOOTSTRAP_SERVERS;
    public String ACKS;
    public String COMPRESSION;
    public Integer RETRIES;
    public Integer BATCH;
    public Long LINGER;
    public Long BUFFER;
    public String KEY;
    public String VALUE;

    public producerConstants(){

    }

    public producerConstants(String filename){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            producerConstants PROPS_FROM_FILE = new producerConstants();
            PROPS_FROM_FILE = mapper.readValue(new File(filename), producerConstants.class);
            ACKS=PROPS_FROM_FILE.ACKS;
            BATCH=PROPS_FROM_FILE.BATCH;
            BOOTSTRAP_SERVERS=PROPS_FROM_FILE.BOOTSTRAP_SERVERS;
            BUFFER=PROPS_FROM_FILE.BUFFER;
            COMPRESSION=PROPS_FROM_FILE.COMPRESSION;
            KEY=PROPS_FROM_FILE.KEY;
            LINGER=PROPS_FROM_FILE.LINGER;
            RETRIES=PROPS_FROM_FILE.RETRIES;
            TOPIC=PROPS_FROM_FILE.TOPIC;
            VALUE=PROPS_FROM_FILE.VALUE;
        }
        catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
        }
    }
}
