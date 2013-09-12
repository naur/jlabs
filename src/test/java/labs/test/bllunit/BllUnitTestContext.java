package labs.test.bllunit;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 9/12/13.
 */
public interface BllUnitTestContext {
    Class<?> getTestClass();

    Method getTestMethod();

    Throwable getTestException();

    Map<Class<?>, Object> getEntities();
}
