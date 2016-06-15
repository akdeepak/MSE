package com.telcordia.cvas.mse.sldb.tools.sldbeditor.common;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;


public class ConsoleUtil {
    public static void addMessage(String message, boolean debugInfo) {
        if (debugInfo) {
            if (!Constant.CONSOLE_IN_DEBUG_MODE) {
                return;
            }
        }
        MessageConsole messageConsole = findConsole("Console");
        MessageConsoleStream out = messageConsole.newMessageStream();
        out.println(message);
    }

    private static MessageConsole findConsole(String name) {
        ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
        IConsoleManager consoleManager = consolePlugin.getConsoleManager();
        IConsole[] consoles = consoleManager.getConsoles();
        for (int i = 0; i < consoles.length; i++)
            if (name.equals(consoles[i].getName()))
                return (MessageConsole) consoles[i];
        //no console found, so create a new one
        MessageConsole myConsole = new MessageConsole(name, null);
        consoleManager.addConsoles(new IConsole[] { myConsole });
        return myConsole;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/common/ConsoleUtil.java $ $Rev: 49464 $ $LastChangedBy: dkarunan $ $Date: 2011-09-08 23:04:08 +0530 (Thu, 08 Sep 2011) $" };
}
