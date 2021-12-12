package uz.jl.configs;

import uz.jl.models.settings.Language;

/**
 * @author D4uranbek вс. 13:13. 12.12.2021
 */
public class LangConfig {
    public static String get(Language language, String key) {
        return AppConfig.getLang(language).getProperty(key);
    }
}