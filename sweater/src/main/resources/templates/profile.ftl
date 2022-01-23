<#import "parts/common.ftl" as c>
  <@c.page>
    <h5>${username}</h5>
    ${message?ifExists}
    <form method="post">
      <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password :</label>
        <div class="col-sm-6">
          <input type="password" name="password" class="form-control" placeholder="password" />
        </div>
      </div>
      <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email :</label>
        <div class="col-sm-6">
          <input type="email" name="email" class="form-control" placeholder="${email!''}" />
        </div>
      </div>
      <!--  https://gist.github.com/drucoder/ecb7b4c077c58fe68c300c23a342237b -->
      <input type="hidden" name="_csrf" value="${_csrf.token}" />
      <!--<div><input type="submit" value="sign in" /></div>-->
      <button class="btn btn-primary" type="submit">Save</button>
    </form>
    <a href="https://temp-mail.org/">temp-mail</a>
  </@c.page>
