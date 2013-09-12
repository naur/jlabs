package labs.test.bllunit;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

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

        return (T) adapters.get(getCaller()).getEntities().get(clazz);
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

    protected class BllUnitTestContextAdapter implements BllUnitTestContext {

        private FrameworkMethod method;
        private Object target;
        private Throwable testException;
        private Map<Class<?>, Object> entities;

        public BllUnitTestContextAdapter(FrameworkMethod method, Object target) {
            this.method = method;
            this.target = target;
            this.entities = new HashMap<Class<?>, Object>();
        }

        public Class<?> getTestClass() {
            return this.target.getClass();
        }

        public Method getTestMethod() {
            return this.method.getMethod();
        }

        public Throwable getTestException() {
            return this.testException;
        }

        @Override
        public Map<Class<?>, Object> getEntities() {
            return this.entities;
        }

        public void setTestException(Throwable e) {
            this.testException = e;
        }
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
