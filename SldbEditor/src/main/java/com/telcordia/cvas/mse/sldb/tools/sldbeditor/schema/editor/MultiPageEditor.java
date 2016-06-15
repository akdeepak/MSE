package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.editor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.thoughtworks.xstream.XStream;

import com.telcordia.iscp.platform.isdbSimdbShr.isdbSchemaFormatIntJ;

import com.telcordia.cvas.mse.db.sldbobj.SchFieldStore;
import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.ConsoleUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchStoreAdapter;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.guieditor.SchemaEditor;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.guieditor.SchemaEditorInput;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor.StringInput;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor.StringStorage;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor.XMLEditor;

/**
 * An example showing how to create a multi-page editor. This example has 3
 * pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class MultiPageEditor extends MultiPageEditorPart implements
        IResourceChangeListener {
    public static final String ID = "com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.editor.multiPageEditor";
    /** The text editor used in page 0. */
    private XMLEditor xmlEditor;

    private SchemaEditor schemaEditor;

    /** The font chosen in page 1. */
    private Font font;

    /** The text widget used in page 2. */
    private StyledText text;

    private SchStore schStore;
    private Connection connection;

    /**
     * Creates a multi-page editor example.
     */
    public MultiPageEditor() {
        super();
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
    }

    @Override
    protected IEditorSite createSite(IEditorPart page) {
        IEditorSite site = null;

        site = super.createSite(page);
        return site;
    }

    public String getPartName() {
        if (schStore == null) {
            return super.getPartName();
        }
        return connection.getName() + Constant.SE_SEMICOLON
                + schStore.getName();
    }

    public void notifySchemaChange(SchStoreAdapter schStoreAdapter) {
        XStream xstream = new XStream();
        xstream.alias("SCH", SchStore.class);
        xstream.alias("SCH_FIELD", SchFieldStore.class);
        String xmlText = xstream.toXML(schStoreAdapter.getSchStore());
        removePage(1);
        xmlEditor = new XMLEditor();
        try {
            int index = addPage(xmlEditor, new StringInput(new StringStorage(
                    xmlText, schStoreAdapter.getName())));
            setPageText(index, " XML ");
        }
        catch (PartInitException e) {
            ConsoleUtil.addMessage(Constant.ERROR_XML_EDITOR, false);
        }
    }

    /**
     * Creates page 1 of the multi-page editor, which allows you to change the
     * font used in page 2.
     */
    void createPage0() {
        try {
            schemaEditor = new SchemaEditor();
            int index = addPage(schemaEditor, getEditorInput());
            setPageText(index, " GUI ");
        }
        catch (PartInitException e) {
            ErrorDialog.openError(getSite().getShell(),
                    "Error creating nested text editor", null, e.getStatus());
        }
    }

    /**
     * Creates page 0 of the multi-page editor, which contains a text editor.
     */
    void createPage1() {
        try {
            xmlEditor = new XMLEditor();
            SchemaEditorInput schemaEditorInput = (SchemaEditorInput) getEditorInput();
            schStore = schemaEditorInput.getSchema();
            schStore.setSchemaFormat(isdbSchemaFormatIntJ.SIMDB_VARIABLE_NO_KEY);
            connection = schemaEditorInput.getConnection();
            XStream xstream = new XStream();
            xstream.alias("SCH", SchStore.class);
            xstream.alias("SCH_FIELD", SchFieldStore.class);

            String xmlText = xstream.toXML(schStore);
            int index = addPage(xmlEditor, new StringInput(new StringStorage(
                    xmlText, schStore.getName())));
            setPageText(index, " XML ");
        }
        catch (PartInitException e) {
            ErrorDialog.openError(getSite().getShell(),
                    "Error creating nested text editor", null, e.getStatus());
        }
    }

    /**
     * Creates the pages of the multi-page editor.
     */
    protected void createPages() {
        createPage0();
        createPage1();
    }

    public void setActivePage(int pageIndex) {
        super.setActivePage(pageIndex);
    }

    /**
     * The <code>MultiPageEditorPart</code> implementation of this
     * <code>IWorkbenchPart</code> method disposes all nested editors.
     * Subclasses may extend.
     */
    public void dispose() {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        super.dispose();
    }

    /**
     * Saves the multi-page editor's document.
     */
    public void doSave(IProgressMonitor monitor) {
        getEditor(0).doSave(monitor);
    }

    /**
     * Saves the multi-page editor's document as another file. Also updates the
     * text for page 0's tab, and updates this multi-page editor's input to
     * correspond to the nested editor's.
     */
    public void doSaveAs() {
        IEditorPart editor = getEditor(0);
        editor.doSaveAs();
        setPageText(0, editor.getTitle());
        setInput(editor.getEditorInput());
    }

    /*
     * (non-Javadoc) Method declared on IEditorPart
     */
    public void gotoMarker(IMarker marker) {
        setActivePage(0);
        IDE.gotoMarker(getEditor(0), marker);
    }

    /**
     * The <code>MultiPageEditorExample</code> implementation of this method
     * checks that the input is an instance of <code>IFileEditorInput</code>.
     */
    public void init(IEditorSite site, IEditorInput editorInput)
            throws PartInitException {
        super.init(site, editorInput);
    }

    /*
     * (non-Javadoc) Method declared on IEditorPart.
     */
    public boolean isSaveAsAllowed() {
        return true;
    }

    /**
     * Closes all project files on project close.
     */
    public void resourceChanged(final IResourceChangeEvent event) {
        if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
                    for (int i = 0; i < pages.length; i++) {
                        if (((FileEditorInput) xmlEditor.getEditorInput()).getFile().getProject().equals(
                                event.getResource())) {
                            IEditorPart editorPart = pages[i].findEditor(xmlEditor.getEditorInput());
                            pages[i].closeEditor(editorPart, true);
                        }
                    }
                }
            });
        }
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/editor/MultiPageEditor.java $ $Rev: 64550 $ $LastChangedBy: achoubey $ $Date: 2012-01-12 12:21:37 +0530 (Thu, 12 Jan 2012) $" };

}
