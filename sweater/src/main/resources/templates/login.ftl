<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

    <@c.page>
      <#if Session?? && Session.SPRING_SEQURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
          ${Session.SPRING_SEQURITY_LAST_EXCEPTION.message}
        </div>
      </#if>
      <#if message??>
        <div class="alert alert-${messageType}" role="alert">
          ${message}
        </div>
      </#if>
      ${message?ifExists}
      <!-- Login page -->
      <@l.login "/login" false />
      <!-- <a href="/signup">Add new user</a> -->
    </@c.page>
