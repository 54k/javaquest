package org.mozilla.browserquest.mmo4j.script;

import javax.script.ScriptEngine;
import java.io.File;

public interface ScriptService {

    void execute(ScriptEngine scriptEngine, File script);

    <T> T newProxy(ScriptEngine scriptEngine, Class<T> interfaceClazz, File script);

    <T> T newProxy(String engineByName, Class<T> interfaceClazz, String script);
}
