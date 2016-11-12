package com.weirddev.testme.intellij.template;

import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 24/10/2016
 * @author Yaron Yamin
 */
public class Type {
    private final String canonicalName;
    private final String name;
    private final boolean isPrimitive;
    private final String packageName;
    private final List<Type> composedTypes;
    private final boolean array;

    Type(String canonicalName, String name, String packageName, boolean isPrimitive, boolean array, List<Type> composedTypes) {
        this.canonicalName = canonicalName;
        this.name = name;
        this.isPrimitive = isPrimitive;
        this.packageName = packageName;
        this.array = array;
        this.composedTypes = composedTypes;
    }

    Type(String canonicalName) {
        this(extractContainerType(canonicalName), extractClassName(canonicalName),extractPackageName(canonicalName),false,isArray(canonicalName),null);
    }

    public Type(PsiType psiType) {
        String canonicalText = psiType.getCanonicalText();
        array = isArray(canonicalText);
        this.canonicalName = stripArrayDesignator(canonicalText);
        this.name = stripArrayDesignator(psiType.getPresentableText());
        packageName = extractPackageName(canonicalName);
        this.isPrimitive = psiType instanceof PsiPrimitiveType;
        composedTypes = resolveTypes(psiType);
    }

    private static  boolean isArray(String canonicalName) {
        return canonicalName.endsWith("[]");
    }

    public static String extractClassName(@NotNull String fqName) {
        fqName = extractContainerType(fqName);
        int i = fqName.lastIndexOf('.');
        return stripArrayDesignator((i == -1 ? fqName : fqName.substring(i + 1)));
    }

    public static String extractPackageName(String className) {
        if (className != null) {
            className = extractContainerType(className);
            int i = className.lastIndexOf('.');
            return stripArrayDesignator(i == -1 ? "" : className.substring(0, i));
        }
        return null;
    }

    private static String stripArrayDesignator(String typeName) {
        return typeName.replace("[]", "");
    }

    @NotNull
    private static String extractContainerType(String className) {
        int j = className.indexOf('<');
        className=j==-1?className:className.substring(0, j);
        return stripArrayDesignator(className);
    }

    private List<Type> resolveTypes(PsiType psiType) {
        ArrayList<Type> types = new ArrayList<Type>();
        resolveTypes(psiType, types);
        return types;
    }

    private void resolveTypes(PsiType psiType, ArrayList<Type> types) {
        types.add(new Type(psiType.getCanonicalText()));
        if (psiType instanceof PsiClassReferenceType) {
            PsiClassReferenceType psiClassReferenceType = (PsiClassReferenceType) psiType;
            PsiType[] parameters = psiClassReferenceType.getParameters();
            if (parameters.length > 0) {
                for (PsiType parameter : parameters) {
                    resolveTypes(parameter, types);
                }
            }
        }
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public String getName() {
        return name;
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<Type> getComposedTypes() {
        return composedTypes;
    }

    public boolean isArray() {
        return array;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        if (isPrimitive != type.isPrimitive) return false;
        if (array != type.array) return false;
        if (canonicalName != null ? !canonicalName.equals(type.canonicalName) : type.canonicalName != null)
            return false;
        if (name != null ? !name.equals(type.name) : type.name != null) return false;
        if (packageName != null ? !packageName.equals(type.packageName) : type.packageName != null) return false;
        return composedTypes != null ? composedTypes.equals(type.composedTypes) : type.composedTypes == null;

    }

    @Override
    public int hashCode() {
        int result = canonicalName != null ? canonicalName.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isPrimitive ? 1 : 0);
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (composedTypes != null ? composedTypes.hashCode() : 0);
        result = 31 * result + (array ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Type{" +
                "canonicalName='" + canonicalName + '\'' +
                ", name='" + name + '\'' +
                ", isPrimitive=" + isPrimitive +
                ", packageName='" + packageName + '\'' +
                ", composedTypes=" + composedTypes +
                ", array=" + array +
                '}';
    }
}