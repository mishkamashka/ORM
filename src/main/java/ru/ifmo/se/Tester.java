package ru.ifmo.se;

import org.reflections.Reflections;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Tester {
    String pack;
    //Set<Class<?>> classes;
    Set<Class> classes;
    Constructor[] constructors;
    Method[] methods;

    private static Logger logger = Logger.getLogger(Tester.class.getName());

    public Tester(String pack){
        this.pack = pack;
    }

    public void run(){
        classes = this.getClasses(pack);
        this.testConstructors(classes);
        this.testMethods(classes);
    }

    private Set<Class> getClasses(String pack){
        Set<Class> set = new HashSet<>();
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
        final Set<BeanDefinition> classes = provider.findCandidateComponents(pack);
        for (BeanDefinition bean: classes) {
            try {
                Class<?> clazz = Class.forName(bean.getBeanClassName());
                set.add(clazz);
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return set;
//        Reflections reflections = new Reflections(pack);
//        return reflections.getSubTypesOf(Object.class);
    }

    private void testMethods(Set<Class> classes){
        Class<?>[] paramtypes;
        ArrayList<? super Object> params;
        for (Class<?> clazz : classes){
            methods = clazz.getDeclaredMethods();
            for (Method method : methods){
                paramtypes = method.getParameterTypes();
                params = this.createParameters(paramtypes);
                try{
                    method.invoke(clazz.newInstance(), params.toArray());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
                    System.out.println(e.toString());
                    logger.info(e.toString());
                }
            }
        }
    }

    private void testConstructors(Set<Class> classes){
        Class<?>[] paramtypes;
        ArrayList<? super Object> params;
        for (Class<?> clazz : classes){
            constructors = clazz.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors){
                paramtypes = constructor.getParameterTypes();
                params = this.createParameters(paramtypes);
                try{
                    constructor.newInstance(params);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
                    logger.info(e.toString());
                }
            }
        }
    }

    private ArrayList<? super Object> createParameters(Class<?>[] paramtypes){
        int paramcount = paramtypes.length;
        ArrayList<? super Object> params = new ArrayList<>();
        for (int i = 0; i < paramcount; i++){
            if (paramtypes[i].equals(String.class))
                params.add(i,"");
            else
            if (paramtypes[i].equals(boolean.class))
                params.add(i,false);
            else
            if (paramtypes[i].getSuperclass().equals(Number.class) || paramtypes[i].equals(int.class) || paramtypes[i].equals(float.class)
                    || paramtypes[i].equals(byte.class) || paramtypes[i].equals(long.class) || paramtypes[i].equals(short.class)
                    || paramtypes[i].equals(double.class))
                params.add(i,0);
            else
                params.add(i,null);
        }
        return params;
    }

}
