package org.mozilla.browserquest.gameserver.model.stats;

import java.util.HashSet;
import java.util.Set;

public class Calculator {

    private Set<CalculatorFunction> calculatorFunctions = new HashSet<>();

    public boolean addFunction(CalculatorFunction calculatorFunction) {
        return calculatorFunctions.add(calculatorFunction);
    }

    public void calculate(CalculatorContext calculatorContext) {
        calculatorFunctions.forEach(f -> f.calc(calculatorContext));
    }
}
