# README #


This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Single page application where a user can search for a keyword and the images are displayed.
* Version : 1.0.0
* Used https://api.flickr.com/services/rest/ for images

In this project, I have followed MVVM architecture with livedata. I also used two way binding to get rid of boilercode.
- I also used cache so users can use even if there is no internet connection later point of time. 
- I used material design and dark theme to give better user experience


### ScreenShots ###
<img src="https://imgur.com/JJEch2C.png" width="220" height="450">  <img src="https://imgur.com/MUpqJOn.png" width="220" height="450">  <img src="https://imgur.com/eGzR3sY.png" width="220" height="450">

### ScreenShots (Dark Theme) ###
<img src="https://imgur.com/RvUG4pl.png" width="220" height="450">  <img src="https://imgur.com/G5GDGop.png" width="220" height="450">  <img src="https://imgur.com/xfhaw8Z.png" width="220" height="450">


### Dependencies ###
#### Architecture ####
* MVVM architecture used -Loosely coupled architecture : MVVM makes your application architecture as loosely coupled, Extensible code

#### UI Component ####
* Material Library - It is used for using latest MUI component.

#### Testing Libraries ####
* Robolectric and Junit - I have used robolectric and Junit lib. Robolectric - This enables you to run your Android tests in your continuous integration environment without any additional setup and also does not require real device
* Truth Lib - For testing

#### Dependency Injection ####
* Koin : It cuts boiler plates so less code

#### Network Libraies ####
* Retrofit is used for networking
* Gson - Converter is used to parse data
* Coroutines - Improve code performance, Less boiler code

#### Database  ####
* I used cache for for storing temporary data for extensive use db lib can be used like romm 

#### Others  ####
* LiveData, TwoWay Binding

### Features which can be in PRO version ### 
* UI can be improved
* As mentioned, We could use db to save data
* Support mic for search
* Could be liked option for fav images
* Share images to others
* Set as wallpaper etc

### Things that could improved ### 
* Full code coverage - (Time needed) - However I have written unit tests
* UI - Could be improved
* If plan for PRO version then product flavour needed to integrate (Development required)

**Support? : <developer.amitjaiswal@gmail.com>
