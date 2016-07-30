## What do we want to achieve?

* Learn about Firebase and it's benefits
* Practice the migration of services to the new Firebase platform
* See the data coming through Firebase


---

## Getting started

This repository contains an Android application to show Super Heroes information:

![ApplicationScreencast][applicationScreencast]

This Application is based on three Activities:

* ``SuperHeroesLoginActivity`` logs the user into the application using a Twitter account

* ``SuperHeroesActivity`` showing a list of super heroes with name, photo and a special badge if is part of the Avengers Team.

![MainActivityScreenhot][mainActivityScreenshot]

* ``SuperHeroDetailActivity`` showing detailed information about a super hero like his or her name, photo and description.

![SuperHeroDetailActivityScreenshot][superHeroDetailActivityScreenshot]


The application architecture, dependencies and configuration is ready to just start writing tests. In this project you'll find  ``Dagger2`` configured to be able to replace production code with test doubles easily and Espresso to be able to interact with the application user interface.


## Tasks

Your task as Android Developer is to **migrate all third party services to the Firebase platform**.

**This repository is ready to build the application, pass the checkstyle and your tests in Travis-CI environments.**


Our recommendation for this exercise is:

  * Before starting
    1. Fork this repository.
    2. Checkout `migration-start` branch.
    3. Execute the application, explore it manually and make yourself familiar with the code.

  * To help you get started, these is the recommended order to migrate services:
    1. Start by injecting the ``MixpanelTracking`` into the `Presenters`
    2. Track some events like Login, List clicked, etc...
    3. Create Analytics interface
    4. Provide Analytics interface in MainModule
    5. Add Firebase Core Dependencies
    6. Create FirebaseAnalytics
    7. Replace MixPanel Analytics with FirebaseAnalytics
    8. Replace Mixpanel Analytics tracking with Analytics tracking in Presenters
    9. Run app
    10. Check logs
    11. Continue with *Firebase Auth*, *Firebase Crash*, *Firebase FCM*, *Firebase Remote Config*, *Firebase Cloud Test Lab*


## Considerations

    * If you get stuck, `migration-finish` branch contains the whole migration finished
    * Make sure that `google-services.json` file is pointing to your Firebase project


#License

Copyright 2016 David Gonzalez

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[karumilogo]: https://cloud.githubusercontent.com/assets/858090/11626547/e5a1dc66-9ce3-11e5-908d-537e07e82090.png
[espresso]: https://google.github.io/android-testing-support-library/docs/
[dagger2]: http://google.github.io/dagger/
[testDoubles]: http://www.martinfowler.com/bliki/TestDouble.html
[applicationScreencast]: ./art/ApplicationScreencast.gif
[mainActivityScreenshot]: ./art/MainActivityScreenshot.png
[superHeroDetailActivityScreenshot]: ./art/SuperHeroDetailActivityScreenshot.png
[androidTestingDocumentation]: https://google.github.io/android-testing-support-library
[espressoCheatSheet]: https://google.github.io/android-testing-support-library/docs/espresso/cheatsheet/index.html
[espressoIdlingResources]: http://dev.jimdo.com/2014/05/09/wait-for-it-a-deep-dive-into-espresso-s-idling-resources/
[espressoCustomMatchers]: http://blog.xebia.com/android-custom-matchers-in-espresso/
[findingUIViews]: http://www.adavis.info/2015/12/testing-tricks-2-finding-ui-views.html?utm_source=Android+Weekly&utm_campaign=9ed0cecaff-Android_Weekly_186&utm_medium=email&utm_term=0_4eb677ad19-9ed0cecaff-337845529
[toolbarMatcher]: http://blog.sqisland.com/2015/05/espresso-match-toolbar-title.html
[daggermock]: https://github.com/fabioCollini/DaggerMock

