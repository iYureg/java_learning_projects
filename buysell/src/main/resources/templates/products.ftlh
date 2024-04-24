<!DOCTYPE html>
<html>
    <head>
        <title>BUYSELL</title>
    </head>
    <body>
        <h1>BUYSELL</h1><hr>
        <#if user.email??>
            <h3>Имя пользователя: <i>${user.name}</i></h3>
            <form method="post" action="/logout">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Выйти">
            </form>
        <#else>
            <a href="/login">Войти</a>
        </#if>
        <hr>
        <h4>Все товары</h4>
        <form>
            Поиск по названию обявления: <input type="text" name="title" value="Название товара"><br>
            <input type="submit" value="Поиск">
        </form>
        <#list products as product>

            <div>
                <p><b>${product.title}</b> : ${product.price} руб. <a href="/product/${product.id}">подробнее...</a></p>
            </div>

        <#else>
            <h3>Товаров нет</<h3>
        </#list>
        <hr>
        <#if user.email??>
            <h3>Создать новый товар</h3>
            <form action="/product/create" method="post" enctype="multipart/form-data">
                Название обявления: <input type="text" name="title"/><br><br>
                Описание обявления: <input type="text" name="description"/><br><br>
                Цена: <input type="number" name="price"/><br><br>
                Город: <input type="text" name="city"/><br><br>
                Первая фотография: <input type="file" name="file1"/><br><br>
                Вторая фотография: <input type="file" name="file2"/><br><br>
                Третья фотография: <input type="file" name="file3"/><br><br>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Добавить товар"/>
            </form>
        </#if>
    </body>
</html>
