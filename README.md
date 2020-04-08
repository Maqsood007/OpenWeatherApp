# OpenWeatherApp
This repository has android Implementation of Android JetPack &amp; MVVM to fetch Weather forecast for current location and for selected cities.


# Requirements to run the code:

  **Pre-Requisite**
  
   - Java 1.8 (jdk 8)
   - Android Studio + SDK installation
   - Android gradle plugin version: 3.6.1
   - Min API Level: 21
   - Gradle version: 5.6.4
   - TargetSdkVersion: 28

  **With Android Studio**

   - Clone the Project from https://github.com/Maqsood007/OpenWeatherApp.git.
   - Open the cloned project with **Android Studio** and after building, choose a device (Real Device/Emulator) and click run button.
   
      - **To Run the Test**
         
         - Go to app tests folder **test**/**androidTest**
         - Right Click on test file and select 'Run ${FileName.kt} with coverage'
         
# To run the code with gradle and generate the tests reports:         

   
   - Clone the Project from https://github.com/Maqsood007/OpenWeatherApp.git.
   - Open the command line terminal.
   - Navigate to the cloned repo 'OpenWeatherApp'.
   - **Important**sWrite command 'touch local.properties' to generate the file and copy paste your system installation path to SDK i.e dk.dir=/Users/maqsood/Library/Android/sdk.
   - Write and enter './gradlew clean' to clean the project.
   - Write and enter './gradlew build' to build the project.
   
   **After building the project successfully follow below to generate the reports.**
   
   - **Test with out coverage:**
   - Write and enter './gradlew app:test' to run all the test in the project without coverage.
   - You can found reports at /OpenWeatherApp/app/build/reports
     - Folder **androidTest** will contain instrumental tests reports and **tests** will contain unit tests reports.
   ![alt text](https://github.com/Maqsood007/NYT-Most-Popular/blob/development/screenshots/Screen%20Shot%202020-01-02%20at%2012.28.28%20AM.png) 
      
   - **Test with coverage:**   
   - Write and enter './gradlew connectedAndroidTest' to run all the test in the project and generate the coverage reports.
   - You can found coverage reports at path **/OpenWeatherApp/app/build/reports/coverage**.
   
   ![alt text](https://github.com/Maqsood007/NYT-Most-Popular/blob/development/screenshots/Screen%20Shot%202020-01-02%20at%2012.28.38%20AM.png) 


# Technologies stack:
   - Android MVVM (ModelView-View-Model)
   - Programing Language: Kotlin
   - Android Navigation
   - Data Binding
   - Material Design practices
   - Dagger 2 for dependency Injection
   - Retrofit library: For remote network calls
   - Reactive Design pattern. RxKotlin & RxAndroid.
   - Picasso for image loading library.
   - Testing: Mockito for Mocking objects.
   - Androidx artifacts.
   - Master/Detail approach with ModelView API's.

# Why MVVM? Why not MVC or MVP:
Each day coming, we have evolutions in coding practices. They time Android started its journey was much different then what it is now. There are more then one practices that are being used in Android Application Development.

Lets start with **MVC**:

The coding approach is counted as batter or best on the base of few parameters. For example

   - Is it following modular approach?
   - Is it following 'Separation of concern' approach.
   - Dependency between classes.
   
      - In traditional **MVC (Model-View-Controller)** approach is bit modular but we dont have the concept of separate the concern. Controller is controlling the communication between model and view but controller will always have coupling with view instance and model.
      - In **MVP (Model-View-Presenter)** we overcome MVC disadvantages but still we fell into the callback(interfaces) hell. Also in MVP Presenter will have coupling with View and model. Testing the classes with coupling and dependencies to other classes is difficult.
      
# MVVM (ModelView-View-Model) Came into the picture:
   - Easier testing and modularity.
   - Less coupling between components.
   - Reactive design pattern support.
   - Observer and observable approaches.  
   - Clearer separation of the UI and application logic.
    

# Dagger2 (Dependency injection):
   - Inversion of control. Dependency injection made coder life easier. When a class depends on another class, it should create an instance of the class. This creates coupling between classes. With **Dagger2** we will take care of the thing to make things easier for testing.
   

# RxAndroid & RxKotlin:
   - To achieve reactive design pattern. 
   
# Retrofit:
   - Networking library.
   
# Architecture Navigation Component
   - Used Android JetPack architectural component - Navigation

# ScreenShots: Output:
**Portrait:**

![alt text](https://github.com/Maqsood007/NYT-Most-Popular/blob/development/screenshots/device-2020-01-01-230135.png)

![alt text](https://github.com/Maqsood007/NYT-Most-Popular/blob/development/screenshots/device-2020-01-01-230227.png)

**Landscape:**

![alt text](https://github.com/Maqsood007/NYT-Most-Popular/blob/development/screenshots/device-2020-01-01-230309.png)

![alt text](https://github.com/Maqsood007/NYT-Most-Popular/blob/development/screenshots/device-2020-01-01-230327.png)

   
   

 
    

