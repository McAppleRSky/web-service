<#macro login path isRegisterForm>
  <form action="${path}" method="post">
    <div class="form-group row">
      <label class="col-sm-2 col-form-label">User Name :</label>
      <div class="col-sm-6">
        <input type="text" name="username" class="form-control" placeholder="user name" />
      </div>
    </div>
    <div class="form-group row">
      <label class="col-sm-2 col-form-label">Password :</label>
      <div class="col-sm-6">
        <input type="password" name="password" class="form-control" placeholder="password" />
      </div>
    </div>
    <#if isRegisterForm>
      <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email :</label>
        <div class="col-sm-6">
          <input type="email" name="email" class="form-control" placeholder="anyuser@samedomain.ru" />
        </div>
        <div class="col-sm-6">
          <div class="g-recaptcha" data-sitekey="6LcN2Z4cAAAAADH4-Wh9sCY3GlXrtzUeQLIekNuV"></div>
          <#if captchaError??>
            <div class="alert alert-${messageType}" role="alert">
              ${captchaError}
            </div>
          </#if>
        </div>
    </#if>

    <!--  https://gist.github.com/drucoder/ecb7b4c077c58fe68c300c23a342237b -->
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <!--<div><input type="submit" value="sign in" /></div>-->
    <div class="form-group row">
      <span class="col-sm-2 col-form-label">
        <button class="btn btn-primary" type="submit">
          <#if isRegisterForm>create<#else>sign in</#if>
        </button>
      </span>
      <div class="col-sm-6">
        <#if !isRegisterForm>
          <a href="/signup">Add new user</a>
        </#if>
      </div>
    </div>
  </form>
</#macro>

<#macro logout>
  <form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">sign out</button>
    <!--    <input type="submit" value="Sign out" />-->
  </form>
</#macro>
