<!DOCTYPE html>
<html>
    <head>
        <title>BUYSELL</title>
    </head>
    <body>
        <h1>BUYSELL | <a href="/">Home</a></h1><hr>


        <h4>Панель администратора</h4>
        <table border="1">
            <tr>
                <th>email</th>
                <th>Номер телефона:</th>
                <th>Активность</th>
                <th>Роли</th>
                <th>Бан</th>
                <th>редактировние</th>
                <th>подробная информация</th>
            </tr>
            <#list users as user>
                <tr>
                    <th>${user.email}</th>
                    <th>${user.phoneNumber}</th>
                    <th><#if user.active>true<#else>false</#if></th>
                    <th><#list user.roles as role>${role}</#list></th>
                    <th>
                        <form action="/admin/user/ban/${user.id}" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <input type="submit" value="Ban">
                        </form>
                    </th>
                    <th><a href="/admin/user/edit/${user.id}">редактировать</a></th>
                    <th><a href="/user/${user.id}">подробная информация</a></th>
                </tr>
            <#else>
                <h3>Ползователей нет</h3>
            </#list>
        </table>
    </body>
</html>
