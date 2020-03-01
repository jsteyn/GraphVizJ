package com.jannetta.graphvizj.components;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;

import java.io.IOException;

public class DotRSyntaxTextArea extends RSyntaxTextArea {
    private static String theme = "/themes/dot.xml";
    public DotRSyntaxTextArea() {
        super();
        AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        atmf.putMapping("text/gv", "com.jannetta.graphvizj.GVJ_TokenMaker");

        setSyntaxEditingStyle("text/gv");
        setCodeFoldingEnabled(true);
        setTheme(this, theme);
    }

    private void setTheme(RSyntaxTextArea syntaxTextArea, String str_theme) {
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    str_theme));
            theme.apply(syntaxTextArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
