package labs.test.bllunit;

import labs.entities.User;
import labs.test.bllunit.annotation.BllAfter;
import labs.test.bllunit.annotation.BllBefore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.*;

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
            if (testContext.getTestException() != null) {
                logger.error("BllUnitTestException", new Exception(testContext.getTestException()));
            }
        }
    }

    /**
     * beforeOrAfter
     */
    private void beforeOrAfter(BllUnitTestContext testContext, boolean isSetup,
                               Collection<AnnotationAttributes> annotations) throws Exception {
        List<String> str = new ArrayList<String>();
        for (AnnotationAttributes annotation : annotations) {
            str.addAll(Arrays.asList(annotation.getXml()));
            //register BllBefore.config 指定的 Configuration
            if (annotation.getConfig().length > 0) {
                testContext.getConfigContext(AnnotationConfigApplicationContext.class).register(annotation.getConfig());
                testContext.getConfigContext(AnnotationConfigApplicationContext.class).refresh();
            }
        }
        //register BllBefore.xml 指定的 Configuration
        if (!str.isEmpty())
            testContext.getConfigContext(ClassPathXmlApplicationContext.class).setConfigLocations(str.toArray(new String[0]));
    }

    /**
     * 收集 BllBefore 和 BllAfter 的注解信息
     */
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

        private String[] xml;
        private String[] json;
        private String[] ini;
        private Class<?>[] config;

        public AnnotationAttributes(Annotation annotation) {
            Assert.state((annotation instanceof BllBefore) || (annotation instanceof BllAfter),
                    "Only BllBefore and BllAfter annotations are supported");
            Map<String, Object> attributes = AnnotationUtils.getAnnotationAttributes(annotation);
            this.xml = (String[]) attributes.get("xml");
            this.json = (String[]) attributes.get("json");
            this.ini = (String[]) attributes.get("ini");
            this.config = (Class<?>[]) attributes.get("config");
        }

        public String[] getXml() {
            return this.xml;
        }

        public String[] getjson() {
            return this.json;
        }

        public String[] getIni() {
            return this.ini;
        }

        public Class<?>[] getConfig() {
            return this.config;
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
