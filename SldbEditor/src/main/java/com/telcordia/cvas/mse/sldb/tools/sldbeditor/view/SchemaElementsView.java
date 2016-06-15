package com.telcordia.cvas.mse.sldb.tools.sldbeditor.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.editor.MultiPageEditor;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.guieditor.SchemaEditor;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.FieldTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.SchTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.TableTemplate;

/**
 * This class implements view with templates for editing schemas.
 * 
 * NewSchemaElementsView
 * 
 * @author igor.golovan
 * @version $Rev$
 */
public class SchemaElementsView extends ViewPart {

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.", "@(#) $URL$ $Rev$ $LastChangedBy$ $Date$" };

    public static final String ID = "com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.schemaElementsView";
    private FormToolkit toolkit;
    private Form form;

    private Map<String, FieldTemplate> fieldsTemplates;
    private Map<String, TableTemplate> tablesTemplates;

    /**
     * Read templates from XML file and store in maps
     */
    public SchemaElementsView() {
        // ApplicationContext factory = new ClassPathXmlApplicationContext(
        // "../schemaTemplateContext.xml");
        // tablesTemplates = (Map<String, TableTemplate>)
        // factory.getBeansOfType(TableTemplate.class);
        // fieldsTemplates = (Map<String, FieldTemplate>)
        // factory.getBeansOfType(FieldTemplate.class);

        tablesTemplates = new HashMap<String, TableTemplate>();
        tablesTemplates.put("defaultTable", new TableTemplate("defaultTable",
                "Embedded Table"));

        fieldsTemplates = new HashMap<String, FieldTemplate>();
        fieldsTemplates.put("defaultField", new FieldTemplate("defaultField",
                "Field"));
    }

    /**
     * Create and add template hyperlink to view
     * 
     * @param parent
     * @param template
     */
    private void addTemplate(Composite parent, final SchTemplate template) {
        Button newSchemaLink = toolkit.createButton(parent,
                template.getLabel(), SWT.NONE);

        newSchemaLink.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
                ISharedImages.IMG_OBJS_DND_TOFASTVIEW_SOURCE));

        DragSource ds = new DragSource(newSchemaLink, DND.Drop);
        ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });

        ds.addDragListener(new DragSourceAdapter() {
            public void dragSetData(DragSourceEvent event) {
                event.data = template.getId();
            }
        });

    }

    /**
     * Find and return template by name.
     * 
     * @param templateName
     * @return
     */
    public SchTemplate getTemplate(String templateName) {
        SchTemplate template = tablesTemplates.get(templateName);
        if (template != null) {
            return template;
        }
        template = fieldsTemplates.get(templateName);
        return template;
    }

    /**
     * Creates view and adds templates in it.
     */
    @Override
    public void createPartControl(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
        form = toolkit.createForm(parent);
        TableWrapLayout layout = new TableWrapLayout();
        form.getBody().setLayout(layout);
        for (FieldTemplate template : fieldsTemplates.values()) {
            addTemplate(form.getBody(), template);
        }
        for (TableTemplate template : tablesTemplates.values()) {
            addTemplate(form.getBody(), template);
        }
    }

    @Override
    public void setFocus() {
        form.setFocus();
    }

}
