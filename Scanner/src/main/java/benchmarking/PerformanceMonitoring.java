package benchmarking;

import datamodel.CpuUsageResults;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class PerformanceMonitoring implements Monitorable {
    private final static Logger logger = LoggerFactory.getLogger(CpuMonitoring.class);

    private Calendar startTimeOfMonitoring;
    private Calendar endTimeOfMonitoring;

    @Override
    public void startMonitoring() {
        startTimeOfMonitoring = Calendar.getInstance();
        startTimeOfMonitoring.add(Calendar.SECOND, -1);
    }

    @Override
    public void stopMonitoring() {
        endTimeOfMonitoring = Calendar.getInstance();
        endTimeOfMonitoring.add(Calendar.SECOND, 1);
    }

    public CpuUsageResults parseForPid(long pid) {
        BufferedReader br = null;
        String line;
        List<String> cpuUsageResults = new ArrayList<String>();

        try {
            Thread.sleep(1500);
            String strpath="/tmp/performanceMonitoring.log";
            ReversedLinesFileReader reverseFileReader = new ReversedLinesFileReader(new File(strpath));;
            do {
                line = reverseFileReader.readLine();
                if (line == null) {
                    continue;
                }

                String[] splitedLine = line.split("\\s+");

                if ((splitedLine.length >= 6) &&                                                                         // at least a timestamp, a pid and a %
                        (splitedLine[0].trim().length() > 18)) {                                                        // a valid timestamp (date + time)

                    String[] dateAndTime = splitedLine[0].split("_");
                    String date = dateAndTime[0];
                    String time = dateAndTime[1];

                    if (date.trim().length() == 0 || time.trim().length() == 0) {
                        continue;
                    }

                    String[] dateSplit = date.split("/");
                    String[] timeSplit = time.split(":");

                    Calendar timeFromLog = Calendar.getInstance();

                    timeFromLog.set(Calendar.SECOND, Integer.parseInt(timeSplit[2]));
                    timeFromLog.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));
                    timeFromLog.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
                    timeFromLog.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSplit[2]));
                    timeFromLog.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]) - 1);
                    timeFromLog.set(Calendar.YEAR, Integer.parseInt(dateSplit[0]));

                    if (timeFromLog.after(startTimeOfMonitoring) && timeFromLog.before(endTimeOfMonitoring) && isNumeric(splitedLine[1].trim())) {
                        long resultsPid = Long.parseLong(splitedLine[1]);
                        if (resultsPid == pid) {
                            cpuUsageResults.add(splitedLine[2]);                                                    // PID is ok and time is ok
                        }
                    }

                    // look untill the time read from log is before the process began
                    if (timeFromLog.before(startTimeOfMonitoring)) {
                        break;
                    }
                }
            } while (line != null);
            reverseFileReader.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        Collections.reverse(cpuUsageResults);
        return new CpuUsageResults(pid, startTimeOfMonitoring, cpuUsageResults);
    }

    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}