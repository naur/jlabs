package labs.research.test.bllunit;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 9/12/13.
 */
public interface BllUnitTestContext {
    Class<?> getTestClass();

    Method getTestMethod();

    Throwable getTestException();

    <T extends ApplicationContext> T getConfigContext(Class<T> clazz);

//    enum ConfigContextType {
//        AnnotationConfig,
//        ClassPathXml;
//    }
}
