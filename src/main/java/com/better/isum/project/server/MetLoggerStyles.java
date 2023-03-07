package com.better.isum.project.server;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.Color;
import java.util.Map;

public class MetLoggerStyles {
    private static String fontName = "Consolas";
    private static short fontSize = 12;

    private final static Map<String, Color> customColorValues = Map.of(
            "BLACK", new Color(0, 0, 0),
            "WHITE", new Color(255, 255, 255),
            "RED", new Color(255, 68, 68),
            "GREEN", new Color(99, 255, 94),
            "YELLOW", new Color(250, 255, 107),
            "PURPLE", new Color(212, 105, 229));
    public Map<String, SimpleAttributeSet> infoLevelStyling = Map.of("timeStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "logLevelStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setBold(this, true);
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "threadStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setItalic(this, true);
            StyleConstants.setBackground(this, customColorValues.get("PURPLE"));
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "messageStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    });
    public Map<String, SimpleAttributeSet> configLevelStyling = Map.of("timeStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "logLevelStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setBold(this, true);
            StyleConstants.setBackground(this, customColorValues.get("GREEN"));
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "threadStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setItalic(this, true);
            StyleConstants.setBackground(this, customColorValues.get("PURPLE"));
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "messageStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    });
    public Map<String, SimpleAttributeSet> warningLevelStyling = Map.of("timeStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "logLevelStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setBold(this, true);
            StyleConstants.setBackground(this, customColorValues.get("YELLOW"));
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "threadStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setItalic(this, true);
            StyleConstants.setBackground(this, customColorValues.get("PURPLE"));
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "messageStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    });
    public Map<String, SimpleAttributeSet> criticalLevelStyling = Map.of("timeStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setBackground(this, customColorValues.get("RED"));
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "logLevelStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setBold(this, true);
            StyleConstants.setBackground(this, customColorValues.get("RED"));
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "threadStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setItalic(this, true);
            StyleConstants.setBackground(this, customColorValues.get("PURPLE"));
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    }, "messageStyling", new SimpleAttributeSet() {
        {
            StyleConstants.setFontFamily(this, fontName);
            StyleConstants.setFontSize(this, fontSize);
            StyleConstants.setBackground(this, customColorValues.get("RED"));
            StyleConstants.setForeground(this, customColorValues.get("BLACK"));
        }
    });
}