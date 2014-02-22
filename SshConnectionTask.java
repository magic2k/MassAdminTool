import com.jcraft.jsch.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: admin
 * Date: 24.07.12
 * Time: 19:48
 */
public class SshConnectionTask {

    private String login;
    private String pass;
    private String host;
    private String commandString;
    private int port;

    public SshConnectionTask(HostData hd, String commandString) {
        this.login = hd.getLogin();
        this.pass = hd.getPass();
        this.host = hd.getHost();
        this.commandString = commandString;
        this.port = hd.getPort();
    }

    public void execute() {

        FileOutputStream outFile = null;
        File file = new File("out.log");
        try {
            outFile = new FileOutputStream(file, true);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            byte[] byteStringStart = ("\n -----------Starting task on " + host + ":" + port + "--------------------------- \n").getBytes();
            outFile.write(byteStringStart);
            outFile.flush();

            JSch jsch = new JSch();
            Session session = jsch.getSession(login, host, port);
            session.setPassword(pass);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(10000);

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(commandString);
//        channel.setInputStream(System.in);
            channel.setOutputStream(outFile);
            ((ChannelExec) channel).setErrStream(outFile);

            channel.connect(10000);
            //sleep to ensure commands have enough time after connect. Insufficient check;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
//            channel.disconnect();
//            session.disconnect();
            //renew stream. Without in i got "Bad file descriptor". Must check that later
            outFile = new FileOutputStream(file, true);
            byte[] byteStringEnd = ("\n -----------Ending task on " + host + ":" + port + "--------------------------- \n").getBytes();
            outFile.write(byteStringEnd);
            outFile.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

