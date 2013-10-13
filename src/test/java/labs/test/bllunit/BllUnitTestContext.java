package labs.test.bllunit;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;
import java.util.Map;

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
