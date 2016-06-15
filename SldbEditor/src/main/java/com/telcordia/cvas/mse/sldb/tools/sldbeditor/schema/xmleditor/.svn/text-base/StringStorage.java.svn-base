package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class StringStorage implements IStorage {
    private String name;
    private String content;

    public StringStorage(String content, String name) {
        this.content = content;
        this.name = name;
    }

    public InputStream getContents() throws CoreException {
        return new ByteArrayInputStream(content.getBytes());
    }

    public IPath getFullPath() {
        return null;
    }

    public Object getAdapter(Class adapter) {
        return null;
    }

    public String getFolderName() {
        return "";
    }

    public String getName() {
        return name;
    }

    public boolean isReadOnly() {
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StringStorage other = (StringStorage) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

}
