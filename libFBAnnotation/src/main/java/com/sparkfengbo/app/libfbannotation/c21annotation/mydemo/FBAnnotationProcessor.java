package com.sparkfengbo.app.libfbannotation.c21annotation.mydemo;

import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by fengbo on 2017/12/19.
 */

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class FBAnnotationProcessor extends AbstractProcessor {

    private static final String TAG = "FBAnnotationProcessor";
    private Elements elesUtils;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elesUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        StringBuilder sb = new StringBuilder();
        for(Element e : roundEnvironment.getRootElements()) {
            System.console().printf("getRootElements includes : " + e.getSimpleName());
        }

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(FBJsonCast.class);

        //关系： 类名 ： 成员Map
        //成员map ： 成员名 ： 值
        Map<String, Map<String, Object>> values = new LinkedHashMap<>();
        for (Element e : elements) {
            if (e.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) e;
                values.put(typeElement.getQualifiedName().toString(), new LinkedHashMap<String, Object>());
            } else if (e.getKind() == ElementKind.FIELD) {
                VariableElement ve = (VariableElement) e;
                TypeElement type = (TypeElement) ve.getEnclosingElement();
                Map<String, Object> fieldsValues = values.get(type.getQualifiedName().toString());
                if(fieldsValues != null) {
                    fieldsValues.put(ve.getSimpleName().toString(), ve.getConstantValue());
                }
            }
        }

        for(Map.Entry<String, Map<String, Object>> entry1 : values.entrySet()) {
            sb.append("class " + entry1.getKey() + "{ \n");
            for(Map.Entry<String, Object> entry2 : entry1.getValue().entrySet()) {
                sb.append("\"" + entry2.getKey() + "\" : " + entry2.getValue() + ";\n");
            }
            sb.append("}\n");
        }

        return false;
    }

    private void generateJsonString() {

    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(FBJsonCast.class.getName());
        annotations.add(FBFloatRange.class.getName());
        return super.getSupportedAnnotationTypes();
    }

}
