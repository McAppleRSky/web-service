package frontend.servlet;

import frontend.servlet.tool.ScriptSelector;
import frontend.servlet.tool.ScriptStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;

class ScriptSelectorTest {

    private final String[] DO_POST_CREATE_EXCLUSIONS = {"new"};
    private ScriptSelector scriptSelector;

    @BeforeEach
    void init(){
        scriptSelector = new ScriptSelector();
    }

    @Test
    void servletUserDoPostPositive() {
        String  tstStrLogin = randomAlphabetic(4)
                ,tstStrPassword = randomAlphabetic(8);
        ScriptStatus expected = ScriptStatus.SCRIPT_POSITIVE
                ,actual = scriptSelector.servletUserDoPost(
                        tstStrLogin, tstStrPassword, DO_POST_CREATE_EXCLUSIONS
                );
        assertEquals(expected, actual);
    }

    @Test
    void servletUserDoPostScript0_empty() {
        String  tstStrLogin = ""
                ,tstStrPassword = "";
        ScriptStatus expected = ScriptStatus.SCRIPT_0
                ,actual = scriptSelector.servletUserDoPost(
                        tstStrLogin, tstStrPassword, DO_POST_CREATE_EXCLUSIONS
                );
        assertEquals(expected, actual);
    }

    @Test
    void servletUserDoPostScript0_empty_half() {
        String  tstStrLogin = randomAlphabetic(4)
                ,tstStrPassword = "";
        ScriptStatus expected = ScriptStatus.SCRIPT_0
                ,actual = scriptSelector.servletUserDoPost(
                        tstStrLogin, tstStrPassword, DO_POST_CREATE_EXCLUSIONS
                );
        assertEquals(expected, actual);
    }

    @Test
    void servletUserDoPostScript1_null() {
        String  tstStrLogin = null
                ,tstStrPassword = null;
        ScriptStatus expected = ScriptStatus.SCRIPT_1
                ,actual = scriptSelector.servletUserDoPost(
                        tstStrLogin, tstStrPassword, DO_POST_CREATE_EXCLUSIONS
        );
        assertEquals(expected, actual);
    }

    @Test
    void servletUserDoPostScript1_null_half() {
        String  tstStrLogin = ""
                ,tstStrPassword = null;
        ScriptStatus expected = ScriptStatus.SCRIPT_1
                ,actual = scriptSelector.servletUserDoPost(
                        tstStrLogin, tstStrPassword, DO_POST_CREATE_EXCLUSIONS
                );
        assertEquals(expected, actual);
    }

}
