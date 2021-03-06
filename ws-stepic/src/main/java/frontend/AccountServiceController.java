package frontend;

import base.AccountService;
import frontend.AccountServiceControllerMBean;

public class AccountServiceController implements AccountServiceControllerMBean {

    private final AccountService accountService;

    public AccountServiceController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public int getUsers() {
        return accountService.getUsersCount();
    }

    @Override
    public int getUsersLimit() {
        return accountService.getUsersLimit();
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        accountService.setUsersLimit(usersLimit);
    }

}
