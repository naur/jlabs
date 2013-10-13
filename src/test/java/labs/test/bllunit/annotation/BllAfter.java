package labs.test.bllunit.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 9/12/13.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface BllAfter {
    String[] value() default "";
}
