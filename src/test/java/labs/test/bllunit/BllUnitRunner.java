package labs.test.bllunit;

import labs.test.bllunit.annotation.BllAfter;
import labs.test.bllunit.annotation.BllBefore;
import labs.test.models.Person;
import labs.test.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 9/12/13.
 */
class BllUnitRunner {

    private static final Logger logger = LoggerFactory.getLogger(BllUnitRunner.class);

    public void beforeTestMethod(BllUnitTestContext testContext) throws Exception {
        Collection<BllBefore> annotations = getAnnotations(testContext, BllBefore.class);
        beforeOrAfter(testContext, true, AnnotationAttributes.get(annotations));
    }

    public void afterTestMethod(BllUnitTestContext testContext) throws Exception {
        try {
            Collection<BllAfter> annotations = getAnnotations(testContext, BllAfter.class);
            try {
                beforeOrAfter(testContext, false, AnnotationAttributes.get(annotations));
            } catch (RuntimeException e) {
                if (testContext.getTestException() == null) {
                    throw e;
                }
                if (logger.isWarnEnabled()) {
                    logger.warn("Unable to throw database cleanup exception due to existing test error", e);
                }
            }
        } finally {
        }
    }

    private void beforeOrAfter(BllUnitTestContext testContext, boolean isSetup,
                               Collection<AnnotationAttributes> annotations) throws Exception {
        for (AnnotationAttributes annotation : annotations) {
            for (String value : annotation.getValue()) {
                testContext.getEntities().put(Class.forName(value), Class.forName(value).newInstance());
            }
        }
//        testContext.getEntities().put(Person.class, new Person() {{
//            setId(618);
//            setName("demo person");
//            setType("test");
//        }});
//        testContext.getEntities().put(User.class, new User() {{
//            setId(1900);
//            setName("demo user");
//            setComment("test");
//        }});
    }

    private <T extends Annotation> Collection<T> getAnnotations(BllUnitTestContext testContext, Class<T> annotationType) {
        List<T> annotations = new ArrayList<T>();
        addAnnotationToList(annotations, AnnotationUtils.findAnnotation(testContext.getTestClass(), annotationType));
        addAnnotationToList(annotations, AnnotationUtils.findAnnotation(testContext.getTestMethod(), annotationType));
        return annotations;
    }

    private <T extends Annotation> void addAnnotationToList(List<T> annotations, T annotation) {
        if (annotation != null) {
            annotations.add(annotation);
        }
    }

    private static class AnnotationAttributes {

        private String[] value;

        public AnnotationAttributes(Annotation annotation) {
            Assert.state((annotation instanceof BllBefore) || (annotation instanceof BllAfter),
                    "Only BllBefore and BllAfter annotations are supported");
            Map<String, Object> attributes = AnnotationUtils.getAnnotationAttributes(annotation);
            this.value = (String[]) attributes.get("value");
        }

        public String[] getValue() {
            return this.value;
        }

        public static <T extends Annotation> Collection<AnnotationAttributes> get(Collection<T> annotations) {
            List<AnnotationAttributes> annotationAttributes = new ArrayList<AnnotationAttributes>();
            for (T annotation : annotations) {
                annotationAttributes.add(new AnnotationAttributes(annotation));
            }
            return annotationAttributes;
        }
    }
}
