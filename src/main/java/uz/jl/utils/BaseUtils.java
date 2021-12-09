package uz.jl.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Elmurodov Javohir, Mon 12:13 PM. 12/6/2021
 */
public class BaseUtils {
    public static String genId() {
        return System.nanoTime() + RandomStringUtils.random(20, true, true);
    }
}
