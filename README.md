# MazeTVApp
Jobsity challange

SOME OBSERVATIONS:

- In order to follow some of the SOLID principles, I chose to use MVVM and Clean architecture combined as well as a Dependency Injection lib (Dagger Hilt);
- For the API access, I used Retrofit2 and Android Paging lib;
- For observables/observers I used mostly Flow. A small exception happened when trying to hold data from the viewModel inside SavedStateHandle, it only accepts LiveData.

APP:

- Covered all mandatory features.. didn't have time to implement some of the optionals;
- Latest APK (debug only) is available [here](https://github.com/pedrotlf/MazeTVApp/blob/master/distribution/app-debug.apk)
