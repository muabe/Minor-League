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
           
        }


        for (TypeElement typeElement : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                Tester tester = element.getAnnotation(Tester.class);
                System.out.println("------------------------------------------------");
                if (tester != null) {
					System.out.println("isClass:"+typeElement.getKind().isClass());
                    System.out.println("isField:"+typeElement.getKind().isField());
                    System.out.println("isInterface:"+typeElement.getKind().isInterface());

                    System.out.println("SimpleName:"+typeElement.getSimpleName());
                    System.out.println("NestingKind:"+typeElement.getNestingKind().toString());
                    System.out.println("QualifiedName:"+typeElement.getQualifiedName());
                    System.out.println("EnclosedElements Size:"+typeElement.getEnclosedElements().size());
                    System.out.println("EnclosingElement:"+typeElement.getEnclosingElement().toString());
                    System.out.println("TypeParameters Size:"+typeElement.getTypeParameters().size());
                    System.out.println("Interfaces Size:"+typeElement.getInterfaces().size());
                    System.out.println("getKind toString :"+typeElement.getKind().toString());
                    System.out.println("**************************************************");
                    System.out.println("ÇÔ¼ö¸í:" + element);
                    System.out.println("SimpleName:"+element.getSimpleName());
                    System.out.println("getKind:"+element.getKind().toString());
                    System.out.println("asType:"+element.asType().toString());
                    System.out.println("getAnnotationMirrors:"+element.getAnnotationMirrors().get(0).getAnnotationType());
                    System.out.println("getEnclosedElements size:"+element.getEnclosedElements().size());
                    System.out.println("getEnclosingElement:"+element.getEnclosingElement().toString());
                }else{
                    System.out.println("Tester Null!!!");
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