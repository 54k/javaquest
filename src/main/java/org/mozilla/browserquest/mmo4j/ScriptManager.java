package org.mozilla.browserquest.mmo4j;

import com.google.common.reflect.Reflection;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ScriptManager implements Runnable {

    @Override
    public void run() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        Bindings bindings = scriptEngine.createBindings();
        bindings.put("ScriptManager", this);
        scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("scripts/testScript.js");

            Object impl = scriptEngine.eval(new InputStreamReader(resourceAsStream));
            Invocable invocable = (Invocable) scriptEngine;

            Reflection.newProxy(Runnable.class, new ScriptHandler(scriptEngine, invocable, impl)).run();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void doSome() {
        System.out.println("hello from javascript");
    }

    private static class ScriptHandler implements InvocationHandler {
        private ScriptEngine scriptEngine;
        private Invocable invocable;
        private Object object;

        private ScriptHandler(ScriptEngine scriptEngine, Invocable invocable, Object object) {
            this.scriptEngine = scriptEngine;
            this.invocable = invocable;
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return invocable.invokeMethod(object, method.getName(), args);
        }
    }
}
