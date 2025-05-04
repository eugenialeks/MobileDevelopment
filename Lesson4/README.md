# Практическая работа №4

**Тема:** Привязка графических компонентов, асинхронная работа, сервисы и WorkManager в Android‑приложениях

---

## Цель работы

Закрепить навыки:

- подключения и использования `ViewBinding` для связки кода и разметки;
- организации многопоточности с помощью `Thread`, `Handler`, `Looper` и `Loader`;
- передачи данных между потоками и обновления UI из фоновых потоков;
- создания и управления Service‑компонентом для фонового воспроизведения аудио;
- планирования фоновых задач через `WorkManager`.
  
---

## ПРИВЯЗКА ГРАФИЧЕСКИХ КОМПОНЕНТОВ

Был создан проект **ru.mirea.golysheva.Lesson4**.

В нём показано, как включить и применять `View Binding` в активности и фрагменте, а также как с его помощью сверстать экран музыкального плеера для портретной и горизонтальной ориентации.

В файл `app/build.gradle` было добавлено:
```java
viewBinding {
    enabled = true
}
```

![image](https://github.com/user-attachments/assets/fc69139d-4db2-4c9d-b640-dd0c62237be0)

![image](https://github.com/user-attachments/assets/4644f66f-f614-44c6-8c86-0bb8fe478ff7)

Использование View Binding существенно упрощает работу с графическими компонентами Android‑приложения, делая код чище и безопаснее, а также ускоряет поиск ошибок, перенося его на стадию компиляции.

---

## ОСНОВНЫЕ ПОНЯТИЯ АСИНХРОННОЙ РАБОТЫ В ОС ANDROID

- Был создан модуль **thread**

- Включён **View Binding** в `build.gradle`:

```java
viewBinding {
    enabled = true
}
```

![image](https://github.com/user-attachments/assets/c8558b87-daab-4c72-9590-52be3c0108bf)

![image](https://github.com/user-attachments/assets/f08c3353-a0e5-4f36-9f6c-5e6e1a4246f0)

1. **View Binding** в Java‑коде так же устраняет необходимость в `findViewById`, обеспечивая корректную типизацию и защиту от `NullPointerException`.
2. Использование `Thread` позволяет вынести ресурсоёмкие операции за пределы UI‑потока и тем самым предотвратить заморозку интерфейса.
3. Метод `runOnUiThread()` обеспечивает безопасное взаимодействие фоновых потоков с элементами пользовательского интерфейса.
4. Практика подтвердила теорию главы: грамотное распределение задач между потоками — фундамент отзывчивого и стабильного Android‑приложения.

---

## ПЕРЕДАЧА ДАННЫХ МЕЖДУ ПОТОКАМИ

### ЗАДАНИЕ №3.1

Создан модуль **data_thread**.

![image](https://github.com/user-attachments/assets/6dae5413-dbf3-43fd-a50d-496bfd4cc36d)

1. `runOnUiThread(Runnable)`
- Выполняет Runnable в главном (UI) потоке	
- Сразу после добавления, при следующем цикле UI
2. `View.post(Runnable)`
- Отправляет задачу в очередь сообщений конкретного View
- Как только очередь доходит до этой задачи
3. `View.postDelayed(Runnable, delay)`
- Выполняет задачу в UI-потоке с задержкой
- После указанной задержки (в мс)

### ЗАДАНИЕ №3.2

В данном задании реализован механизм взаимодействия между потоками с помощью очереди сообщений (MessageQueue) и компонентов `Looper` и `Handler`. Пользователь вводит возраст и профессию. Информация передается во второй поток, в котором по возрасту осуществляется задержка, а затем результат возвращается обратно в основной поток.

![image](https://github.com/user-attachments/assets/651081a5-7453-4f26-bc5c-94c8c8ca5044)

![image](https://github.com/user-attachments/assets/86c3b919-bf03-4e7b-82ae-e259b7c38319)

В ходе выполнения задания был создан поток с собственным `Looper`, настроен обмен сообщениями между фоновым и главным потоком с помощью `Handler`, реализована задержка по времени и возвращение результата. 

### ЗАДАНИЕ №3.3

Был создан модуль **CryptoLoader**. Включён `ViewBinding` в `build.gradle` модуля добавлена строка:

```java
viewBinding {
    enabled = true
} }
```

Также был написан класс **CryptoUtils** с методами:

- `generateKey()` – генерирует 256-битный ключ AES;
- `encryptMsg(String, SecretKey)` – шифрует строку в массив байт;
- `decryptMsg(byte[], SecretKey)` – дешифрует массив байт обратно в строку.

Был реализован собственный **Loader**

Создан класс **MyLoader**, наследующий `AsyncTaskLoader<String>`.

В конструкторе принимает `Context` и `Bundle` с зашифрованными данными и ключом.

В `onStartLoading()` вызывает `forceLoad()`, а в `loadInBackground()` выполняет дешифрование через CryptoUtils и возвращает результат.

![image](https://github.com/user-attachments/assets/0cae6d53-a9ad-404c-8943-34a466409e0f)

Реализован полноценный пример использования `LoaderManager` и собственного `AsyncTaskLoader` для асинхронной обработки данных (дешифровки AES) с безопасной передачей результата в UI-поток, устойчивый к изменениям конфигурации.

---

## СЕРВИС 

Создан новый модуль **ServiceApp**. В `build.gradle` модуля включён `ViewBinding`. В `AndroidManifest.xml` модули добавлены разрешения:

```xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
```

Внутри <application> зарегистрирован сервис:

```xml
<service
    android:name=".PlayerService"
    android:exported="true"
    android:enabled="true"
    android:foregroundServiceType="mediaPlayback"/>
```

Также был добавлен аудиофайл. Для этого была создана папка **res/raw** и скопирован MP3-файл **music.mp3** в нее.

Создан класс **PlayerService**, наследующий `Service`.

В `onCreate()`:

- Создан `NotificationChannel` и запущен foreground-сервис с уведомлением о «MIREA Music Player».
- Инициализирован `MediaPlayer` для воспроизведения `R.raw.music`.

В `onStartCommand()`:

- Запускается проигрывание и устанавливается `OnCompletionListener`, чтобы по окончании автоматически снять foreground.
- Вернул `START_NOT_STICKY` для корректного поведения при рестарте.

В `onDestroy()`:

- Останавливается и освобождается MediaPlayer, удаляется foreground-уведомление.

![image](https://github.com/user-attachments/assets/7b0a4475-71da-4a79-aa18-ce45bff199a2)

![image](https://github.com/user-attachments/assets/8d6d2bb9-c23c-423a-accb-25a909321ac7)

Реализован foreground-сервис для фонового воспроизведения аудио с корректной работой в разных версиях Android, включая создание уведомлений, управление жизненным циклом (`onCreate`, `onStartCommand`, `onDestroy`) и обработку разрешений.

## WORKMANAGER

Был создан модуль **WorkManager**. Подключена библиотека **WorkManager** В `build.gradle` модуля добавлена зависимость:

```java
implementation "androidx.work:work-runtime:2.10.0"
```

Реализован класс **UploadWorker** и настроенны ограничения (Constraints):

```java
Constraints constraints = new Constraints.Builder()
    .setRequiredNetworkType(NetworkType.UNMETERED)  // только Wi-Fi
    .setRequiresCharging(true)                     // во время зарядки
    .build();
```

![image](https://github.com/user-attachments/assets/0dbcf63c-498f-4fc2-abfd-ab165b83dc35)

В модуле **WorkManager** реализован пример постановки фоновой задачи через `OneTimeWorkRequest` с заданными ограничениями, показан механизм планирования и автоматического обхода Doze/JobScheduler, а также проверка работы задачи и её отмены при несоблюдении `Constraints`.

---

## Итоги

В ходе выполнения практической работы №4 закреплены навыки:

- использования `ViewBinding` для безопасного доступа к элементам интерфейса;
- организации многопоточности и обмена данными между потоками;
- асинхронной загрузки и обработки данных через `Loader`;
- создания foreground‑сервиса с уведомлением и воспроизведением медиа;
- планирования периодических задач при помощи `WorkManager`.

---

# MireaProject

Был добавлен новый фрагмент **WorkFragment** в проект **MireaProject**, реализующий выполнение фоновой задачи с помощью библиотеки `WorkManager`.

Задача запускается по кнопке, выполняется в отдельном потоке с задержкой, и её статус отображается в интерфейсе.

- `MyWorker` — класс-наследник Worker, реализующий фоновую задачу (с задержкой 5 секунд и логированием).
- `WorkFragment` — интерфейс с кнопкой запуска задачи и отображением её статуса (ENQUEUED, RUNNING, SUCCEEDED).

Фоновая задача запускается через **WorkManager** с использованием `OneTimeWorkRequest` и задержкой через `Thread.sleep(5000)`.

![image](https://github.com/user-attachments/assets/24abec16-c30b-47c7-97d9-ab5e6af68218)


---

**Выполнила**: Голышева Е.А.  
**Группа**: БСБО-09-22
