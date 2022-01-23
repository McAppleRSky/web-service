<#include "security.ftl">
<#import "login.ftl" as l>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="/">Sweater</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
        <!--    <a class="navbar-brand" href="#">Hidden brand</a>-->
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="/">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/main">message</a>
          </li>
          <#if isAdmin>
            <li class="nav-item">
              <a class="nav-link" href="/user">user list</a>
            </li>
          </#if>
          <#if user??>
            <li class="nav-item">
              <a class="nav-link" href="/user/profile">profile</a>
            </li>
          </#if>

        </ul>
        <div class="navbar-text mr-3">${name}</div>
        <@l.logout />
      </div>
    </nav>
