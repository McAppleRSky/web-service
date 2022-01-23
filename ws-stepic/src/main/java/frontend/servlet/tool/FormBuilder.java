package frontend.servlet.tool;

public class FormBuilder {
    private String template = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
            + "</head><body><div style=\"visibility: visible; overflow: visible; position: absolute; left: 100px; top: 100px;\">"
            + "<form action=\"" + "%s"
            + "\" method=\"" + "%s"
            + "\"><label for=\"login\">user name:</label>"
            + "<input type=\"text\" name=\"login\">"
            + "<label for=\"password\">user pass:</label>"
            + "<input type=\"text\" name=\"password\">"
            + "<input type=\"submit\" value=\"" + "%s"
            + "\"></form>"
            + "</div></body></html>";

    private String path;

    private String method;

    private String value;

    {
        this.method = HttpMethod.GET.toString();
    }

    public FormBuilder() {
    }

    public FormBuilder(String path) {
        this.path = path;
    }

    public FormBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public FormBuilder setPostMethod() {
        this.method = HttpMethod.POST.toString();
        return this;
    }

    public FormBuilder setMethod(HttpMethod method) {
        this.method = method.toString();
        return this;
    }

    public FormBuilder setSubmitButtonValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                template, path, method, value );
    }

}
