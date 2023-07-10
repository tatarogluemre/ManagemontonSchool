package utilities;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ObjectMapperUtils {

    // <T> T herhangi bir data type
    // ObjectMapper icin - "import org.codehaus.jackson.annotate.JsonIgnoreProperties;" gerekli

    //   ObjectMapper().readValue(json, cls) methodu birinci parametrede aldığı String formatındaki Json datayı
    // ikinci parametrede belitilen Java objesine çevirir.
    public static <T> T convertJsonToJava(String json, Class<T> cls){   // Generic Method
        try {
            return new ObjectMapper().readValue(json,cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
