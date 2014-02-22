import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * User: admin
 * Date: 24.07.12
 * Time: 22:41
 */
public class SshTasksManager {

    private List<HostData> hostList;
    private String commandString;
    private List<SshConnectionTask> taskList = new LinkedList<SshConnectionTask>();
    private JTextArea textArea;

    public SshTasksManager(List hostList, String commandString, JTextArea textArea) {

        this.hostList = hostList;
        this.commandString = commandString;
        this.textArea = textArea;
    }

    public void start() {

        //purge log file
        File file = new File("out.log");
        file.delete();

        for(HostData hd : hostList) {
            taskList.add( new SshConnectionTask(hd, commandString) );
        }

        for(SshConnectionTask task : taskList) {
                task.execute();

        }

        //log file reading for console update
        FileReader fr = null;
        try {
            fr = new FileReader("out.log");

            StringBuffer sb = new StringBuffer(9086);
            int symbol;
            while((symbol = fr.read()) != -1) {
                sb.append((char)symbol);
            }

            textArea.append("\n" + sb.toString() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

        textArea.append("ALL TASKS ENDED. See details in out.log");
    }

}
