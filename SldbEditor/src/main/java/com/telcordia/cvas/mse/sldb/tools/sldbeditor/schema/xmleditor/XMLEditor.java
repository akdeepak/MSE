package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor;

import org.eclipse.ui.editors.text.TextEditor;

public class XMLEditor extends TextEditor {

    public static final String ID = "com.telcordia.cvas.mse.sldb.tools.sldbeditor.editor.xmleditor.XMLEditor";
    private ColorManager colorManager;

    public XMLEditor() {
        colorManager = new ColorManager();
        setSourceViewerConfiguration(new XMLConfiguration(colorManager));
        setDocumentProvider(new XMLDocumentProvider());
    }

    public void dispose() {
        colorManager.dispose();
        super.dispose();
    }

}
