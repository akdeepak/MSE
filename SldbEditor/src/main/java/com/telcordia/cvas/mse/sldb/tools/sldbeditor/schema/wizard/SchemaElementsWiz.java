package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.ConsoleUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchStoreAdapter;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.FieldTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.SchTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.TableTemplate;

/**
 * This class shows a satisfaction survey
 */
class SchemaElementsWiz implements IWizard {
    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/wizard/SchemaElementsWiz.java $ $Rev: 49802 $ $LastChangedBy: achoubey $ $Date: 2011-09-13 16:23:59 +0530 (Tue, 13 Sep 2011) $" };

    public static final String DEFAULT_IMAGE = "org.eclipse.jface.wizard.Wizard.pageImage";//$NON-NLS-1$
    public static final String FIELD_PAGE_NAME = "Field";
    private IWizardContainer container = null;
    private List<IWizardPage> pages = new ArrayList<IWizardPage>();
    private boolean needsProgressMonitor = false;
    private boolean forcePreviousAndNextButtons = false;
    private boolean isHelpAvailable = false;
    private Image defaultImage = null;
    private ImageDescriptor defaultImageDescriptor = JFaceResources.getImageRegistry().getDescriptor(
            DEFAULT_IMAGE);
    private RGB titleBarColor = null;
    private String windowTitle = null;
    private IDialogSettings dialogSettings = null;
    private SchTemplate schTemplate;
    private SchStoreAdapter schStoreAdapter;
    private SchTemplate result;

    public SchemaElementsWiz(SchTemplate schTemplate,
            SchStoreAdapter schStoreAdapter) {
        this.schTemplate = schTemplate;
        this.schStoreAdapter = schStoreAdapter;
        // Add the pages
        if (schTemplate.getTemplateType() == SchTemplate.TemplateType.SCH_TABLE) {
            setWindowTitle(Constant.TABLE_WIZARD);
            TableTemplate tableTemplate = (TableTemplate) schTemplate;
            if (tableTemplate.getTableName() == null) {
                tableTemplate.setTableName("");
            }
            addPage(new TablePage(this));

            if (tableTemplate.getFieldTemplates() == null) {
                tableTemplate.setFieldTemplates(new ArrayList<FieldTemplate>());
            }
            for (FieldTemplate fTempl : tableTemplate.getFieldTemplates()) {
                addPage(new FieldPage(fTempl, this, true));
            }
        }
        else {
            setWindowTitle(Constant.FIELD_WIZARD);
            addPage(new FieldPage((FieldTemplate) schTemplate, this, false));
        }
    }

    @Override
    public boolean performFinish() {
        result = schTemplate;
        return true;
    }

    SchTemplate getResult() {
        return result;
    }

    public SchTemplate getSchTemplate() {
        return schTemplate;
    }

    public SchStoreAdapter getSchStoreAdapter() {
        return schStoreAdapter;
    }

    protected SchemaElementsWiz() {
        super();
    }

    public void addPage(IWizardPage page) {
        pages.add(page);
        page.setWizard(this);
    }

    public void addPages() {
    }

    public boolean canFinish() {
        // Default implementation is to check if all pages are complete.
        for (int i = 0; i < pages.size(); i++) {
            if (!(pages.get(i)).isPageComplete()) {
                return false;
            }
        }
        return true;
    }

    public void createPageControls(Composite pageContainer) {
        // the default behavior is to create all the pages controls
        for (int i = 0; i < pages.size(); i++) {
            IWizardPage page = pages.get(i);
            page.createControl(pageContainer);

        }
    }

    public void dispose() {
        // notify pages
        for (int i = 0; i < pages.size(); i++) {
            (pages.get(i)).dispose();
        }
        // dispose of image
        if (defaultImage != null) {
            JFaceResources.getResources().destroyImage(defaultImageDescriptor);
            defaultImage = null;
        }
    }

    public IWizardContainer getContainer() {
        return container;
    }

    public Image getDefaultPageImage() {
        if (defaultImage == null) {
            defaultImage = JFaceResources.getResources().createImageWithDefault(
                    defaultImageDescriptor);
        }
        return defaultImage;
    }

    public IDialogSettings getDialogSettings() {
        return dialogSettings;
    }

    public IWizardPage getNextPage(IWizardPage page) {
        int index = pages.indexOf(page);
        if (index == pages.size() - 1 || index == -1) {
            // last page or page not found
            return null;
        }
        return pages.get(index + 1);
    }

    public IWizardPage getPage(String name) {
        for (int i = 0; i < pages.size(); i++) {
            IWizardPage page = pages.get(i);
            String pageName = page.getName();
            if (pageName.equals(name)) {
                return page;
            }
        }
        return null;
    }

    public int getPageCount() {
        return pages.size();
    }

    public IWizardPage[] getPages() {
        return (IWizardPage[]) pages.toArray(new IWizardPage[pages.size()]);
    }

    public IWizardPage getPreviousPage(IWizardPage page) {
        int index = pages.indexOf(page);
        if (index == 0 || index == -1) {
            // first page or page not found
            return null;
        }
        return pages.get(index - 1);
    }

    public Shell getShell() {
        if (container == null) {
            return null;
        }
        return container.getShell();
    }

    public IWizardPage getStartingPage() {
        if (pages.size() == 0) {
            return null;
        }
        return pages.get(0);
    }

    public RGB getTitleBarColor() {
        return titleBarColor;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public boolean isHelpAvailable() {
        return isHelpAvailable;
    }

    public boolean needsPreviousAndNextButtons() {
        return forcePreviousAndNextButtons || pages.size() >= 1;
    }

    public boolean needsProgressMonitor() {
        return needsProgressMonitor;
    }

    public boolean performCancel() {
        return true;
    }

    public void setContainer(IWizardContainer wizardContainer) {
        container = wizardContainer;
    }

    public void setDefaultPageImageDescriptor(ImageDescriptor imageDescriptor) {
        defaultImageDescriptor = imageDescriptor;
    }

    public void setDialogSettings(IDialogSettings settings) {
        dialogSettings = settings;
    }

    public void setForcePreviousAndNextButtons(boolean b) {
        forcePreviousAndNextButtons = b;
    }

    public void setHelpAvailable(boolean b) {
        isHelpAvailable = b;
    }

    public void setNeedsProgressMonitor(boolean b) {
        needsProgressMonitor = b;
    }

    public void setTitleBarColor(RGB color) {
        titleBarColor = color;
    }

    public void setWindowTitle(String newTitle) {
        windowTitle = newTitle;
        if (container != null) {
            container.updateWindowTitle();
        }
    }

    public void removeLastPages(int pageCnt) {
        int pageSize = pages.size();
        for (int i = 0; i < pageCnt; i++) {
            ConsoleUtil.addMessage("Remove page #" + (pageSize - 1 - i), true);
            pages.remove(pageSize - 1 - i);
        }
    }

}
