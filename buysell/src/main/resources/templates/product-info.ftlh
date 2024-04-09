<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
    <h1>BUYSELL | <a href="/">Home</a></h1>
    <hr>
    <h4>Подробная информация о товаре</h4>
    <b>Название товара: </b> ${product.title}<br>
    <b>Описание товара: </b> ${product.description}<br>
    <b>Цена: </b> ${product.price}<br>
    <b>Город: </b> ${product.city}<br>
    <b>Автор : </b> ${product.author}<br>
    <b>Название товара: </b> ${product.title}<br>
    <hr>
    <form action="/product/delete/${product.id}" method="post">
        <input type="submit" value="Удалить товар"/>
    </form>
</body>
</html>