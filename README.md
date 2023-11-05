# HammerSystemsPizza
Pizza App for Hammer Systems Company
Stack: MVVM, Clean Architecture, Jetpack Compose, Dagger Hilt, Retrofit, Room, Kotlin Coroutines, Lottie, Navigation Component.
1) I wrote my own server for the app. Stack: Ktor, Render. The API of the server consists of a single handle /pizzas, which returns a json file containing pizzas. URL: https://hammersystemspizza-backend.onrender.com/pizzas
2) When logging into the application, a GET request is sent to the server to get a list of pizzas. Upon successful download, the information is cached in the internal database.
3) In the absence of the Internet, there is an appeal to the local database to download the data.
4) Lottie animation icon on Bottom Bar.
