package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor;

import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.ConsoleUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchStoreAdapter;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.editor.MultiPageEditor;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.guieditor.SchemaEditorInput;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.wizard.SchemaElementsWizard;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.editor.SdrMultiPageEditor;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.guieditor.SdrEditorInput;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.SchTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.GuiView;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.SchemaElementsView;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.XmlView;

public class EditorController {

    private static EditorController instance = new EditorController();

    private EditorController() {
    }

    synchronized public static EditorController getInstance() {
        return instance;
    }

    public SchTemplate openNewElementWizard(String templateName,
            SchStoreAdapter schStoreAdapter) {
        IWorkbenchPage page = getActivePage();
        SchemaElementsView view = (SchemaElementsView) page.findView(SchemaElementsView.ID);
        if (view == null) {
            try {
                page.showView(SchemaElementsView.ID);
            }
            catch (Exception e) {
            }
            view = (SchemaElementsView) page.findView(SchemaElementsView.ID);
        }
        SchTemplate template = view.getTemplate(templateName);
        template = SchemaElementsWizard.showAndFill(template.clone(),
                schStoreAdapter);
        return template;
    }

    public void notifySchemaSaved(SchStoreAdapter schStoreAdapter) {

        updateNavigationView();
    }

    public void updateNavigationView() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
        XmlView xmlNavigationView = (XmlView) page.findView(XmlView.ID);
        if (xmlNavigationView != null)
            xmlNavigationView.refresh();
        GuiView schemaNavigationView = (GuiView) page.findView(GuiView.ID);
        schemaNavigationView.refresh();
    }

    public void notifySchemaChanged(SchStoreAdapter schStoreAdapter,
            Connection connection) {
        IWorkbench workbench = PlatformUI.getWorkbench();
        MultiPageEditor editor = (MultiPageEditor) workbench.getActiveWorkbenchWindow().getActivePage().findEditor(
                new SchemaEditorInput(schStoreAdapter, connection, null));
        editor.notifySchemaChange(schStoreAdapter);
    }

    public void openEditor(SchStoreAdapter schStoreAdapter,
            Connection connection, boolean isNewSchema, String action) {
        IWorkbenchPage page = getActivePage();
        try {
            SchemaEditorInput input = new SchemaEditorInput(schStoreAdapter,
                    connection, action);
            input.setDirty(isNewSchema);
            page.openEditor(input, MultiPageEditor.ID);
        }
        catch (PartInitException e) {
            ConsoleUtil.addMessage("Open editor error: " + e, false);
        }
    }

    public void closeEditor(String schemaName, Connection connection) {
        IWorkbenchPage page = getActivePage();
        IEditorReference[] editors = page.findEditors(new StringInput(
                new StringStorage("", schemaName)), null,
                IWorkbenchPage.MATCH_INPUT);
        page.closeEditors(editors, false);

        editors = page.findEditors(new SchemaEditorInput(new SchStoreAdapter(
                new SchStore(schemaName)), connection, null), null,
                IWorkbenchPage.MATCH_INPUT);
        page.closeEditors(editors, false);
    }

    public IWorkbenchPage getActivePage() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        return workbench.getActiveWorkbenchWindow().getActivePage();
    }

    public void sdrOpenEditor(SdrStore sdrStore, boolean isNewSchema) {
        IWorkbenchPage page = getActivePage();
        try {
            SdrEditorInput input = new SdrEditorInput(sdrStore);
            // input.setDirty(isNewSchema);
            page.openEditor(input, SdrMultiPageEditor.ID);
        }
        catch (PartInitException e) {
            ConsoleUtil.addMessage("Open editor error: " + e, false);
        }
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/xmleditor/EditorController.java $ $Rev: 51954 $ $LastChangedBy: achoubey $ $Date: 2011-09-29 12:30:02 +0530 (Thu, 29 Sep 2011) $" };

}
