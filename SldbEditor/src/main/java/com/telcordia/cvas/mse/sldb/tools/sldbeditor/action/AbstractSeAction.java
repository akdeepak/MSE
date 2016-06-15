package com.telcordia.cvas.mse.sldb.tools.sldbeditor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.ImageUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeMessages;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.ConnectionsView;

public abstract class AbstractSeAction extends Action implements
        IViewActionDelegate {
    public AbstractSeAction(String textId, String toolTipId, String imageId) {
        this(textId, toolTipId, imageId, 0);
    }

    public AbstractSeAction(String textId, String toolTipId, String imageId,
            int style) {
        super(SeMessages.getString(textId));
        setToolTipText(SeMessages.getString(toolTipId == null
                ? textId
                : toolTipId));
        if (imageId != null) {
            org.eclipse.jface.resource.ImageDescriptor image = ImageUtil.getDescriptor(imageId);
            setImageDescriptor(image);
            setHoverImageDescriptor(image);
        }
    }

    public void init(IViewPart iviewpart) {
    }

    public void run(IAction action) {
        run();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(isAvailable());
    }

    public boolean isAvailable() {
        return true;
    }

    public boolean isNotAvailable() {
        return false;
    }

    public String getId() {
        return "AbstractSeAction";
    }

    protected ConnectionsView getView() {
        return Activator.getDefault().getConnectionsView();
    }
}
