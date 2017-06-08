# Baking App
An Android app that allows a baker-in-chief to share recipes with the world. The app user can select a recipe
and see video-guided steps for how to complete it. The recipes' instructions, ingredients, videos, and images
are fetched from a JSON file hosted online. The user can switch between the different steps of a recipe by 
swiping left and right. 

The app also includes a homescreen widget to enhance the user's experience by displaying ingredient list
for a desired recipe.

The app uses a Master/Detail approach to create a responsive design that works on phones and tablets.

Several UI tests using Espresso are included  

## Screenshots
### Tablet UI ###

<img src="screenshots/baking-app_tablet.png">

### Phone UI ###   
<img src="screenshots/baking-app_1.png" width="250">  <img src="screenshots/baking-app_2.png" width="250">  
<img src="screenshots/baking-app_3.png" width="250">  <img src="screenshots/baking-app_4.png" width="250">  

## Getting Started
The below instruction will get you a copy of the project up and running on your machine for development and testing purposes.

### Prerequisites
Android Studio including SDK version 25 and build tools version 25.0.2.  
You can always update to the latest versions. 
Requires API 17+  

### Installing and Deployment
1. Clone the repository or download the project
2. Import the project to your Android Studio
3. Build the project
4. Install the APK on your device or an emulator

### Tests
1. Recipe RecyclerView Tests - Check if data is fetched from the URL and loaded into the RecyclerView
2. Steps RecyclerView Tests - Check if Data is being loaded into the RecyclerView after clicking on an Item in the Recipe's RecyclerView
3. Floating Action Button Tests - Check if the FAB expands the BottomSheet and shows the ingredients text
4. Steps Instructions Test - Check if an item in the Steps' RecyclerView opens the detail Activity 
5. Steps Activity Test - Check if clicking and item in the Recipe's RecyclerView starts an Intent with the correct extras (position of item in the list)  

### Built With
[Android Studio](https://developer.android.com/studio/index.html) - The IDE used  
[Gradle](https://gradle.org/) - Dependency Management  

### Libraries Used
[Volley](https://github.com/google/volley) - Transmitting Networking Data  
[ExoPlayer](https://github.com/google/ExoPlayer) - Media Player  
[Espresso](https://google.github.io/android-testing-support-library/docs/espresso/index.html) - Android Testing Support Library (ATSL)  
[ButterKnife](https://jakewharton.github.io/butterknife/) - Bind Android views  

### Contributing 
Pull requests are gracefully accepted.  

### License
The project is licensed under the MIT License - see [LICENSE.txt](LICENSE.txt) file for detail.
