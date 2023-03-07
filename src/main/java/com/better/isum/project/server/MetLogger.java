package com.better.isum.project.server;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.LogRecord;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.BorderLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.io.IOException;

public class MetLogger {
    private static MetLogger instance = null;
    private static LiveConsole liveConsole = new LiveConsole();
    private static final Logger LOGGER = Logger.getLogger(MetLogger.class.getName());

    private MetLogger() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String loggerStartingTimeString = simpleDateFormat.format(new Date());

            FileHandler fileHandler = new FileHandler(
                    String.format("C:\\1 Programming\\1 better isum\\isum-log_%s.log", loggerStartingTimeString));
            SimpleFormatter log_formatter = new SimpleFormatter() {
                @Override
                public String format(LogRecord record) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(record.getMillis())));
                    builder.append(" [").append(record.getLevel()).append("]");
                    builder.append(" [").append(Thread.currentThread().getName()).append("]");
                    builder.append(" - ").append(record.getMessage()).append("\n");
                    return builder.toString();
                }
            };
            fileHandler.setFormatter(log_formatter);

            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating file handler", e);
        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, "Security error adding file handler to logger", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing logger", e);
        }
    }

    public static synchronized MetLogger getInstance() {
        if (instance == null)
            instance = new MetLogger();
        return instance;
    }

    public void LogMessage(String message, Level level) {
        liveConsole.log(message, level);
        // Currently dont write log files
        // LOGGER.getHandlers()[0].publish(new LogRecord(level, message));
    }

    private static class LiveConsole {
        private static MetLoggerStyles logging_Styles = new MetLoggerStyles();

        private StyledDocument consoleDoc;

        private LiveConsole() {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            JTextPane textPane = new JTextPane();
            textPane.setEditable(false);
            consoleDoc = textPane.getStyledDocument();

            frame.add(textPane, BorderLayout.CENTER);
            frame.setVisible(true);
        }

        public void log(String message, Level level) {
            int docLength = consoleDoc.getLength();
            final SimpleDateFormat simpleDateFormatt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSSSS");
            Map<String, SimpleAttributeSet> levelStyling;

            switch (level.intValue()) {
                case 800: // INFO
                    levelStyling = logging_Styles.infoLevelStyling;
                    break;
                case 700: // CONFIG
                    levelStyling = logging_Styles.configLevelStyling;
                    break;
                case 900: // WARNING
                    levelStyling = logging_Styles.warningLevelStyling;
                    break;
                case 1000: // SEVERE
                    levelStyling = logging_Styles.criticalLevelStyling;
                    break;
                default:
                    levelStyling = logging_Styles.configLevelStyling;
            }

            try {
                consoleDoc.insertString(docLength, message + "\n", levelStyling.get("messageStyling"));
                consoleDoc.insertString(docLength, " - ", logging_Styles.infoLevelStyling.get("messageStyling"));
                consoleDoc.insertString(docLength, "[" + Thread.currentThread().getName() + "]",
                        levelStyling.get("threadStyling"));
                consoleDoc.insertString(docLength, " ", logging_Styles.infoLevelStyling.get("messageStyling"));
                consoleDoc.insertString(docLength, "[" + level + "]", levelStyling.get("logLevelStyling"));
                consoleDoc.insertString(docLength, " ", logging_Styles.infoLevelStyling.get("messageStyling"));
                consoleDoc.insertString(docLength, simpleDateFormatt.format(new Date()),
                        levelStyling.get("timeStyling"));
            } catch (BadLocationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}