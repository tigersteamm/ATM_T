package uz.jl.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.jl.configs.AppConfig;
import uz.jl.models.branch.Branch;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Elmurodov Javohir, Thu 9:32 AM. 12/9/2021
 */
public final class FRWBranch extends FRWBase<Branch> {

    private static FRWBranch frwBranch;

    public static FRWBranch getInstance() {
        if (Objects.isNull(frwBranch)) {
            frwBranch = new FRWBranch();
        }
        return frwBranch;
    }


    public FRWBranch() {
        super(AppConfig.get("db.branches.path"));
    }

    @Override
    public List<Branch> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<Branch>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List<Branch> dataList) {
        try (FileWriter fileWriter = new FileWriter(path, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonDATA = gson.toJson(dataList);
            bufferedWriter.write(jsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(Branch branch) {
        List<Branch> all = getAll();
        all.add(branch);
        writeAll(all);
    }
}
