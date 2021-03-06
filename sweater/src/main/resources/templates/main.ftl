<#import "parts/common.ftl" as c>
  <!--  <#import "parts/login.ftl" as l>-->

  <@c.page>
    <div class="form-row">
      <div class="form-group col-md-6">
        <form class="form-inline" method="get" action="/main">
          <input class="form-control" type="text" name="filter" value="${filter?ifExists}" placeholder="Search by tag">
          <!-- <input type="hidden" name="_csrf" value="${_csrf.token}" /> -->
          <button class="btn btn-primary ml-2" type="submit">search</button>
        </form>
      </div>
    </div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
      Add new message
    </a>
    <div class="collapse" id="collapseExample">
      <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
          <div class="form-group">
            <input class="form-control ${(textError??)?string('is-invalid','')}" value="<#if message??>${message.text}</#if>" type="text" name="text" placeholder="Input message">
            <#if textError??>
              <div class="invalid-feedback">
                ${textError}
              </div>
            </#if>
          </div>
          <div class="form-group">
            <input class="form-control" type="text" name="tag" placeholder="Input tag">
          </div>
          <div class="form-group">
            <div class="custom-file">
              <input type="file" name="file" id="customFile">
              <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
          </div>
          <input class="form-control" type="hidden" name="_csrf" value="${_csrf.token}" />
          <div class="form-group">
            <button class="btn btn-primary ml-2">Add</button>
          </div>
        </form>
      </div>
    </div>
    <div class="card-columns">
      <#list messages as message>
        <div class="card my-3">
          <!--          <b>${message.id}</b>-->
          <div class="m-2">
            <span>${message.text}</span>
            <i>${message.tag}</i>
          </div>
          <div class="card-footer text-muted">
            ${message.authorName}
          </div>
          <div>
            <#if message.filename??>
              <img class="card-img-top" src="/img/${message.filename}">
            </#if>
          </div>
        </div>
      <#else>
          No message
      </#list>
    </div>
  </@c.page>
