package org.mozilla.browserquest.mmo4j.script;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultScriptService implements ScriptService {

    private Map<String, ScriptEngine> scriptEngineMap = new ConcurrentHashMap<>();
    private ScriptContext scriptContext = new SimpleScriptContext();

    public DefaultScriptService() {
        ScriptEngineManager sem = new ScriptEngineManager();

        for (ScriptEngineFactory ef : sem.getEngineFactories()) {
            String engineName = ef.getEngineName();
            ScriptEngine engine = ef.getScriptEngine();
            addScriptEngine(engineName, engine);
        }
    }

    private void addScriptEngine(String name, ScriptEngine scriptEngine) {
        ScriptEngine engine = scriptEngineMap.get(name);

        if (engine != null) {
            double v00 = Double.parseDouble(engine.getFactory().getEngineVersion());
            double v01 = Double.parseDouble(scriptEngine.getFactory().getEngineVersion());
            if (v01 < v00) {
                return;
            }
        }
        scriptEngine.setContext(scriptContext);
        scriptEngineMap.put(name, scriptEngine);
    }

    @Override
    public void execute(ScriptEngine scriptEngine, File script) {
        if (script.exists()) {
            try {
                scriptEngine.eval(new FileReader(script), scriptContext);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @Override
    public <T> T newProxy(ScriptEngine scriptEngine, Class<T> interfaceClazz, File script) {
        return null;
    }

    @Override
    public <T> T newProxy(String engineByName, Class<T> interfaceClazz, String script) {
        return null;
    }
}
