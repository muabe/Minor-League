package com.muabe.bindprocess.aptvalues;

import com.muabe.bindprocess.ProcessorUtils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

public abstract class AptValues {
    ProcessorUtils util;

    public AptValues(ProcessorUtils util) {
        this.util = util;
    }
    public abstract ElementKind getKind();

    public abstract void value(ProcessingEnvironment processingEnv, Element element, AnnotationMirror mirror, String name, Object value);

    public abstract Class<? extends Annotation> getAnnotation();

    public static void annotationValue(ProcessingEnvironment processingEnv, Element annotatedElement, AptValues aptValues){
        if(annotatedElement.getKind() == aptValues.getKind()) {
            String packageName = aptValues.util.elementUtils.getPackageOf(annotatedElement).getQualifiedName().toString();

            List<? extends AnnotationMirror> annotationMirrors = annotatedElement.getAnnotationMirrors();
            for (AnnotationMirror mirror : annotationMirrors) {
                if (aptValues.getAnnotation().getCanonicalName().equals(mirror.getAnnotationType().toString())) {
                    Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = mirror.getElementValues();
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                        String key = entry.getKey().getSimpleName().toString();
                        Object value = entry.getValue().getValue();
                        aptValues.value(processingEnv, annotatedElement, mirror, key, value);
                    }
                }
            }
        }
    }
}
