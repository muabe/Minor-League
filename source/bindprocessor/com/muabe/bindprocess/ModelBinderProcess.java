package com.muabe.bindprocess;


import com.google.auto.service.AutoService;
import com.muabe.bindprocess.aptvalues.AptValues;
import com.muabe.bindprocess.aptvalues.ModelBinderApt;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("com.muabe.bindprocess.ModelBinder")
@AutoService(Processor.class)
public class ModelBinderProcess extends AbstractProcessor {
    ProcessorUtils util;

    AptValues v;

    ProcessUtils utils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        util = new ProcessorUtils(processingEnvironment);
        v = new ModelBinderApt(util);

        utils = new ProcessUtils(processingEnv);



        try {
            loadAndScanJar(new File("/Users/muabe/Desktop/Android-Projects/UniBoot/bindprocess/libs/tdnaVo-0.0.1.jar"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        if (!roundEnvironment.processingOver()) {
            util.note("어노테이션 찾기 시작");
            for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(v.getAnnotation())) {
//                AptValues.annotationValue(processingEnv, annotatedElement, v);
            }
            util.note("어노테이션 찾기 끝");

            util.note("전체 찾기 시작");
            for(Element element : roundEnvironment.getRootElements()){
                if(element.getKind().equals(ElementKind.CLASS)) {
                    util.note(element.getKind()+" "+element.getSimpleName());
                    if(((TypeElement)element).getAnnotation(ModelBinder.class) !=null) {
                        util.note(element.getKind() + ":" + element.getSimpleName().toString());
                        ClassDecl classDecl = new ClassDecl(processingEnv, (TypeElement)element);
                        for(FieldDecl fieldDecl : classDecl.getFieldMap().values()){
                            classDecl.addMethod("public", fieldDecl.getName()+"ddd", fieldDecl.getType().toString(), fieldDecl.getName());
                            classDecl.addSetterMethod("public", fieldDecl.getName(), fieldDecl.getType().toString(), fieldDecl.getName());
                        }


                    }
                }else if(element.getKind().equals(ElementKind.INTERFACE)){
                    //Interface일 경
                }


//                Element e = element;
//                while (!(e instanceof PackageElement)) {
//                     e = e.getEnclosingElement();
//                }
//                util.note("패키지 : "+((PackageElement)e).getQualifiedName().toString());

            }

            PackageElement pg = util.elementUtils.getPackageElement("com.skt.invites.tdna.cmm.vo");
            for(Element element : pg.getEnclosedElements()){
                utils.note(element.getSimpleName().toString());
                util.note("요기1");
                if("CodeVO".equals(element.getSimpleName().toString()) && element instanceof TypeElement) {

                    util.note("요기2");
                    ClassDecl classDecl = new ClassDecl(processingEnv, (TypeElement) element);
                    util.note("요기3");
                    for (FieldDecl fieldDecl : classDecl.getFieldMap().values()) {
                        classDecl.addMethod("public", fieldDecl.getName()+"ddd", fieldDecl.getType().toString(), fieldDecl.getName());
                    }
                    util.note("요기3");
                    create(element.getSimpleName().toString()+"Create", "name");
                    break;
                }

            }
            util.note("전체 찾기 ");
            return true;
        }
        return false;
    }

    private void create(String className, String fieldName){
        util.note("생성시");
        FieldSpec field = FieldSpec.builder(String.class, fieldName)
                .addModifiers(Modifier.PRIVATE)
                .build();

        MethodSpec setter = MethodSpec.methodBuilder("set"+fieldName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(fieldName.getClass(), fieldName)
                .addStatement("this."+fieldName+" = "+fieldName)
                .build();

        MethodSpec getter = MethodSpec.methodBuilder("get"+fieldName)
                .addModifiers(Modifier.PUBLIC)
                .returns(fieldName.getClass())
                .addStatement("return this."+fieldName )
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addField(field)
                .addMethod(setter)
                .addMethod(getter)
                .build();

        JavaFile javaFile = JavaFile.builder("com.muabe.mylibrary", helloWorld)
                .build();
        util.note(javaFile.toString());

        try {
            javaFile.writeTo(util.filer);
            util.note("생성완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<TypeElement> getTypeElementsToProcess(Set<? extends Element> elements,
                                                            Set<? extends Element> supportedAnnotations) {
        Set<TypeElement> typeElements = new HashSet<>();
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                boolean found = false;
                for (Element subElement : element.getEnclosedElements()) {
                    for (AnnotationMirror mirror : subElement.getAnnotationMirrors()) {
                        for (Element annotation : supportedAnnotations) {
                            if (mirror.getAnnotationType().asElement().equals(annotation)) {
                                typeElements.add((TypeElement) element);
                                found = true;
                                break;
                            }
                        }
                        if (found) break;
                    }
                    if (found) break;
                }
            }
        }
        return typeElements;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>(Arrays.asList(ModelBinder.class.getCanonicalName()));
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotationMirror, ExecutableElement executableElement, String s) {
        return super.getCompletions(element, annotationMirror, executableElement, s);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    public Map<String, List<Class<?>>> loadAndScanJar(File jarFile)
            throws ClassNotFoundException, ZipException, IOException {
        Map<String, List<Class<?>>> classes = new HashMap<String, List<Class<?>>>();

        List<Class<?>> interfaces = new ArrayList<Class<?>>();
        List<Class<?>> clazzes = new ArrayList<Class<?>>();
        List<Class<?>> enums = new ArrayList<Class<?>>();
        List<Class<?>> annotations = new ArrayList<Class<?>>();

        classes.put("interfaces", interfaces);
        classes.put("classes", clazzes);
        classes.put("annotations", annotations);
        classes.put("enums", enums);

        // Count the classes loaded
        int count = 0;

        // Your jar file
        JarFile jar = new JarFile(jarFile);
        // Getting the files into the jar
        Enumeration<? extends JarEntry> enumeration = jar.entries();

        // Iterates into the files in the jar file
        while (enumeration.hasMoreElements()) {
            ZipEntry zipEntry = enumeration.nextElement();

            // Is this a class?
            if (zipEntry.getName().endsWith(".class")) {

                // Relative path of file into the jar.
                String className = zipEntry.getName();

                // Complete class name
                className = className.replace(".class", "").replace("/", ".");
                // Load class definition from JVM
                Class<?> clazz = getClass().getClassLoader().loadClass(className);

                try {
                    // Verify the type of the "class"
                    if (clazz.isInterface()) {
                        interfaces.add(clazz);
                    } else if (clazz.isAnnotation()) {
                        annotations.add(clazz);
                    } else if (clazz.isEnum()) {
                        enums.add(clazz);
                    } else {
                        clazzes.add(clazz);
                    }

                    count++;
                } catch (ClassCastException e) {

                }
            }
        }

        System.out.println("Total: " + count);

        return classes;
    }
}





// 패키지명 가져올때
//    final Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(CharlesIntent.class);
//
//        for (Element element : elements) {
//                if(packageName==null){
//                Element e = element;
//                while (!(e instanceof PackageElement)) {
//                e = e.getEnclosingElement();
//                }
//                packageName = ((PackageElement)e).getQualifiedName().toString();
//                }
