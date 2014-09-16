package org.mozilla.browserquest.script;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.reflect.Reflection;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// TODO cache for compiled scripts
public class DefaultScriptService implements ScriptService {

    public static final File SCRIPT_FOLDER = new File("scripts");

    private Map<String, ScriptEngine> scriptEngineByName = new ConcurrentHashMap<>();
    private Map<String, ScriptEngine> scriptEngineByExtension = new ConcurrentHashMap<>();

    public DefaultScriptService() {
        load();
    }

    private void load() {
        ScriptEngineManager sem = new ScriptEngineManager();
        for (ScriptEngineFactory scriptEngineFactory : sem.getEngineFactories()) {
            String scriptEngineName = scriptEngineFactory.getEngineName();
            ScriptEngine engine = scriptEngineFactory.getScriptEngine();
            registerScriptEngine(scriptEngineName, engine);
        }
    }

    @Override
    public void executeScript(File script) {
        executeScript(script, new SimpleScriptContext());
    }

    @Override
    public void executeScript(File script, ScriptContext scriptContext) {
        checkIsFile(script);
        ScriptEngine engine = getEngineForFile(script);
        executeScript(engine, script, scriptContext);
    }

    private ScriptEngine getEngineForFile(File script) {
        String extension = Files.getFileExtension(script.getName());
        if (Strings.isNullOrEmpty(extension)) {
            throw new RuntimeException("Script file (" + script.getName() + ") doesn't has an extension that identifies the ScriptEngine to be used.");
        }
        return getEngineByExtension(extension);
    }

    private ScriptEngine getEngineByExtension(String extension) {
        ScriptEngine scriptEngine = scriptEngineByExtension.get(extension);
        if (scriptEngine == null) {
            throw new RuntimeException("No engine registered for extension (" + extension + ")");
        }
        return scriptEngine;
    }

    @Override
    public void executeScript(String scriptEngineName, File script) {
        executeScript(scriptEngineName, script, new SimpleScriptContext());
    }

    @Override
    public void executeScript(String scriptEngineName, File script, ScriptContext scriptContext) {
        ScriptEngine engine = getEngineByName(scriptEngineName);
        executeScript(engine, script, scriptContext);
    }

    private ScriptEngine getEngineByName(String scriptEngineName) {
        ScriptEngine scriptEngine = scriptEngineByName.get(scriptEngineName);
        if (scriptEngine == null) {
            throw new RuntimeException("No engine registered with name (" + scriptEngineName + ")");
        }
        return scriptEngine;
    }

    @Override
    public void executeScriptsInDirectory(File scriptDirectory) {
        executeScriptsInDirectory(scriptDirectory, new SimpleScriptContext());
    }

    @Override
    public void executeScriptsInDirectory(File scriptDirectory, ScriptContext scriptContext) {
        checkIsDirectory(scriptDirectory);
        File[] scripts = Optional.ofNullable(scriptDirectory.listFiles(File::isFile)).orElse(new File[0]);
        for (File script : scripts) {
            executeScript(script, scriptContext);
        }
    }

    @Override
    public void executeScriptsInDirectory(String scriptEngineName, File scriptDirectory) {
        executeScript(scriptEngineName, scriptDirectory, new SimpleScriptContext());
    }

    @Override
    public void executeScriptsInDirectory(String scriptEngineName, File scriptDirectory, ScriptContext scriptContext) {
        checkIsDirectory(scriptDirectory);
        ScriptEngine engine = getEngineByName(scriptEngineName);

        File[] scripts = Optional.ofNullable(scriptDirectory.listFiles(File::isFile)).orElse(new File[0]);
        for (File script : scripts) {
            executeScript(engine, script, scriptContext);
        }
    }

    @Override
    public <T> T newProxy(Class<T> interfaceClass, File script) {
        return newProxy(interfaceClass, script, new SimpleScriptContext());
    }

    @Override
    public <T> T newProxy(Class<T> interfaceClass, File script, ScriptContext scriptContext) {
        checkIsFile(script);
        ScriptEngine engine = getEngineForFile(script);
        return newProxy(interfaceClass, engine, script, scriptContext);
    }

    @Override
    public <T> T newProxy(String scriptEngineName, Class<T> interfaceClass, File script) {
        return newProxy(scriptEngineName, interfaceClass, script, new SimpleScriptContext());
    }

    @Override
    public <T> T newProxy(String scriptEngineName, Class<T> interfaceClass, File script, ScriptContext scriptContext) {
        checkIsFile(script);
        ScriptEngine engine = getEngineByName(scriptEngineName);
        return newProxy(interfaceClass, engine, script, scriptContext);
    }

    private <T> T newProxy(Class<T> interfaceClass, ScriptEngine scriptEngine, File script, ScriptContext context) {
        Preconditions.checkArgument(scriptEngine instanceof Invocable, "Engine (%s) is not instance of %s", scriptEngine, Invocable.class.getSimpleName());
        Object scriptObject = executeScript(scriptEngine, script, context);
        if (scriptObject == null) {
            throw new RuntimeException("Script file (" + script.getName() + ") doesn't export any object for proxy creation.");
        }
        @SuppressWarnings("ConstantConditions") Invocable invocable = (Invocable) scriptEngine;
        return Reflection.newProxy(interfaceClass, new ScriptInvocationHandler(invocable, scriptObject));
    }

    private Object executeScript(ScriptEngine scriptEngine, File script, ScriptContext context) {
        context.setAttribute(ScriptEngine.FILENAME, script.getName(), ScriptContext.ENGINE_SCOPE);
        context.setAttribute("classpath", SCRIPT_FOLDER.getAbsolutePath(), ScriptContext.ENGINE_SCOPE);
        context.setAttribute("sourcepath", SCRIPT_FOLDER.getAbsolutePath(), ScriptContext.ENGINE_SCOPE);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(script)))) {
            return executeScript(scriptEngine, reader, context);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            scriptEngine.getContext().removeAttribute(ScriptEngine.FILENAME, ScriptContext.ENGINE_SCOPE);
        }
    }

    private Object executeScript(ScriptEngine scriptEngine, Reader reader, ScriptContext context) throws ScriptException {
        if (scriptEngine instanceof Compilable) {
            Compilable compilable = (Compilable) scriptEngine;
            CompiledScript compiledScript = compilable.compile(reader);
            return compiledScript.eval(context);
        }
        return scriptEngine.eval(reader, context);
    }

    private static void checkIsFile(File script) {
        Preconditions.checkArgument(script.isFile(), "Argument (%s) is not a file.", script);
    }

    private static void checkIsDirectory(File scriptDirectory) {
        Preconditions.checkArgument(scriptDirectory.isDirectory(), "Argument (%s) is not a directory.", scriptDirectory);
    }

    @Override
    public boolean registerScriptEngine(String scriptEngineName, ScriptEngine scriptEngine) {
        ScriptEngine prevScriptEngine = scriptEngineByName.get(scriptEngineName);
        ScriptEngineFactory scriptEngineFactory = scriptEngine.getFactory();

        if (prevScriptEngine != null) {
            String prevEngineVersion = prevScriptEngine.getFactory().getEngineVersion();
            String engineVersion = scriptEngineFactory.getEngineVersion();
            double v1 = Double.parseDouble(prevEngineVersion);
            double v2 = Double.parseDouble(engineVersion);
            if (v2 <= v1) {
                return false;
            }
        }

        scriptEngineByName.put(scriptEngineName, scriptEngine);
        scriptEngineFactory.getExtensions().forEach(ext -> scriptEngineByExtension.put(ext, scriptEngine));
        return true;
    }

    @Override
    public boolean unregisterScriptEngine(String scriptEngineName) {
        ScriptEngine scriptEngine = scriptEngineByName.remove(scriptEngineName);
        if (scriptEngine == null) {
            return false;
        }

        ScriptEngineFactory scriptEngineFactory = scriptEngine.getFactory();
        scriptEngineFactory.getExtensions().forEach(scriptEngineByExtension::remove);
        return true;
    }

    private static class ScriptInvocationHandler implements InvocationHandler {

        private final Invocable invoker;
        private final Object scriptObject;

        private ScriptInvocationHandler(Invocable invoker, Object scriptObject) {
            this.invoker = invoker;
            this.scriptObject = scriptObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return invoker.invokeMethod(scriptObject, method.getName(), args);
        }
    }
}
