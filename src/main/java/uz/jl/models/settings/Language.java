package uz.jl.models.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Elmurodov Javohir, Tue 12:18 PM. 12/7/2021
 */
@Getter
@AllArgsConstructor
public enum Language {
    UZ("UZ", "Uzbek"),
    RU("RU", "Russian"),
    EN("EN", "English");

    private final String name;
    private final String code;

    public static Language getByCode(String code) {
        for (Language language : values()) {
            if (language.getCode().equalsIgnoreCase(code)) {
                return language;
            }
        }
        return null;
    }
}
