import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;

public class producerConstants {
    public String TOPIC;
    public String BOOTSTRAP_SERVERS;
    public String acks;
    public String compression;
    public Integer retries;
    public Integer batch;
    public Long linger;
    public Long buffer;
    public String key;
    public String value;

    public producerConstants(){

    }

    public producerConstants(String filename){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            producerConstants tmp = new producerConstants();
            tmp = mapper.readValue(new File(filename), producerConstants.class);
            acks=tmp.acks;
            batch=tmp.batch;
            BOOTSTRAP_SERVERS=tmp.BOOTSTRAP_SERVERS;
            buffer=tmp.buffer;
            compression=tmp.compression;
            key=tmp.key;
            linger=tmp.linger;
            retries=tmp.retries;
            TOPIC=tmp.TOPIC;
            value=tmp.value;
        }
        catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
        }
    }
}
