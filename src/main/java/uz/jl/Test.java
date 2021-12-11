package uz.jl;

import uz.jl.configs.AppConfig;
import uz.jl.exceptions.APIException;
import uz.jl.ui.ClientUI;

/**
 * @author D4uranbek сб. 18:54. 11.12.2021
 */
public class Test {
    static {
        try {
            AppConfig.init();
        } catch (APIException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String s = "4735293296269604";
        System.out.println(s.length());
        //ClientUI.giveCard();
    }
}
