import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;
import com.Tester;

/**
 * Created by Muabe on 2018-05-02.
 */
public class Lib extends AbstractProcessor {
    private int round = 1;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("round : "+(this.round++));
        Iterator<? extends  TypeElement> iter = annotations.iterator();
        while (iter.hasNext()){
            TypeElement el = iter.next();
            System.out.println("------------------------------------------------");
            System.out.println("SimpleName:"+el.getSimpleName());
            System.out.println("NestingKind:"+el.getNestingKind().toString());
            System.out.println("QualifiedName:"+el.getQualifiedName());
            System.out.println("EnclosedElements Size:"+el.getEnclosedElements().size());
            System.out.println("EnclosingElement:"+el.getEnclosingElement().toString());
            System.out.println("TypeParameters Size:"+el.getTypeParameters().size());
            System.out.println("Interfaces Size:"+el.getInterfaces().size());
            System.out.println("getKind toString :"+el.getKind().toString());
        }

		for (TypeElement typeElement : annotations) {

            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {

                Override override = element.getAnnotation(Override.class);

                if (override != null) {
                    System.out.println("@Override at " + element);
                }
            }
        }


        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
		System.out.println("getSupportedAnnotationTypes:"+Tester.class.getName());
		set.add(Tester.class.getName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}