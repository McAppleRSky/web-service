package frontend.servlet.user;

import base.AccountService;
import frontend.servlet.ServletUser;
import frontend.servlet.tool.ScriptSelector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ServletUserInitTest extends ServletUser {

    public ServletUserInitTest() {
        super(mock(AccountService.class), mock(ScriptSelector.class));
    }

    @Test
    void InitTest() {
        assertEquals("/user", PAGE_URL);
        assertEquals("/api/latest/user", WS_PAGE_URL);
        assertEquals(1, DO_POST_CREATE_EXCLUSIONS.length);
    }

}
