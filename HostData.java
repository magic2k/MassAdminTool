/**
 * User: v.kolosov
 * Date: 24.07.12
 * Time: 16:02
 */
public class HostData {

    private String login;
    private String pass;
    private String host;
    private int port;

    public HostData(String login, String pass, String host, int port) {
        this.login = login;
        this.pass = pass;
        this.host = host;
        this.port = port;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "HostData{" +
                "login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
