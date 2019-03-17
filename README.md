# Halo
Halo is a simple weather app using Dark Sky's forecast API. When the app is initially opened, the device's current location is acquired and a request to the Dark Sky API is made to fetch the current and future weather conditions. Dark Sky provides an abundance of data such as the weather summary, precipitation odds, and humidity, so taht this info is displayed within the UI. The obtained data is save in SQLite database where it cane be fetched and using the Data Util and WeatherDataConverter class  all of this information is converted to data model classes making it easy to display for the UI in the future. The app has five main views: the home screen where the weather information for each location is displayed separately and display by viewpager, the second screen where the user can see the list of all saved location and their current forecast's. That second screen is called by tapping the 'city' icon at the top of the screen. The third screen contains a searchview toolbar where the User can find the location they want information for. It can be called by taping the floating action button in the second screen. The fourth and fifth screen's can be called by taping the overflow menu icon at the top of the screen, the first one contains basic settings like unit's of measurement used for provided data and the way time is formatted according to certain time zone. The last screen contains basic information about the app itself.

## Building project

### API Keys
In order to hide the access tokens for Mapbox and Dark Sky, access tokens are purposely not checked into version control. Thus, if you checkout this project and try compiling, the build will fail. To resolve this issue, open your local `locale.properties` file stored in `<USER NAME>/.gradle/locale.properties` (on Mac) and add the following lines with your own access token:

```groovy
darkskyKey="DARK_SKY_TOKEN_HERE"
mapboxAccessToken="MAPBOX_ACCESS_TOKEN_HERE"
```

## Architecture
Halo is written in Kotlin using a single activity MVVM architecture. The app relies on the [Android Bindings library](https://developer.android.com/topic/libraries/data-binding/) to populate the views and the [ViewModel/LiveData](https://developer.android.com/topic/libraries/architecture/viewmodel) Android Architecture Components library to extract logical data from SQLite database managed by [Room Persistence library](https://developer.android.com/topic/libraries/architecture/room). The app navigation relies on the [Navigation component library](https://developer.android.com/guide/navigation/) and the background work is schedule by the [WorkManager API](https://developer.android.com/topic/libraries/architecture/workmanager).

The app targets the latest API level, with a min sdk version of 21, and makes use of the new [Material Design Components](https://material.io/develop/android/docs/getting-started/) library (The base app theme extends the material dark style.)

## Location and Weather API's

To allow users the ability to query additional location weather conditions besides their current locations, Halo uses The [Mapbox Geocoding APIs](https://www.mapbox.com/) to populate a human-readable format list of possible location's and populate current, short and long term forecast for them using The [Dark Sky Api](https://darksky.net/dev). Additionally, during the opening of the app in case of certain time delay or location movement the app make a reverse geocoding API call to update data.

All API calls are made using Retrofit combined with OkHttp and the resulting JSON is deserialized using the popular GSON library. The output data is observed and collected with RxJava.

## Firebase
Halo uses Firebase mobile platform for futher app development. Currently it use's:
- Analytics for insight on app usage and user engagement
- Crashlytics for collecting crash reports 
- Performance for app's performance issues
- Remote Config for change the behavior and content on kye-value data
- Firebase Messaging for delivering messages to User's if there is a reason for it

## Additional libraries

Some additional libraries used in this project include:
- Dagger 2 for dependency injection
- Glide for displaying forecast icon's
- Timber for logging
- Constraint Layout
- ViewPager
- RecyclerView
- CardView
- Google Play Location services for gathering the users location

## License

Copyright 2019 mbojec

   Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
