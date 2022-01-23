package frontend.servlet.tool;

import java.util.List;

public class ServletHelper {
    private String splitter;
    private String urlSuffix = null;

    public UrlResolveResult resolve(StringBuffer url) {
        int lastCharPosition = url.length() - 1;
        String strUrl = url.charAt(lastCharPosition) == '/' ? url.substring(0, lastCharPosition) : url.toString();
        String[] splitUrl = strUrl.split(splitter,2);
        if (splitUrl[1].isEmpty()) {
            return UrlResolveResult.EMPTY;
        } else {
            if (UrlResolveResult.NEW.toString().equalsIgnoreCase(splitUrl[1].split("/")[1])) {
                return UrlResolveResult.NEW;
            } else {
                urlSuffix = splitUrl[1].split("/", 3)[1];
                return UrlResolveResult.OVER;
            }
        }
    }

    public String toMyStrToJson(List list){
        String result = list.isEmpty() ? "{}" : "";
        for (Object user : list) {
            result = result.isEmpty() ? result + user.toString() : result + "," + user.toString();
        }
        return "["+result+"]";
    }

    public void setSplitter(String splitter) {
        this.splitter = splitter;
    }

    public String getUrlSuffix() {
        return urlSuffix;
    }

}
