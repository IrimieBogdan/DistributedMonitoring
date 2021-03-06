package executors;

import datamodel.CommandPidAndResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * Executes commands in terminal
 */
public class CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    /**
     * Start a new process with the provided command
     *
     * @return command output
     */
    public CommandPidAndResults execute(String command) {
        String line;
        Process process;
        long commandPid = -1;
        StringBuilder commandOutput = new StringBuilder();

        command = processCommand(command);

        try {
            process = Runtime.getRuntime().exec(command);

            Field pid = process.getClass().getDeclaredField("pid");
            pid.setAccessible(true);
            commandPid = pid.getLong(process);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = br.readLine()) != null) {
                commandOutput.append(line);
            }
            process.waitFor();
            process.exitValue();  // throw some error if exit value is incorrect
            process.destroy();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        logger.info("Command output: " + commandOutput.toString());
        return new CommandPidAndResults(commandPid, commandOutput.toString());
    }

    /**
     * Process the command and add or remove arguments.
     * @param command that will be processed.
     */
    private String processCommand(String command) {
        ResultFormat format = ResultFormat.STANDARD;

        // if the command is a nmap command than add the argument to get results as XML
        if (command.matches("^nmap.*")) {
            format = ResultFormat.XML;
        }

        // if url contains http:// remove it
        command = command.replace("http://", "");

        switch (format) {
            case STANDARD:
                break;
            case XML:
                if (!command.contains("-oX -")) {
                    String newCommand = command + " -oX -";
                    command = newCommand;
                }
                break;
        }
        return command;
    }

}
