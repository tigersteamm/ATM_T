package uz.jl.ui;

import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.dao.atm.AtmDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.mapper.ATMMapper;
import uz.jl.mapper.BranchMapper;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.atm.AtmService;
import uz.jl.services.branch.BranchService;
import uz.jl.utils.Input;

import static uz.jl.ui.BaseUI.showResponse;

/**
 * @author D4uranbek вс. 19:19. 12.12.2021
 */
public class BirnimaUI {
    static AtmService service = AtmService.getInstance(AtmDao.getInstance(), ATMMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();

    public void cashWithdrawal() {
        String amount = Input.getStr(LangConfig.get(language, "amount") + " >> ");
        ResponseEntity<String> response = service.cashWithdrawal(amount);
        showResponse(response);
    }
}
