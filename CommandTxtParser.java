import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * User: v.kolosov
 * Date: 24.07.12
 * Time: 16:39
 */
public class CommandTxtParser {
    static String parse(String fileName) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(fileName));
        StringBuffer sb = new StringBuffer();
        String line;

        while ((line = in.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }
}
