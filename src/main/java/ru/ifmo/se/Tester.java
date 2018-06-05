package ru.ifmo.se;

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

public class Tester {
    String pack;
    Set<Class> classes;
    Constructor[] constructors;
    Method[] methods;

    private static Logger logger = Logger.getLogger(Tester.class.getName());

    public Tester(String pack){
        logger.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
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
    }

    private void testMethods(Set<Class> classes){
        Class<?>[] paramtypes;
        Object[] params;
        Class<?>[] conparamtypes;
        Object[] conparams;
        for (Class<?> clazz : classes){
            methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                paramtypes = method.getParameterTypes();
                params = this.createParameters(paramtypes);
                try {
                    method.invoke(clazz.newInstance(), params);
                    logger.fine("Method " + method.toString() + " invoking finished without exceptions.");
                } catch (InstantiationException e1) {
                    constructors = clazz.getDeclaredConstructors();
                    for (Constructor<?> constructor : constructors) {
                        conparamtypes = constructor.getParameterTypes();
                        conparams = this.createParameters(conparamtypes);
                        try {
                            method.invoke(constructor.newInstance(conparams), params);
                            logger.fine("Method " + method.toString() + " invoking finished without exceptions.");
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            logger.info("Constructor " + constructor.toString() + ":\n" + e.toString());
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.out.println(e.toString());
                    logger.info("Method " + method.toString() + ":\n" + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    private void testConstructors(Set<Class> classes){
        Class<?>[] paramtypes;
        Object[] params;
        for (Class<?> clazz : classes){
            constructors = clazz.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors){
                paramtypes = constructor.getParameterTypes();
                params = this.createParameters(paramtypes);
                try{
                    constructor.newInstance(params);
                    logger.fine("Constructor " + constructor.toString() + " invoking finished without exceptions.");
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
                    logger.info("Constructor " + constructor.toString() + ":\n" + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    private Object[] createParameters(Class<?>[] paramtypes){
        int paramcount = paramtypes.length;
        Object[] array = new Object[paramcount];
        for (int i = 0; i < paramcount; i++){
            if (paramtypes[i].equals(String.class))
                array[i]="";
            else
            if (paramtypes[i].equals(boolean.class))
                array[i]=false;
            else
            if (paramtypes[i].equals(int.class) || paramtypes[i].equals(float.class)
                    || paramtypes[i].equals(byte.class) || paramtypes[i].equals(long.class) || paramtypes[i].equals(short.class)
                    || paramtypes[i].equals(double.class))
                array[i]=0;
            else
                array[i]=null;
        }
        return array;
    }

}
