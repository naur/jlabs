package labs.research.test.bllunit.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 9/12/13.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface BllBefore {
    String[] xml() default {};

    String[] json() default {};

    String[] ini() default {};

    Class<?>[] config() default {};
}
