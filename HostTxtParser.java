import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * User: v.kolosov
 * Date: 24.07.12
 * Time: 16:07
 */
public class HostTxtParser {

    static List parse(String fileName) throws IOException {

        List<HostData> hostList = new LinkedList<HostData>();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = in.readLine()) != null) {
            String user = line.substring(0, line.indexOf(':'));
            String pass = line.substring(line.indexOf(':') + 1, line.indexOf('@'));
            String host = line.substring(line.indexOf('@') + 1, line.lastIndexOf(':'));
            int port = Integer.parseInt(line.substring(line.lastIndexOf(':') + 1));
            hostList.add(new HostData(user, pass, host, port));
        }

        return hostList;
    }
}
