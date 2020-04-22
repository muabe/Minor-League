package com.muabe.bindprocess;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class ProcessorUtils {
    public Elements elementUtils;
    ProcessingEnvironment env;
    public Filer filer;

    ProcessorUtils(ProcessingEnvironment processingEnvironment){
        this.env = processingEnvironment;
        elementUtils = processingEnvironment.getElementUtils();
        this.filer = processingEnvironment.getFiler();

    }

    public void note(String message){
        env.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }

}
