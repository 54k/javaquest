package org.mozilla.browserquest.script;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import java.io.File;

public interface ScriptService {

    void executeScript(File script);

    void executeScript(File script, ScriptContext scriptContext);

    void executeScript(String scriptEngineName, File script);

    void executeScript(String scriptEngineName, File script, ScriptContext scriptContext);

    void executeScriptsInDirectory(File scriptDirectory);

    void executeScriptsInDirectory(File scriptDirectory, ScriptContext scriptContext);

    void executeScriptsInDirectory(String scriptEngineName, File scriptDirectory);

    void executeScriptsInDirectory(String scriptEngineName, File scriptDirectory, ScriptContext scriptContext);

    <T> T newProxy(Class<T> interfaceClass, File script);

    <T> T newProxy(Class<T> interfaceClass, File script, ScriptContext scriptContext);

    <T> T newProxy(String scriptEngineName, Class<T> interfaceClass, File script);

    <T> T newProxy(String scriptEngineName, Class<T> interfaceClass, File script, ScriptContext scriptContext);

    boolean registerScriptEngine(String scriptEngineName, ScriptEngine scriptEngine);

    boolean unregisterScriptEngine(String scriptEngineName);
}
