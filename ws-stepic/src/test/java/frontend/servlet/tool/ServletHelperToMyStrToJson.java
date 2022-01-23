package frontend.servlet.tool;

import base.UserProfile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServletHelperToMyStrToJson {

    @Test
    void ServletHelperPositiveUnoUserTest() {
        String   tstStrLogin = randomAlphabetic(2)
                ,tstStrPassword = randomAlphabetic(3)
                ;
        List<UserProfile> userProfilesUno = Collections.singletonList(
                new UserProfile(tstStrLogin, tstStrPassword)
        );
        ServletHelper servletHelper = new ServletHelper();
        String expected = "[{\"login\":\"" + tstStrLogin
                + "\",\"password\":\"" + tstStrPassword
                + "\"}]"
                ,actual = servletHelper.toMyStrToJson(userProfilesUno)
                ;
        assertEquals(expected, actual);
    }

    @Test
    void ServletHelperPositiveDuoUserTest() {
        String   tstStrLogin = randomAlphabetic(2)
                ,tstStrPassword = randomAlphabetic(3)
                ,tstStrLogin1 = randomAlphabetic(2)
                ,tstStrPassword1 = randomAlphabetic(3)
                ;
        List<UserProfile> userProfilesDuo = new ArrayList<>();
        userProfilesDuo.add(new UserProfile(tstStrLogin, tstStrPassword));
        userProfilesDuo.add(new UserProfile(tstStrLogin1, tstStrPassword1));
        ServletHelper servletHelper = new ServletHelper();
        String expected = "[{\"login\":\"" + tstStrLogin
                + "\",\"password\":\"" + tstStrPassword
                + "\"},{\"login\":\"" + tstStrLogin1
                + "\",\"password\":\"" + tstStrPassword1
                + "\"}]"
                ,actual = servletHelper.toMyStrToJson(userProfilesDuo);
        assertEquals(expected, actual);
    }

}
