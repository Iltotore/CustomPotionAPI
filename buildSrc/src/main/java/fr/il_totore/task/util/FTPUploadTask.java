package fr.il_totore.task.util;

import com.jcraft.jsch.*;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FTPUploadTask extends DefaultTask {

    @TaskAction
    public void run(){
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        String remoteHost = (String) getProject().property("ftp.host");
        String username = (String) getProject().property("ftp.user");
        String password = (String) getProject().property("ftp.password");
        System.out.println("connecting to FTP...");
        JSch jsch = new JSch();
        try{
            Session jschSession = jsch.getSession(username, remoteHost);
            jschSession.setConfig(config);
            System.out.println("logging in...");
            jschSession.setPassword(password);
            jschSession.connect();
            System.out.println("uploading file...");
            ChannelSftp channel = (ChannelSftp) jschSession.openChannel("sftp");
            channel.connect();
            FileInputStream inputStream = new FileInputStream(new File(getProject().getBuildDir(), getProject().getBuildFile().getAbsolutePath() + getProject().getBuildFile().getName() + ".jar"));
            channel.put(inputStream, (String) getProject().property("ftp.file"));
            channel.disconnect();
            inputStream.close();
        } catch(JSchException | SftpException | IOException e){
            e.printStackTrace();
        }
    }
}
