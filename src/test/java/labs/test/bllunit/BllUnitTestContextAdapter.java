package labs.test.bllunit;

import org.junit.runners.model.FrameworkMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 10/13/13.
 */
class BllUnitTestContextAdapter implements BllUnitTestContext {

    private FrameworkMethod method;
    private Object target;
    private Throwable testException;
    private Map<Class<?>, ApplicationContext> applicationContexts;

    public BllUnitTestContextAdapter(FrameworkMethod method, Object target) {
        this.method = method;
        this.target = target;
        this.applicationContexts = new HashMap<Class<?>, ApplicationContext>();
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

    public <T> T getBean(Class<T> clazz) {
        T t = null;
        for (ApplicationContext context : this.applicationContexts.values()) {
            t = (T) context.getBean(clazz);
            if (t != null) {
                break;
            }
        }
        return t;
    }

    @Override
    public <T extends ApplicationContext> T getConfigContext(Class<T> clazz) {
        if (!this.applicationContexts.containsKey(clazz)) {
            try {
                this.applicationContexts.put(clazz, clazz.newInstance());
            } catch (Exception ex) {
            }
        }
        return (T) this.applicationContexts.get(clazz);
    }

    public void setTestException(Throwable e) {
        this.testException = e;
    }
}
