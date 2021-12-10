package uz.jl.enums.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static uz.jl.utils.Color.BLUE;
import static uz.jl.utils.Print.print;
import static uz.jl.utils.Print.println;

/**
 * @author Elmurodov Javohir, Mon 12:12 PM. 11/29/2021
 */
@Getter
@AllArgsConstructor
public enum ATMType {
    UZCARD("des"),
    HUMO("des"),
    VISA("des"),
    VISA_UZCARD("des"),
    VISA_HUMO("des");
    private String description;
    public static void show(){
        for (ATMType value : ATMType.values()) {
            if(!value.equals(VISA_HUMO))
                print(BLUE,value+" or ");
            else
                print(BLUE,value);
        }
        println("");
    }
    public static ATMType getByValue(String type){
        for (ATMType value : values()) {
            if(value.name().equals(type))
                return value;
        }
        return null;
    }
}
