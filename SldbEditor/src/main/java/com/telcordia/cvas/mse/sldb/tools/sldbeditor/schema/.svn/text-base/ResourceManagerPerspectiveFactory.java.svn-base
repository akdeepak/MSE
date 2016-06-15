package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.SchemaElementsView;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.GuiView;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.XmlView;

public class ResourceManagerPerspectiveFactory implements IPerspectiveFactory {
    private static final String LEFT = "left";
    private static final String BOTTOM = "bottom";
    private static final String RIGHT = "right";

    public void createInitialLayout(IPageLayout layout) {
        IFolderLayout left = layout.createFolder(LEFT, IPageLayout.LEFT, 0.25f,
                layout.getEditorArea());

        left.addView(GuiView.ID);
        left.addView(XmlView.ID);

        IFolderLayout bottom = layout.createFolder(BOTTOM, IPageLayout.BOTTOM,
                0.8f, layout.getEditorArea());
        bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);

        IFolderLayout right = layout.createFolder(RIGHT, IPageLayout.RIGHT,
                0.8f, layout.getEditorArea());
        right.addView(SchemaElementsView.ID);
    }
}
