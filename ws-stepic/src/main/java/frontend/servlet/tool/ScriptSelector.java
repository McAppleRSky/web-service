package frontend.servlet.tool;

public class ScriptSelector {

    public ScriptStatus servletUserDoPost(String login, String password, String[] loginExclusions) {
        ScriptStatus result;
        if (login !=null && password !=null) {
            if (login.trim().length()>0 && password.trim().length()>0) {
                for (String loginExclusion : loginExclusions) {
                    if (loginExclusion.equalsIgnoreCase(login)) {
                        return ScriptStatus.SCRIPT_2;
                    }
                }
                result = ScriptStatus.SCRIPT_POSITIVE;
            } else {
                result = ScriptStatus.SCRIPT_0;
            }
        } else {
            result = ScriptStatus.SCRIPT_1;
        }
        return result;
    }
}
