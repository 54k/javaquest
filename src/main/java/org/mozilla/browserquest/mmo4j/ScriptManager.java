package org.mozilla.browserquest.mmo4j;

import com.google.common.reflect.Reflection;
import com.google.inject.Injector;

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
import java.util.function.Function;

public class ScriptManager implements Runnable {

    private Injector injector;

    public ScriptManager(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void run() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        Bindings bindings = scriptEngine.createBindings();
        bindings.put("ScriptManager", this);
        bindings.put("inject", (Function<String, Object>) s -> {
            try {
                return injector.getInstance(Class.forName(s));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Injection of " + s + " failed");
        });
        scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("scripts/testScript.js");

            Object impl = scriptEngine.eval(new InputStreamReader(resourceAsStream));
            Invocable invocable = (Invocable) scriptEngine;

            Reflection.newProxy(Runnable.class, new ScriptHandler(invocable, impl)).run();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void doSome() {
        System.out.println("hello from javascript");
    }

    private static class ScriptHandler implements InvocationHandler {

        private Invocable invocable;
        private Object object;

        private ScriptHandler(Invocable invocable, Object object) {
            this.invocable = invocable;
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return invocable.invokeMethod(object, method.getName(), args);
        }
    }
}
