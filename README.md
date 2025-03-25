Программа для ведения личных трат, Restful Api - удобный инструмент для аналитики куда уходят деньги. Например, позволяет видеть распределение трат по категориям.

Тз:

Пример: Рестораны: 5811, 5812, 5813 Фастфуд: 5814 Супермаркеты: 5297, 5298

Еда: Рестораны, Фастфуд, Супермаркеты Развлечения: 7911, 7922, Рестораны

Обозначения - обязательное поле [xyz] - опциональное поле mcc - 4 цифры - код операции category - текстовое поле

Доступные команды:

Добавить категорию трат add category [mcc2] [mcc3] ... ответ: created new category "name" (list of mcc) ответ: mcc already reserved for category "another name" - если такой mcc код уже в другой категории

Изменить категорию трат (если с таким именем уже есть) add mcc to category [mcc2] [mcc3] ... ответ: added new mcc to category name (list of mcc) ответ: mcc already reserved for category "another name" - если такой mcc код уже в другой категории

Изменить категорию трат add group to category [category to add] ... - добавить всю категорию в категорию ответ: added new group to category name (list of mcc) (list of categories)

Удалить remove category ответ: category removed from (list of categories)

Добавить трату add transaction [mcc] ответ: transaction added into category name (list of categories)

Удалить трату remove transaction - первую подходящую если несколько ответ: transaction deleted

Список категорий show categories ответ: Еда Развлечения Фастфуд Рестораны

Траты по существующим категориям в выбранный месяц (сумма за месяц) showTransactionsByMonth(String month) вывод: Еда 700р 70% Развлечения 700р 70% Фастфуд 300р 30% Рестораны 400р 40% Без категории 0р 0%

Траты в категории по месяцам (сумма за месяц) showTransactionsByCategoryPerMonths show by months ответ: январь 1000р февраль 1100р март 900р
