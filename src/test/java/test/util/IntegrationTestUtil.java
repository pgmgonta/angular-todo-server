package test.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tatsuya on 2014/09/25.
 */
public class IntegrationTestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final String DATEFORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static Date buildDateFrom_yyyyMMddHHmmss(String yyyyMMddHHmmss) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT_YYYYMMDDHHMMSS);
        return dateFormat.parse(yyyyMMddHHmmss);
    }

}
