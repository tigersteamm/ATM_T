package uz.jl.services.client;

import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.card.CardDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.dao.db.FRWCard;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.card.CardStatus;
import uz.jl.enums.card.CardType;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.exceptions.APIRuntimeException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.card.Card;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static uz.jl.utils.BaseUtils.genId;
import static uz.jl.utils.Color.PURPLE;
import static uz.jl.utils.Color.RED;

public class ClientService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {

    Role role = Session.getInstance().getUser().getRole();
    Language language = Session.getInstance().getUser().getLanguage();


    private static ClientService service;

    public static ClientService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new ClientService(repository, mapper);
        }
        return service;
    }

    public ClientService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> create(String userName, String password, String phoneNumber) {
        if (!(Role.EMPLOYEE.equals(role))) {
            return new ResponseEntity<>(LangConfig.get(language, "forbidden"), HttpStatus.HTTP_403);
        }
        AuthUser user = new AuthUser();
        user.setId(genId());
        user.setUsername(userName);
        user.setPassword(password);
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(Role.CLIENT);
        user.setPhoneNumber(phoneNumber);
        user.setCreatedBy(Session.getInstance().getUser().getId());
        user.setLanguage(Session.getInstance().getUser().getLanguage());
        user.setDeleted(0);
        return create(user);
    }

    @Override
    public ResponseEntity<String> create(AuthUser authUser) {
        if (repository.hasSuchName(authUser.getUsername())) {
            return new ResponseEntity<>(LangConfig.get(language, "already.exists"), HttpStatus.HTTP_406);
        }
        FRWAuthUser.getInstance().writeAll(authUser);
        return new ResponseEntity<>(LangConfig.get(language, "successfully.done"), HttpStatus.HTTP_200);
    }


    public ResponseEntity<String> delete(String userName) {
        AuthUser authUser;
        try {
            authUser = repository.findByUserName(userName);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return delete(authUser);
    }

    @Override
    public ResponseEntity<String> delete(AuthUser authUser) {
        if (authUser.getDeleted() == 1) {
            return new ResponseEntity<>(LangConfig.get(language, "already.done"), HttpStatus.HTTP_406);
        }

        authUser.setDeleted(1);
        FRWAuthUser.getInstance().writeAll(getAll());
        return new ResponseEntity<>(LangConfig.get(language, "successfully.done"), HttpStatus.HTTP_200);
    }

    public void list() {
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0) {
                if (authUser.getStatus().equals(UserStatus.ACTIVE) && authUser.getRole().equals(Role.CLIENT)) {
                    Print.println(PURPLE, authUser.getUsername());
                }
                if (authUser.getStatus().equals(UserStatus.BLOCKED) && authUser.getRole().equals(Role.CLIENT)) {
                    Print.println(RED, authUser.getUsername());
                }
            }
        }
    }

    @Override
    public AuthUser get(String id) {
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (id.equalsIgnoreCase(authUser.getId())) {
                return authUser;
            }
        }
        throw new APIRuntimeException("Client not found", HttpStatus.HTTP_404.getCode());
    }

    @Override
    public List<AuthUser> getAll() {
        return FRWAuthUser.getInstance().getAll();
    }

    public ResponseEntity<String> block(String userName) {
        try {
            AuthUser authUser = repository.findByUserName(userName);
            if (authUser.getDeleted() == 1) {
                throw new APIException("Client Not Found", HttpStatus.HTTP_404);
            }
            if (authUser.getStatus().equals(UserStatus.BLOCKED)) {
                return new ResponseEntity<>(LangConfig.get(language, "already.done"), HttpStatus.HTTP_406);
            }
            if (authUser.getStatus().equals(UserStatus.ACTIVE)) {
                authUser.setStatus(UserStatus.BLOCKED);
            }
            FRWAuthUser.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>(LangConfig.get(language, "successfully.done"), HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> unblock(String userName) {
        try {
            AuthUser authUser = repository.findByUserName(userName);
            if (authUser.getDeleted() == 1) {
                throw new APIException("Client Not Found", HttpStatus.HTTP_404);
            }
            if (authUser.getStatus().equals(UserStatus.ACTIVE)) {
                return new ResponseEntity<>(LangConfig.get(language, "already.done"), HttpStatus.HTTP_406);
            }
            if (authUser.getStatus().equals(UserStatus.BLOCKED)) {
                authUser.setStatus(UserStatus.ACTIVE);
            }
            FRWAuthUser.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>(LangConfig.get(language, "successfully.done"), HttpStatus.HTTP_200);
    }

    @Override
    public ResponseEntity<String> update(String id, AuthUser authUser) {
        return null;
    }

    public void blockList() {
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0 && authUser.getStatus().equals(UserStatus.BLOCKED))
                Print.println(Color.RED, authUser.getUsername());
        }
    }

    public Integer blockCount() {
        Integer count = 0;
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0 && authUser.getStatus().equals(UserStatus.BLOCKED))
                count++;
        }
        return count;
    }

    public ResponseEntity<String> giveCard(String holderUsername, String type, String password) {
//        if (!Role.EMPLOYEE.equals(role)) {
//            return new ResponseEntity<>(LangConfig.get(language, "forbidden"), HttpStatus.HTTP_403);
//        }
        AuthUser user;
        try {
            user = AuthUserDao.getInstance().findByUserName(holderUsername);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }

        CardType cardType = CardType.getByString(type);
        if (cardType.equals(CardType.UNDEFINED)) {
            return new ResponseEntity<>("Invalid card type", HttpStatus.HTTP_400);
        }

        if (!password.matches("^[0-9]{4}$")) {
            return new ResponseEntity<>("Invalid password", HttpStatus.HTTP_400);
        }

        Card card = Card.builder()
                .pan(generatePan(cardType))
                .expiry(getStringExpiry())
                .password(password)
                .type(cardType)
                .status(CardStatus.ACTIVE)
                .balance(BigDecimal.valueOf(0))
                .bankId(Session.getInstance().getUser().getBankId())
                .holderId(user.getId())
                .build();
        FRWCard.getInstance().writeAll(card);
        return new ResponseEntity<>(LangConfig.get(language, "successfully.done"), HttpStatus.HTTP_200);
    }

    private String getStringExpiry() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 3);
        Date expiry = cal.getTime();

        DateFormat dateFormat = new SimpleDateFormat("MMyy");
        return dateFormat.format(expiry);
    }

    private String generatePan(CardType type) {
        String generatedPan = "";
        do {
            generatedPan = switch (type) {
                case UZCARD -> "8600" + (new Random().nextLong(1000000000000L) + 100000000000L);
                case HUMO -> "9860" + (new Random().nextLong(1000000000000L) + 100000000000L);
                case MASTER_CARD -> "4853" + (new Random().nextLong(1000000000000L) + 100000000000L);
                case VISA -> "4735" + (new Random().nextLong(1000000000000L) + 100000000000L);
                case UNION_PAY -> "6262" + (new Random().nextLong(1000000000000L) + 100000000000L);
                case COBAGE -> "6330" + (new Random().nextLong(1000000000000L) + 100000000000L);
                default -> "-1";
            };
        } while (CardDao.getInstance().hasSuchPan(generatedPan));
        return generatedPan;
    }

//    private CardType getCardType(String pan) {
//        if (pan.matches("^(8600)[0-9]{12}$")) {
//            return CardType.UZCARD;
//        } else if (pan.matches("^(9860)[0-9]{12}$")) {
//            return CardType.HUMO;
//        } else if (pan.matches("^(4853)[0-9]{12}$")) {
//            return CardType.MASTER_CARD;
//        } else if (pan.matches("^4[0-9]{12}(?:[0-9]{3})?$")) {
//            return CardType.VISA;
//        } else if (pan.matches("^(6330)[0-9]{12}$")) {
//            return CardType.COBAGE;
//        } else if (pan.matches("^(6262)[0-9]{12}$")) {
//            return CardType.UNION_PAY;
//        } else {
//            return CardType.UNDEFINED;
//        }
//    }
}
