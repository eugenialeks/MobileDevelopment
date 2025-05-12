# Практическая работа №5

**Тема:** Использование аппаратных возможностей мобильных устройств: сенсоры, механизм разрешений, камера и микрофон в Android-приложениях

---

## Цель работы

Закрепить навыки:

- получения и отображения списка аппаратных датчиков устройства;
- чтения показаний акселерометра в реальном времени;
- запроса «опасных» разрешений (runtime permissions) и обработки ответа пользователя;
- вызова системного приложения «Камера», сохранения снимка через `FileProvider` и отображения результата;
- записи и воспроизведения аудио при помощи `MediaRecorder` и `MediaPlayer`;
- интеграции сенсоров, камеры и диктофона в единый проект **MireaProject**.
  
---

## СПИСОК ДАТЧИКОВ

Был создан проект **ru.mirea.golysheva.Lesson5**. Внутри демонстрируется работа с классом SensorManager для получения полной коллекции поддерживаемых датчиков:

```java
SensorManager sensorManager =
        (SensorManager) getSystemService(Context.SENSOR_SERVICE);
List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
```
В макете **activity_main.xml** размещён `ListView`; при старте активности заполняется массивом хеш-карт, где Name — имя сенсора, Value — максимальный диапазон измерений. В результате пользователь видит полный перечень аппаратных и виртуальных датчиков устройства.

![image](https://github.com/user-attachments/assets/c286ad53-1d08-47a7-98e0-065efb146b6b)

---

##  ПОКАЗАНИЯ АКСЕЛЕРОМЕТРА

Создан модуль **Accelerometer**. Активность реализует интерфейс `SensorEventListener`, регистрируя слушатель в `onResume()` и освобождая ресурсы в `onPause()`:

```java
sensorManager.registerListener(this,
        accelerometer,
        SensorManager.SENSOR_DELAY_NORMAL);
```

В методе `onSensorChanged()` значения осей x, y, z выводятся в три **TextView**, обновляясь при каждом изменении ориентации устройства.

![image](https://github.com/user-attachments/assets/aac87fa7-86a1-41a6-853f-db2b551d4176)


---

## МЕХАНИЗМ РАЗРЕШЕНИЙ

Добавление «dangerous»-permissions в **AndroidManifest.xml**:

```xml
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES"/>
```

---

## КАМЕРА

Модуль **Camera** демонстрирует вызов системного приложения «Камера» посредством неявного намерения `MediaStore.ACTION_IMAGE_CAPTURE`. Для безопасного обмена файлом настроен `FileProvider`; его дескриптор добавлен в **AndroidManifest.xml**, а пути описаны в `res/xml/paths.xml`.

Перед запуском камеры формируется временный файл в директории **Pictures** приложения, генерируется Uri:

```java
String authorities = getPackageName() + ".fileprovider";
imageUri = FileProvider.getUriForFile(this, authorities, photoFile);
cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
```

После съёмки снимок автоматически сохраняется и отображается в **ImageView**.

![image](https://github.com/user-attachments/assets/46d36304-1bc6-4bb1-8ab7-df8b6f014283)

![image](https://github.com/user-attachments/assets/09ba43de-eef0-46e6-9f96-925e6b71f064)

![image](https://github.com/user-attachments/assets/1c51996d-f59e-435b-97f9-1fe9f7e98170)

---

## МИКРОФОН

В модуле **AudioRecord** реализован диктофон. При старте записи создаётся и настраивается объект `MediaRecorder`.

Запись:

```java
recorder = new MediaRecorder();
recorder.setAudioSource(MIC);
recorder.setOutputFormat(THREE_GPP);
recorder.setAudioEncoder(AMR_NB);
recorder.setOutputFile(recordPath);
recorder.prepare();
recorder.start();
```

Воспроизведение:

```java
player = new MediaPlayer();
player.setDataSource(recordPath);
player.prepare();
player.start();
```

- Кнопки **Record** и **Play** блокируют друг друга, предотвращая одновременный доступ к файлу.
- Аудиофайл сохраняется в `/Android/data/<pkg>/files/Music/audiorecordtest.3gp`.

![image](https://github.com/user-attachments/assets/5ca585c2-6301-4fb3-acae-40239cf79e49)

![image](https://github.com/user-attachments/assets/f4402d46-8d45-4d0b-9e5a-dade9ca7536c)

![image](https://github.com/user-attachments/assets/e3be9e2a-4a98-4a64-b6a8-3e1fd03f4a7c)

![image](https://github.com/user-attachments/assets/42e3f921-fab3-439f-9d18-e9281c412bee)

---

## Итоги

В практической работе № 5 выполнены и проверены:

- Вывод полного перечня сенсоров и чтение потоковых данных акселерометра.
- Реализация динамического запроса «опасных» разрешений, совместимая c Android 6-14.
- Захват снимка стандартной камерой c безопасной передачей URI через `FileProvider`.
- Сохранение фото в публичной медиатеке и отображение в «Галерее».
- Запись звука микрофона, сохранение в 3GPP-файл и воспроизведение через `MediaPlayer`.
- Корректное освобождение ресурсов и обработка жизненного цикла активностей.

---

# MireaProject

В рамках контрольного задания в проект **MireaProject** были добавлены три новых фрагмента, реализующих работу с аппаратной частью устройства

![image](https://github.com/user-attachments/assets/ed55ac88-daf9-46a5-bda2-e32347c26a69)

---

## SensorFragment

- Используются датчики `TYPE_ACCELEROMETER` и `TYPE_MAGNETIC_FIELD`.
- Определяется направление на север, выводится угол в градусах.

![image](https://github.com/user-attachments/assets/10aa1aa1-00b0-4c22-a091-e3dccbc51ca2)

---

## CameraFragment

- Используется встроенное приложение камеры через `Intent`.
- После съёмки изображение отображается на экране.

![image](https://github.com/user-attachments/assets/b8389c18-ee2a-4ff3-bf03-74fd7d13f907)

![image](https://github.com/user-attachments/assets/99e3e20d-4266-4efd-b4cc-d52251269417)

---

## MicrophoneFragment

- Используется MediaRecorder для записи аудио.
- Добавлена кнопка для воспроизведения записанного файла через MediaPlayer.
- Кнопка "Воспроизвести" становится видимой только после завершения записи.

![image](https://github.com/user-attachments/assets/f7ec3e66-1154-40d0-a490-d840415066ca)

![image](https://github.com/user-attachments/assets/7bbe44e7-65b0-462a-bd88-5b2e775a0cd2)

![image](https://github.com/user-attachments/assets/5d0d249a-73c5-4629-a973-da59b5c2e97f)

---

## ЗАПРОС РАЗРЕШЕНИЙ

В код каждого фрагмента добавлены проверки и запросы разрешений:

- `CAMERA` — для съёмки;
- `RECORD_AUDIO` — для записи звука;
- `WRITE_EXTERNAL_STORAGE` — для сохранения файла.

Также в **AndroidManifest.xml** добавлено:

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    tools:ignore="ScopedStorage"/>
<uses-feature android:name="android.hardware.camera" android:required="false" />
```

---

**Выполнила**: Голышева Е.А.  
**Группа**: БСБО-09-22
