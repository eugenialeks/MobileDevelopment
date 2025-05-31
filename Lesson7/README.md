# Практическая работа №7

**Тема:** Сетевое взаимодействие в Android: сокеты, HttpURLConnection и Firebase Authentication

---

## Цель работы

Закрепить навыки:

- Организовать прямое соединение по TCP‐сокетам (Socket API) для получения серверного времени;
- Использовать HttpURLConnection для выполнения HTTP‐запросов, парсинга JSON и вывода сетевых данных (внешний IP + текущая погода);
- Настроить Firebase Authentication для регистрации, входа, выхода и верификации электронной почты пользователей;
- Разобрать и обработать JSON‐ответы, учитывать особенности мобильного подключения (таймзоны, формат данных).
  
---

## Сокетное взаимодействие (модуль TimeService)

Нужно получить текущее время с сервера time.nist.gov (порт 13) через TCP‐сокет, распарсить строку и вывести отдельно дату и время в формате московского времени.

Был создан новый модуль **ru.mirea.golysheva.timeservice**. Было добавлено разрешение в **AndroidManifest.xml**:

``` java
<uses-permission android:name="android.permission.INTERNET" />
```

Это даёт приложению доступ к сети.

По нажатию «Получить время» приложение:

- Открывает TCP‐соединение с time.nist.gov:13.

- Получает строку, которая содержит дату и время UTC.

- Парсит дату/время, конвертирует из UTC в зону Europe/Moscow и выводит в TextView.

![image](https://github.com/user-attachments/assets/4be98174-cf3c-480b-a2c9-22b1f25e8268)

![image](https://github.com/user-attachments/assets/6687cd35-b5bb-48a5-8d02-4abf554562fa)

##  HttpURLConnection и работа с JSON (модуль HttpURLConnection)

Нужно было определить внешний IP‐адрес устройства через сервис ipinfo.io/json. Извлечь из ответа JSON поля: ip, city, region, country, loc (координаты). По полученным координатам (широта, долгота) сделать запрос к сервису Open-Meteo и отобразить текущую погоду в московском часовом поясе.

Был создан модуль **HttpURLConnection**. Были прописаны разрешения в манифесте: 

``` java
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
```

После нажатия «Получить IP и погоду» приложение выполняет два последовательных запроса: к https://ipinfo.io/json и к Open-Meteo.

JSON отвечают с информацией об IP и координатах, а также текущей погоде в Москве.

![image](https://github.com/user-attachments/assets/82fe98cd-7319-4552-8b62-44bfaca1510d)

![image](https://github.com/user-attachments/assets/4d7c3f79-c81b-4006-95d5-531131216973)

---

## Firebase Authentication (модуль FirebaseAuth)

В данном модуле нужно реализовать регистрацию, вход, выход и верификацию электронной почты пользователей с помощью Firebase Authentication (Email/Password). 

Был создан модуль **FirebaseAuth**. Было произведено подключение к Firebase.

![image](https://github.com/user-attachments/assets/382b4f37-403c-404d-934a-c22a1bb149c5)

![image](https://github.com/user-attachments/assets/b6aeccba-878a-415c-9989-f2948a399518)

![image](https://github.com/user-attachments/assets/da75a35a-dc82-47d6-8287-8cd23aa8622b)

![image](https://github.com/user-attachments/assets/583eb035-05ee-4285-b43e-b2a1a43ca9dc)

![image](https://github.com/user-attachments/assets/2be6e118-03c2-4116-8318-1bc0ac43679a)

![image](https://github.com/user-attachments/assets/dcbe6f19-392d-49ba-b942-1ed3de623d94)

---

## Итоги

В практической работе № 7 реализованы и проверены следующие задачи:

1. Сокеты (TimeService):

- Установлено соединение с сервером времени time.nist.gov:13.

- Получена вторая строка от сервера, распарсена в формат UTC.

- Конвертация во время по часовому поясу Europe/Moscow.

- Экран корректно отображает дату и время.

2. HttpURLConnection (HttpURLConnection):

- Определён внешний IP‐адрес с помощью запроса к https://ipinfo.io/json.

- Извлечены и выведены поля: ip, city, region, country, loc (координаты).

- Получены координаты (широта, долгота) и выполнен второй запрос к Open-Meteo.

- Из JSON-ответа {"current_weather": { … }} распознаны и выведены: температура, скорость ветра, направление ветра, код погоды.

---

# MireaProject

Был реализован экран авторизации с использованием Firebase Authentication. Добавлена возможность регистрации нового пользователя. После успешной авторизации — выполнить переход на главный экран.   

![image](https://github.com/user-attachments/assets/2d5e06e1-6b1a-462f-955f-395b82eb7d0f)

![image](https://github.com/user-attachments/assets/d72aa744-bbdf-4847-9444-122503f712c6)

![image](https://github.com/user-attachments/assets/504bd683-e1f4-4c95-8e33-c1745d37a9d1)


Был добавлен фрагмент в Android-приложение MireaProject, который отображает данные из сетевого ресурса. Для получения данных используется библиотека Retrofit.
Данные отображаются в виде текстовой информации (заголовок и тело поста) из открытого API.

![image](https://github.com/user-attachments/assets/bdfc4305-7301-4464-95cf-9b12257c3c5f)

![image](https://github.com/user-attachments/assets/0d9b14d8-b998-4c57-bde9-a0006eb76084)

---

**Выполнила**: Голышева Е.А.  
**Группа**: БСБО-09-22
