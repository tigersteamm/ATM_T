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

    private final String code;
    private final String name;

    public static Language getByCode(String code) {
        for (Language language : values()) {
            if (language.getCode().equalsIgnoreCase(code)) {
                return language;
            }
        }
        return null;
    }

    public static void showAll() {
        for (Language language : values()) {
            System.out.println(language);
        }
    }

    @Override
    public String toString() {
        return name + " >> " + code;
    }
}
