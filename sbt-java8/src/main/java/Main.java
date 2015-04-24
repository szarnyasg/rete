import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by szarnyasg on 4/24/15.
 */
public class Main {
    public static void main(String[] args) throws YamlException, FileNotFoundException {
        final String filePath = "/home/szarnyasg/tmp/sample.yml";
        final YamlReader reader = new YamlReader(new FileReader(filePath));
        final Map data = (Map) reader.read();
        System.out.println(data);
    }
}
