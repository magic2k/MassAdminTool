import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * User: v.kolosov
 * Date: 24.07.12
 * Time: 11:10
 */
public class GuiMain {
    private JTextField serverListTextField;
    private JTextField commandsListTextField;
    private JButton serverBrowseButton;
    private JButton commandsBrowseButton;
    private JTextArea textArea;
    private JButton startButan;
    private JPanel mainPanel;

    private String commandString;
    private List<HostData> hostList = new LinkedList<HostData>();


    public static void main(String[] args) {
        JFrame frame = new JFrame("GuiMain");
        frame.setContentPane(new GuiMain().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Mass administration tool");
        frame.pack();
        frame.setVisible(true);
    }

    public GuiMain() {
        init();
    }

    /*
    * Метод чтения файла и загрузки его содержимого
    * в TextArea
    */
    private void readFile(String selectedFileName) {
        try {
            FileReader fr = new FileReader(selectedFileName);
            StringBuffer sb = new StringBuffer(2048);
            int symbol;
            while((symbol = fr.read()) != -1) {
                sb.append((char)symbol);
            }
            textArea.append("\n" + sb.toString() + "\n");
        }
        catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(textArea, ex.toString(), "Title", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(textArea, ex.toString(), "Title", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void init() {

        //перенос строк
//        textArea.setLineWrap(true);

        // инициализируем кнопки
        serverBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Создание диалога выбора файла
                JFileChooser fileChooser = new JFileChooser();
                //Добавление фильтра в диалог выбора файла
                fileChooser.addChoosableFileFilter(new TxtFilter());

                fileChooser.showOpenDialog(serverBrowseButton);
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile != null) {
                    //Выбранный файл: имя записываем в текстовое поле для пути
                    serverListTextField.setText(selectedFile.getAbsolutePath());
                    //содержимое загружаем в TextArea
                    readFile(selectedFile.getAbsolutePath());
                    try {
                        hostList = HostTxtParser.parse(selectedFile.getAbsolutePath());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            //Класс, фильтрующий текстовые файлы
            class TxtFilter extends javax.swing.filechooser.FileFilter {
                public String getDescription() {
                    return "*.txt";
                }

                public boolean accept(File f) {
                    String filename = f.getName();
                    return f.isDirectory() || filename.endsWith(".txt");
                }
            }
        });

        commandsBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Создание диалога выбора файла
                JFileChooser fileChooser = new JFileChooser();
                //Добавление фильтра в диалог выбора файла
                fileChooser.addChoosableFileFilter(new TxtFilter());

                fileChooser.showOpenDialog(commandsBrowseButton);
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile != null) {
                    //Выбранный файл: имя записываем в текстовое поле для пути
                    commandsListTextField.setText(selectedFile.getAbsolutePath());
                    //содержимое загружаем в TextArea
                    readFile(selectedFile.getAbsolutePath());
                    try {
                        commandString = CommandTxtParser.parse(selectedFile.getAbsolutePath());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            //Класс, фильтрующий текстовые файлы
            class TxtFilter extends javax.swing.filechooser.FileFilter {
                public String getDescription() {
                    return "*.txt";
                }

                public boolean accept(File f) {
                    String filename = f.getName();
                    return f.isDirectory() || filename.endsWith(".txt");
                }
            }
        });

        startButan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for( HostData hd : hostList) {
                    System.out.println(hd.toString());
                }
                System.out.println(commandString + "\n");

                SshTasksManager manager = new SshTasksManager(hostList, commandString, textArea);
                manager.start();
            }
        });

    }

}
