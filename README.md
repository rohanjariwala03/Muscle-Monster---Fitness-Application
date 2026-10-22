# Muscle Monster

An Android fitness app for a gym, written in Java with Firebase as the backend. I built it with three teammates as our final year project between June and August 2021.

The app has three kinds of accounts. After login it checks which Realtime Database node the email belongs to (`Users`, `Trainer` or `Admin`) and opens the matching home screen.

## Members

- Browse exercises by category, read descriptions and watch exercise videos (ExoPlayer).
- Run a workout timer, save workouts and see workout history.
- Shop for products, apply a coupon and check out with a card form. The card form only validates input; there is no real payment, the product is just marked as sold to the buyer.
- List your own products for sale with photos, edit them, and see what you've sold and bought.
- Search trainers, view their details, chat with them in real time, book an appointment on a calendar and rate them.
- View gym packages and financing plans, see your coupons, and send feedback.

## Trainers

- Chat with members and view their details.
- See booked appointments and the ratings members have given.
- Update their profile.

## Admin

- Add and view trainers, view members and give a member a discount coupon (valid for 6 months, single use).
- Add, edit and delete shop products, and see sold and purchased items.
- Manage gym packages.
- Read member feedback.

## Stack

- Java 8, Android SDK 30 (min SDK 16), Android Gradle Plugin 4.2.2, Gradle 6.7.1
- Firebase Authentication, Realtime Database and Storage, FirebaseUI Database
- Glide for images, ExoPlayer r2.4.0 for video
- Braintree `card-form` for the checkout form
- `meow-bottom-navigation`, `compact-calendar-view`, Dexter for runtime permissions

## Running it

1. Open the project in Android Studio. It was last built with the 2021 toolchain listed above, so a current Android Studio will likely ask to upgrade Gradle and some dependencies (the build uses the old `compile` configuration and `jcenter()`).
2. Create your own Firebase project, enable Email/Password sign-in, Realtime Database and Storage, and replace `app/google-services.json` with the file for your project.
3. Run the `app` configuration on a device or emulator.

Login only lets in emails that exist under one of the `Users`, `Trainer` or `Admin` nodes. Members can sign up from the app and trainers are added by the admin, but there is no screen to create an admin, so add one to the `Admin` node by hand in the Firebase console.

## Project layout

All source is under `app/src/main/java/com/example/musclemonster_fitnessapp/`:

- `LoginSignUp/` login, sign up, forgot password
- `BottomBarFragments/` the member home tabs: exercises, shop, chat, more
- `MoreMenuClasses/` everything behind the member "More" menu
- `Trainer/` trainer screens
- `Admin/` and `Admin_Home_Activity.java` admin screens
- `AdapterClasses/`, `POJOClasses/` RecyclerView adapters and data models
