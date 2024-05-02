<!DOCTYPE html>
<html lang="en">
    <head>
        <title>BUYSELL</title>
    </head>

    <body>
        <h1>BUYSELL</h1>
        <h4>Редактирование пользователя</h4>
        <form method="post" action="/admin/user/edit">
            <#list roles as role>
                <div>
                    <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                </div>
            </#list>
            <input type="hidden" name="userId" value="${user.id}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit">Сохранить</button>
        </form>
    </body>
</html>