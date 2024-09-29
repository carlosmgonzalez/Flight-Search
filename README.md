<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
  </head>
  <body>
    <h1 class="project-title" style="color: #333; text-align: center; margin-bottom: 20px;">Flight Search</h1>
      
  <p>
    This is a small application that simulates a flight search engine. Simply start typing the name of the airport and suggestions will appear as you type. Once you select the desired airport, all available flights will be displayed, and you can save any flights of interest to your favorites.
  </p>
  <p>
    The app is very intuitive: If you're not actively searching, it shows your favorites. If you start typing, it shows a list of airports. And if you select an airport, it displays the list of available flights..
  </p>
  <p>
   Under the hood, it uses Room for SQLite database management and creation, Dagger Hilt for dependency injection, Datastore to save the user's last search if they didn't finish it, and follows the Model-View-ViewModel (MVVM) design pattern.
  </p>

  ![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-blue?style=flat-square)
  ![Kotlin](https://img.shields.io/badge/Kotlin-yellow?style=flat-square)
  ![Dagger Hilt](https://img.shields.io/badge/Dagger%20Hilt-green?style=flat-square)
  ![Room](https://img.shields.io/badge/Room-red?style=flat-square)
  ![Datastore](https://img.shields.io/badge/Datastore-purple?style=flat-square)
  ![Room](https://img.shields.io/badge/Room-orange?style=flat-square)
  
  <h2>Project gallery</h2>
  <div class="project-gallery" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 15px;">
    <img src="https://res.cloudinary.com/difikt7so/image/upload/v1727645950/android-apps/final-projects-images/flight-search/g1kvuhtrhnicthm2vtdh.png" alt="Screenshot 3" style="box-shadow: 0 2px 4px rgba(0,0,0,0.1);" width="270" height="600">
    <img src="https://res.cloudinary.com/difikt7so/image/upload/v1727645949/android-apps/final-projects-images/flight-search/pdcxf0nbohfhqgxustg2.png" alt="Screenshot 2" style="box-shadow: 0 2px 4px rgba(0,0,0,0.1);" width="270" height="600">
    <img src="https://res.cloudinary.com/difikt7so/image/upload/v1727645950/android-apps/final-projects-images/flight-search/ysdpawqxkwjgugukiufx.png" alt="Screenshot 3" style="box-shadow: 0 2px 4px rgba(0,0,0,0.1);" width="270" height="600">
    <img src="https://res.cloudinary.com/difikt7so/image/upload/v1727645949/android-apps/final-projects-images/flight-search/lzfxaj5geu4zjasndmlh.png" alt="Screenshot 3" style="box-shadow: 0 2px 4px rgba(0,0,0,0.1);" width="270" height="600">
  </div>
</body>
</html>
