package labs.test.bllunit;

import org.junit.runners.model.FrameworkMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
    //    private Map<Class<?>, Object> nameEntities;
//    private Map<String, Object> classEntities;
    private AnnotationConfigApplicationContext applicationContext;

    public BllUnitTestContextAdapter(FrameworkMethod method, Object target) {
        this.method = method;
        this.target = target;
//        this.nameEntities = new HashMap<Class<?>, Object>();
//        this.classEntities = new HashMap<String, Object>();
        this.applicationContext = new AnnotationConfigApplicationContext();
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
    public AnnotationConfigApplicationContext getConfigContext() {
        return applicationContext;
    }

    public void setTestException(Throwable e) {
        this.testException = e;
    }
}
