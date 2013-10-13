package labs.test.bllunit;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 9/12/13.
 */
public class BllUnitRule implements MethodRule {

    private Map<String, BllUnitTestContext> adapters = new HashMap<String, BllUnitTestContext>();

    private static BllUnitRunner runner = new BllUnitRunner();

    public <T> T getBean(Class<T> clazz) {
        if (!adapters.containsKey(getCaller())) return null;

        return (T) adapters.get(getCaller()).getConfigContext().getBean(clazz);
    }

    private String getCaller() {
        return new Throwable().getStackTrace()[2].getMethodName();
    }

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        BllUnitTestContextAdapter adapter = new BllUnitTestContextAdapter(method, target);
        adapters.put(method.getName(), adapter);
        return new BllUnitStatement(adapter, base);
    }

    private class BllUnitStatement extends Statement {

        private Statement nextStatement;
        private BllUnitTestContextAdapter testContext;

        public BllUnitStatement(BllUnitTestContextAdapter testContext, Statement nextStatement) {
            this.testContext = testContext;
            this.nextStatement = nextStatement;
        }

        @Override
        public void evaluate() throws Throwable {
            runner.beforeTestMethod(this.testContext);
            try {
                this.nextStatement.evaluate();
            } catch (Throwable e) {
                this.testContext.setTestException(e);
            }
            runner.afterTestMethod(this.testContext);
        }
    }
}
