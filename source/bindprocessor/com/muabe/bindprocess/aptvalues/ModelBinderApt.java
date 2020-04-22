package com.muabe.bindprocess.aptvalues;

import com.muabe.bindprocess.ModelBinder;
import com.muabe.bindprocess.ProcessorUtils;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Name;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public class ModelBinderApt extends AptValues{

    public ModelBinderApt(ProcessorUtils util) {
        super(util);
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return ModelBinder.class;
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.CLASS;
    }

    @Override
    public void value(ProcessingEnvironment processingEnv, Element element, AnnotationMirror mirror, String name, Object value) {
        if(name.equals("value")){
            TypeElement el = (TypeElement) element;

            util.note("바인더 ->" + element.getKind() + el.getQualifiedName().toString());

            JavacProcessingEnvironment environment = (JavacProcessingEnvironment) processingEnv;
            Context context = environment.getContext();
            TreeMaker maker  = TreeMaker.instance(context);
            JavacElements elemUtils = (JavacElements) util.elementUtils;

            //create Filed
            JCTree.JCVariableDecl variableDecl = createField(Flags.PRIVATE, "java.lang.String", "good", maker, elemUtils, el);
            //create Annotation
            createAnnotation(variableDecl, "androidx.databinding.Bindable", maker, elemUtils, el);

            //클래스에서 field, method 리스트 가져오기
            List<? extends Element> list = el.getEnclosedElements();
            for (Element member : list) {
                util.note(member.getKind() + " " + member.getSimpleName());
                if(member.getKind().equals(ElementKind.FIELD)){ //필드일 경우
                    util.note("필드명 : "+member.getSimpleName());
                }else if(member.getKind().equals(ElementKind.METHOD)){ //메소드일 경우
                    //code 삽입
                    inputCode(maker, elemUtils, member);
                }
            }
        }
    }

    /**
     * field 넣기
     * @param modifierFlag
     * @param typeName
     * @param name
     * @param maker
     * @param elemUtils
     * @param el
     * @return
     */
    private JCTree.JCVariableDecl createField(int modifierFlag, String typeName, String name, TreeMaker maker, JavacElements elemUtils, TypeElement el){
        JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl)elemUtils.getTree(el);
        maker.pos = classDecl.pos;
        JCTree.JCModifiers modifier = maker.Modifiers(modifierFlag);
        Name fieldName = elemUtils.getName(name);
        JCTree.JCExpression type = makeSelectExpr(maker, typeName);

        JCTree.JCVariableDecl variableDecl = maker.VarDef(
                modifier,
                fieldName,
                type,
                null);

        classDecl.defs = classDecl.defs.appendList(com.sun.tools.javac.util.List.of(
                variableDecl
        ));
        return variableDecl;
    }


    /**
     * annotation 넣
     * @param variableDecl
     * @param annotationClassName
     * @param maker
     * @param elemUtils
     * @param el
     */
    private void createAnnotation(JCTree.JCVariableDecl variableDecl, String annotationClassName, TreeMaker maker, JavacElements elemUtils, TypeElement el){
        JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl)elemUtils.getTree(el);
        maker.pos = classDecl.pos;
        JCTree.JCAnnotation an = maker.Annotation(makeSelectExpr(maker, annotationClassName), nil());
        variableDecl.mods.annotations = variableDecl.mods.annotations.append(an);
    }

    /**
     * code 넣기
     * @param maker
     * @param elemUtils
     */
    private void inputCode(TreeMaker maker, JavacElements elemUtils, Element member) {
        JCTree tree = elemUtils.getTree(member);
        JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) tree;


        util.note("mDecl.name"+methodDecl.name);
        util.note("mDecl.pos"+methodDecl.pos);
        util.note("mDecl.getReturnType"+methodDecl.getReturnType());
        util.note("mDecl.mods"+methodDecl.mods);
        util.note("mDecl.getKind"+methodDecl.getKind());


        maker.pos = methodDecl.pos;
        com.sun.tools.javac.util.List<JCTree.JCExpression> nil = nil();
        Name system = elemUtils.getName("System");
        Name out = elemUtils.getName("out");
        Name _println = elemUtils.getName("println");
        JCTree.JCLiteral helloworld = maker.Literal("Hello world");

        methodDecl.body = maker.Block(0,com.sun.tools.javac.util.List.of(
                methodDecl.body,
                maker.Exec(
                        maker.Apply(nil,
                                maker.Select(maker.Select(maker.Ident(system),out),_println),
                                com.sun.tools.javac.util.List.<JCTree.JCExpression>of(helloworld)
                        )
                )
                )
        );
        util.note("mDecl.body"+methodDecl.body.toString());
    }


    private JCTree.JCExpression makeSelectExpr(TreeMaker maker, String select) {
        String[] parts = select.split("\\.");
        JCTree.JCExpression expression = maker.Ident(((JavacElements)util.elementUtils).getName(parts[0]));
        for (int i = 1; i < parts.length; i++) {
            expression = maker.Select(expression, ((JavacElements)util.elementUtils).getName(parts[i]));
        }
        return expression;
    }



//    private JCMethodDecl getWriteContentsMethod(JavacElements utils, JCExpression ioExceptionClass, JCModifiers methodModifiers, JCExpression objectOutputClass, JCBlock writeContentsBlock) {
//        return maker.MethodDef(
//                methodModifiers,
//                utils.getName("writeContents"),
//                maker.TypeIdent(TypeTag.VOID),
//                List.<JCTypeParameter>nil(),
//                List.of(
//                        maker.VarDef(maker.Modifiers(Flags.PARAMETER), utils.getName("out"), objectOutputClass, null),
//                        maker.VarDef(maker.Modifiers(Flags.PARAMETER), utils.getName("self"), makeSelectExpr(classDecl.sym.type.toString()), null)
//                ),
//                List.of(ioExceptionClass),
//                writeContentsBlock,
//                null
//        );
//    }





    public void generate() throws IOException {
//            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("create" + "OJW")
//                    .addModifiers(Modifier.PUBLIC)
//                    .addParameter(String.class, "type")
//                    .beginControlFlow("");
//
//            methodBuilder
//                        .addStatement("case $S: return new $T()", elementInfo.tag, elementInfo.className);
//
////            methodBuilder
////                    .endControlFlow()
////                    .addStatement("throw new RuntimeException(\"not support type\")")
////                    .returns(key);
//            MethodSpec methodSpec = methodBuilder.build();
//            TypeSpec helloWorld = TypeSpec.classBuilder(key.simpleName() + "Factory")
//                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                    .addMethod(methodSpec)
//                    .build();
//            JavaFile javaFile = JavaFile.builder(key.packageName(), helloWorld)
//                    .build();
//
//            javaFile.writeTo(filer);
    }

    private com.sun.tools.javac.util.List<JCTree.JCExpression> nil(){
        return com.sun.tools.javac.util.List.<JCTree.JCExpression>nil();
    }
}
