package uz.jl.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.jl.configs.AppConfig;
import uz.jl.models.atm.ATMEntity;
import uz.jl.ui.AtmUI;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public final class FRWATMEntity extends FRWBase<ATMEntity> {
    private static FRWATMEntity frwATM;
    public static FRWATMEntity getInstance() {
        if (Objects.isNull(frwATM)) {
            frwATM = new FRWATMEntity();
        }
        return frwATM;
    }


    public FRWATMEntity() {
        super(AppConfig.get("db.atms.path"));
    }

    @Override
    public List<ATMEntity> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader("src/main/resources/db/atms.json");
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<AtmUI>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List<ATMEntity> dataList) {
        try (FileWriter fileWriter = new FileWriter("src/main/resources/db/atms.json", true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonDATA = gson.toJson(dataList);
            bufferedWriter.write(jsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(ATMEntity ATM) {
        List<ATMEntity> list=getAll();
        list.add(ATM);
        writeAll(list);
    }
}
