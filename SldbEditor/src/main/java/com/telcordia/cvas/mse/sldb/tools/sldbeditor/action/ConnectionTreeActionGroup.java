package com.telcordia.cvas.mse.sldb.tools.sldbeditor.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionGroup;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.RecordBranch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.RecordLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaBranch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.ConnectionsView;

public class ConnectionTreeActionGroup extends ActionGroup {

    public ConnectionTreeActionGroup() {
    }

    public void fillContextMenu(IMenuManager menu) {
        ConnectionsView view = Activator.getDefault().getConnectionsView();
        //  Object selection[] = view != null ? view.getSelected() : null;
        Object selection[] = view.getSelected();
        System.out.println("SELECTION {} ---->" + selection);
        if (selection == null || selection.length != 1) {
            addAction(menu, new NewConnectionAction());
            return;
        }

        else if (selection[0] instanceof Connection) {
            System.out.println("Am in connection");
            addAction(menu, new NewConnectionAction());
            addAction(menu, new CloseConnectionAction());
        }
        else if (selection[0] instanceof SchemaBranch
                || selection[0] instanceof SchemaLeaf) {
            System.out.println("Am in schema Branch");
            addAction(menu, new CreateSchemaAction());
            addAction(menu, new RetrieveSchemaAction());
            addAction(menu, new RetrieveAllSchemaAction());
            addAction(menu, new RemoveSchemaAction());
        }
        else if (selection[0] instanceof RecordBranch
                || selection[0] instanceof RecordLeaf) {
            System.out.println("Am in Record Branch");
            addAction(menu, new CreateSdrAction());
            addAction(menu, new RetrieveSdrAction());
            addAction(menu, new DeleteSdrAction());
        }
    }

    private boolean addAction(IMenuManager menu, AbstractSeAction action) {
        if (action.isAvailable()) {
            menu.add(action);
            action.setEnabled(true);
            return true;
        }
        else {
            return false;
        }
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/action/ConnectionTreeActionGroup.java $ $Rev: 49689 $ $LastChangedBy: dkarunan $ $Date: 2011-09-12 16:39:57 +0530 (Mon, 12 Sep 2011) $" };
}

/*public class ConnectionTreeActionGroup extends ActionGroup
{

    public ConnectionTreeActionGroup()
    {
    }

    public void fillContextMenu(IMenuManager menu)
    {
        ConnectionsView view = SQLExplorerPlugin.getDefault().getConnectionsView();
        Object selection[] = view != null ? view.getSelected() : null;
        if(selection == null || selection.length != 1)
        {
            addAction(menu, new NewAliasAction());
            return;
        }
        if(selection[0] instanceof Alias)
        {
            Alias alias = (Alias)selection[0];
            addAction(menu, new NewEditorAction());
            addAction(menu, new NewDatabaseStructureViewAction());
            addAction(menu, new ConnectAliasAction());
            menu.add(new Separator());
            for(Iterator iterator = alias.getUsers().iterator(); iterator.hasNext();)
            {
                User user = (User)iterator.next();
                if(!user.isAutoCommit())
                {
                    addAction(menu, new CommitAction());
                    addAction(menu, new RollbackAction());
                    menu.add(new Separator());
                    break;
                }
            }

            if(view.getSelectedConnections(true).size() > 0)
            {
                addAction(menu, new CloseAllConnectionsAction());
                menu.add(new Separator());
            }
            addAction(menu, new NewUserAction());
            addAction(menu, new ChangeAliasAction());
            addAction(menu, new CopyAliasAction());
            addAction(menu, new DeleteAction());
        } else
        if(selection[0] instanceof User)
        {
            User user = (User)selection[0];
            addAction(menu, new NewEditorAction());
            addAction(menu, new NewDatabaseStructureViewAction());
            addAction(menu, new ConnectAliasAction());
            menu.add(new Separator());
            if(!user.isAutoCommit())
            {
                addAction(menu, new CommitAction());
                addAction(menu, new RollbackAction());
                menu.add(new Separator());
            }
            if(view.getSelectedConnections(true).size() > 0)
            {
                addAction(menu, new CloseAllConnectionsAction());
                menu.add(new Separator());
            }
            addAction(menu, new NewUserAction());
            addAction(menu, new EditUserAction());
            addAction(menu, new CopyUserAction());
            addAction(menu, new DeleteAction());
        } else
        if(selection[0] instanceof SQLConnection)
        {
            SQLConnection connection = (SQLConnection)selection[0];
            addAction(menu, new NewEditorAction());
            addAction(menu, new NewDatabaseStructureViewAction());
            menu.add(new Separator());
            if(!connection.getUser().isAutoCommit())
            {
                addAction(menu, new CommitAction());
                addAction(menu, new RollbackAction());
                menu.add(new Separator());
            }
            addAction(menu, new CloseConnectionAction());
        }
    }

    private boolean addAction(IMenuManager menu, AbstractSeAction action)
    {
        if(action.isAvailable())
        {
            menu.add(action);
            action.setEnabled(true);
            return true;
        } else
        {
            return false;
        }
    }*/