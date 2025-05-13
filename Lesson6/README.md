# Практическая работа №6

**Тема:** Хранение пользовательских данных с помощью `SharedPreferences` и файловая работа в Android-приложениях

---

## Цель работы

Закрепить навыки:

- Простые настройки через `SharedPreferences`;
- Защищённые настройки через `EncryptedSharedPreferences`;
- Чтение и запись файлов во внутреннем и внешнем хранилищах;
- Проектирование реляционной базы на SQLite и удобный доступ к ней через `Room`.
  
---

## SHARED PREFERENCES

Создан модуль **Lesson6**.
На экране размещены три поля ввода:
- Номер группы;
- Номер по списку;
- Любимый фильм/сериал.

По нажатию кнопки «Сохранить» значения сохраняются в getSharedPreferences("mirea_settings", MODE_PRIVATE); при старте приложения поля заполняются сохранёнными значениями.
Сделан скриншот XML-файла настроек (**папка raw**).

![image](https://github.com/user-attachments/assets/b305c894-e38e-42ba-b597-170f4cbeb79d)

![image](https://github.com/user-attachments/assets/7c79ead3-b847-4eae-bab5-389cf85dbbb0)

![image](https://github.com/user-attachments/assets/370aca16-abaf-47e1-8f94-cdc59a077044)

###  EncryptedSharedPreferences

Создан модуль **SecureSharedPreferences**.
Добавлена зависимость `androidx.security:security-crypto:1.0.0`.
Сгенерирован мастер-ключ через `MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)`.
Создан защищённый файл настроек `EncryptedSharedPreferences.create(...)` для хранения имени любимого поэта и пути к его изображению.
Скриншот содержимого защищённого файла помещён в папку **raw**.

![image](https://github.com/user-attachments/assets/14dc9121-d85a-4c74-bceb-0c3fddc460c6)

---

## РАБОТА С ФАЙЛАМИ

Создан модуль **InternalFileStorage**. При нажатии «Сохранить» данные записываются в файл **russian_history.txt** через `openFileOutput(...)`.
Файл перенесён в **raw**. 

![image](https://github.com/user-attachments/assets/bdc82a6e-a042-48ee-899b-27d1007c9fc3)

![image](https://github.com/user-attachments/assets/dd7ce7fe-97a9-4af4-87e8-5d9f8149c43f)

![image](https://github.com/user-attachments/assets/597b783a-3527-4acd-8418-3a91954c6dde)

### Notebook

Модуль **Notebook**: реализовано приложение «Блокнот».
На экране поля «Название файла» и «Цитата», кнопки «Сохранить» и «Загрузить».
Файлы сохраняются в `Environment.DIRECTORY_DOCUMENTS` (публичный каталог).
Созданы две записи с цитатами известных людей, файлы перенесены в **raw**. 

![image](https://github.com/user-attachments/assets/97c5be9e-87e1-4dc9-8d6c-2eb2fbc272c5)

![image](https://github.com/user-attachments/assets/6f390166-43f5-4007-8f93-7444cf58f7b8)

![image](https://github.com/user-attachments/assets/3371687e-f8bc-422a-9191-8c1b0a9030ab)

---

## БАЗА ДАННЫХ SQLITE

Модуль **EmployeeDB**: хранение вымышленных супер-героев.
Класс **Hero** с полями `name`, `power`, `rating`.
Класс **HeroDao** с CRUD-методами (`@Insert`, `@Query`, `@Update`, `@Delete`).
Database: абстрактный класс **HeroDatabase** extends RoomDatabase, версия = 1.
Application: Класс **App** singleton-инициализация БД через `Room.databaseBuilder(...).allowMainThreadQueries()`.
В **MainActivity** продемонстрированы вставка, чтение, обновление и удаление записей.

![image](https://github.com/user-attachments/assets/7473fac6-3737-4533-a206-e6135a06446a)

![image](https://github.com/user-attachments/assets/38d149e4-edf4-48d1-95ce-54e792baf2b2)

![image](https://github.com/user-attachments/assets/a54837b5-8292-40e5-b0e3-72e3778a49a2)

![image](https://github.com/user-attachments/assets/1cf3a6b1-f8e3-400f-95f8-2380e7b4a430)

---

## Итоги

В практической работе № 6 выполнены и проверены:

- Конфигурация и использование `SharedPreferences` и `EncryptedSharedPreferences`.
- Чтение и запись файлов во внутренних и внешних каталогах устройства.
- Проектирование и использование SQLite через высокоуровневый компонент `Room`.

---

# MireaProject

В проект **MireaProject** были добавлены два новых фрагмента:

1. Профиль — пользователь вводит и сохраняет данные (имя, группа) с помощью `SharedPreferences`.
2. Файлы — реализована работа с файлами, отображение всех сохранённых файлов и создание новых через `FloatingActionButton`.

---

## Фрагмент «Профиль»

Разработан экран для ввода имени и группы студента.
Данные сохраняются с помощью механизма `SharedPreferences`.
При следующем открытии экрана введённые данные восстанавливаются автоматически.
Интерфейс реализован с использованием компонентов `EditText` и `Button`.

![image](https://github.com/user-attachments/assets/56c4b79d-2f2f-43f7-842c-20d0b44445ed)

![image](https://github.com/user-attachments/assets/6ea23fe4-ec07-4388-8546-002bfe5ed557)

---

## Фрагмент «Работа с файлами»

Пользователь может создавать текстовые файлы во внутреннем хранилище.
Для создания используется `FloatingActionButton`, при нажатии которого появляется диалог ввода имени и содержимого файла.
Все созданные файлы отображаются в виде списка.
При нажатии на любой файл открывается `AlertDialog` с его содержимым.

![image](https://github.com/user-attachments/assets/2498618b-58ef-42bb-9dcf-a15b2823e9eb)

![image](https://github.com/user-attachments/assets/5eeee841-895a-4557-ad0e-0e6b43c2b177)

![image](https://github.com/user-attachments/assets/e88bda83-7998-4136-82d7-c4cab998d151)

---

**Выполнила**: Голышева Е.А.  
**Группа**: БСБО-09-22
