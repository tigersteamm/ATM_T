package uz.jl.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.jl.configs.AppConfig;
import uz.jl.models.atm.Atm;
import uz.jl.ui.AtmUI;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public final class FRWAtm extends FRWBase<Atm> {
    private static FRWAtm frwATM;
    public static FRWAtm getInstance() {
        if (Objects.isNull(frwATM)) {
            frwATM = new FRWAtm();
        }
        return frwATM;
    }


    public FRWAtm() {
        super(AppConfig.get("db.atms.path"));
    }

    @Override
    public List<Atm> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader("src/main/resources/db/atms.json");
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<Atm>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List<Atm> dataList) {
        try (FileWriter fileWriter = new FileWriter("src/main/resources/db/atms.json", false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonDATA = gson.toJson(dataList);
            bufferedWriter.write(jsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(Atm ATM) {
        List<Atm> list=getAll();
        list.add(ATM);
        writeAll(list);
    }
}
