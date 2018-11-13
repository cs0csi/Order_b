package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPFunctions {

    // Creating FTP Client instance
    FTPClient ftp = null;
    private static final String FTPUSER = "ftpuser";
    private static final String FTPPASS = "ftppassword";
    private static final String FTPURL = "ftpurl";
    private static final int FTPPORT = 21;

    // Constructor to connect to the FTP Server
    public FTPFunctions(String host, int port, String username, String password) throws Exception {

        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host, port);
        System.out.println("FTP URL is:" + ftp.getDefaultPort());
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(username, password);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

    // Method to upload the File on the FTP Server
    public void uploadFTPFile(String localFileFullName, String fileName, String hostDir)
            throws Exception {
        try {

            InputStream input = new FileInputStream(new File(localFileFullName));

            this.ftp.storeFile(hostDir + fileName, input);
        } catch (Exception e) {

        }
    }

    // Disconnect the connection to FTP
    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }

    public static void main() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));

            FTPFunctions ftpobj = new FTPFunctions(prop.getProperty(FTPURL), FTPPORT, prop.getProperty(FTPUSER), prop.getProperty(FTPPASS));

            ftpobj.uploadFTPFile("src/Csv/output.csv", "output.csv", "");

            ftpobj.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
