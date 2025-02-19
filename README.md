## Описание

Простое Android-приложение, которое отображает текущую погоду в указанном пользователем городе. 

Приложение использует OpenWeatherMap API для получения данных о погоде.

## Функциональность

•   **Ввод города:** *Пользователь может ввести название города в поле ввода.*

•   **Получение погоды:** *После нажатия кнопки "Получить погоду", приложение отправляет запрос к OpenWeatherMap API.*

•   **Отображение погоды:** *Приложение отображает текущую температуру в указанном городе.*

•   **Отображение ощущаемой погоды:** *Приложение отображает ощущаемую температуру в указанном городе.*

•   **Отображение описания погоды:** *Приложение отображает описание температуры в указанном городе.*

•   **Отображение скорости ветра:** *Приложение отображает описание температуры в указанном городе.*

•   **Обработка ошибок:** *Выводит сообщение об ошибке, если поле ввода пустое.*

## Скриншоты

<img src="https://github.com/user-attachments/assets/ecab9dfb-f927-48a3-ac3d-af6f3d80d5b5" style=" width:300px ; height:600px " />
<img src="https://github.com/user-attachments/assets/68f4cbe4-4f4d-44c4-a637-8b23851bc6b7" style=" width:300px ; height:600px " />
<img src="https://github.com/user-attachments/assets/7738d84c-620b-4a05-9516-a647ec6e074b" style=" width:300px ; height:600px " />

## Детальное описание кода


•   MainActivity.java:

    •  onCreate(Bundle savedInstanceState):


        *   Инициализация UI и связывание элементов с переменными.


        *   Установка слушателя для кнопки "Получить погоду".


        *   Проверка поля ввода на пустоту.


        *   Формирование URL для OpenWeatherMap API.


        *   Запуск AsyncTask для выполнения запроса в фоновом потоке.


    •   GetURLData (AsyncTask):


        *   onPreExecute(): Отображение "Ожидайте...".


        *   doInBackground(String... strings):


            *   Выполнение запроса к API.


            *   Получение данных JSON.


        *   onPostExecute(String result):


            *   Обработка JSON и отображение температуры.

•   activity_main.xml:


    •   Разметка UI: EditText, TextView, Button.


    •   Использование ConstraintLayout для позиционирования.

•   strings.xml:


    •   Хранение текстовых строк (например, сообщение об ошибке).

## Инструкция по использованию


1.  **Получите API ключ:** *Зарегистрируйтесь на OpenWeatherMap (https://openweathermap.org/) и получите бесплатный API ключ.*


2.  **Установите API ключ:** *Замените "YOUR_API_KEY" на ваш API ключ в файле MainActivity.java.*


3.  **Запустите приложение:** *Соберите и запустите приложение на Android-устройстве или эмуляторе.*


4.  **Введите город:** *Введите название города в поле ввода.*


5.  **Получите погоду:** *Нажмите на кнопку "Получить погоду".*


6.  **Просмотрите результат:** *Текущая температура для указанного города отобразится на экране.*

## Технологии


•   **Язык программирования:** Java


•   **Платформа:** Android


•   **API:** OpenWeatherMap API


•   **Среда разработки:** Android Studio

## Зависимости


•   *Android SDK*


•   *OpenWeatherMap API*

## Требования к среде


•   *Android Studio*


•   *Доступ в Интернет*


## Контакты


•   *Al1veeee*


•   *alimkhanov.de@mail.ru*


•   *https://github.com/Al1veeee*
