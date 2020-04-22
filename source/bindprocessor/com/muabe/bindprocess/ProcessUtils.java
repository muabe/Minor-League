package com.muabe.bindprocess;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

public class ProcessUtils {
    protected JavacProcessingEnvironment environment;
    protected Context context;
    protected TreeMaker maker;
    protected JavacElements elemUtils;
    protected Names names;



    public ProcessUtils(ProcessingEnvironment processingEnv){
        environment = (JavacProcessingEnvironment) processingEnv;
        context = environment.getContext();
        maker  = TreeMaker.instance(context);
        elemUtils = environment.getElementUtils();
        this.names = Names.instance(context);

    }

    public JavacProcessingEnvironment getEnvironment() {
        return environment;
    }

    public Context getContext() {
        return context;
    }

    public TreeMaker getMaker() {
        return maker;
    }

    public JavacElements getElemUtils() {
        return elemUtils;
    }


    protected JCTree.JCExpression makeSelectExpr(TreeMaker maker, String select) {
        String[] parts = select.split("\\.");
        JCTree.JCExpression expression = maker.Ident((getElemUtils()).getName(parts[0]));
        for (int i = 1; i < parts.length; i++) {
            expression = maker.Select(expression, (getElemUtils()).getName(parts[i]));
        }
        return expression;
    }

    protected com.sun.tools.javac.util.List<JCTree.JCExpression> nil(){
        return com.sun.tools.javac.util.List.<JCTree.JCExpression>nil();
    }

    protected <T>com.sun.tools.javac.util.List<T> nil(Class<T> t){
        return com.sun.tools.javac.util.List.<T>nil();
    }

    public void note(String message){
        environment.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }
}
