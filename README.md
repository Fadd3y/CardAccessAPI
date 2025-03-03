# Менеджер доступа сотрудников
Приложения для предоставления доступа по ключ картам сотрудникам, в соответствии с их должностью и правами.

## Функционал
- Обработка запроса и принятие решения о разрешении и запрете доступа
- Веб интерфейс для создания, редактирования и удаления сотрудников, должностей и прав доступа
- Интерфейс соответсвующий роли в системе (администратор/пользователь)
- Запись истории получения доступа сотрудников с указанием места

## Как запустить
1. Собрать проект в maven
2. Создать базу данных с помощью файла card_access_db_init.sql
3. Подключить базу в application.properties
4. Перейти на http://localhost:8080/homepage
5. Зайти в учетную запись (username: admin, password: admin)

Пример запроса к приложению:
```
{
    "personId": 1,
    "authority": "Проходная"
}
```

Пример ответа:
```
{
    "accessType": "ACCEPTED",
    "message": "Access granted"
}
```
Пример ответа на некорректный запрос:
```
{
    "accessType": "DENIED",
    "message": "field: personId, error_message: 'personId' field is empty; field: personId, error_message: Person not found; field: authority, error_message: Authority not found; "
}
```
