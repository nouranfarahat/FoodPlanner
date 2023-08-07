# Easy Chef
=================
 Easy Chef is a mobile application that helps users plan their weekly meals. The application provides various features to search for meals, suggest meals, view categories, and save favorite meals. The application uses the MealDB API
to retrieve meal data.

Features
=================

1- Login with Google AccountFireBase

2- Login as a guest with limited feature

3- Daily Inspiration meals for the user 

4- Showing list of countries so that user can view popular meals in each one.

5- browsing meals by ingredients

6- browsing meals by category

7- browsing meals by country

8- adding meals to favourite list

9- adding meals to week plan

10- show each meal details with ingredients and written and video Steps to prepare the meal

11 - FireBase used to save User Plan 

12-simple Gui 

Architecture
=====================

The application uses the MVP (Model-View-Presenter) architecture pattern. The model represents the data and business logic, 
the view displays the data to the user, and the presenter acts as an intermediary between the model and the view.


Libraries
====================

The application uses the following libraries:

1-Retrofit: For making API requests to the MealDB API.

2-Room: For local storage of favorite meals.

3-Firebase: For authentication and data synchronization.

4-Lottie: For the splash screen animation.

5-Glide: For loading and caching images.

SDK
===================
minSdk 21
targetSdk 33
compileSdk 33
