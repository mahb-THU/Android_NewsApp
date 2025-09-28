# 2023 Summer Java Short-Term Project Report

## This code is for the THU 2023 Summer Term Java Project

## 1 Code Structure

### 1.1 Overall Structure

My Java code is mainly divided into the following parts: **adapter, api, entity, fragment, model, signup, utils, view, activity**.
The XML code is mainly divided into **layout, drawable, xml, and values**.

### 1.2 Functionality of Each Module

#### 1.2.1 Java Part

##### 1.2.1.1 Function of `adapter`

The `adapter` serves as a bridge in my code, functioning as an adapter that receives data in the form of `List/ArrayList` and renders it onto the UI interface.
In other words, the adapter connects data-processing logic and the graphical interface.
My adapters include `CollectAdapter`, `NewsAdapter`, and `ReadAdapter`, which are responsible for rendering the homepage news list, search results list, browsing history list, and favorites list.

##### 1.2.1.2 Function of `api`

The `api` folder contains two files: `Api` and `ApiInterface`. These implement a simple **Retrofit-based network request framework**. Using Retrofit, I implemented:

* retrieving data from API endpoints,
* converting JSON data,
* handling multithreading in network requests.

##### 1.2.1.3 Function of `entity`

The `entity` folder only contains one file, `tab`. Its main function is to serve as a supplementary component for the `TabLayout` UI control, improving the visual effect.

##### 1.2.1.4 Function of `fragment`

The `fragment` folder contains `Categories`, `Home`, `Mine`, and `News`.
Fragments are UI components embedded in activities. They play a role similar to activities, connecting with front-end files and implementing logical methods.

##### 1.2.1.5 Function of `model`

The `model` folder contains two files: `NewsData` and `NewsWhole`.

* `NewsWhole` is designed for Retrofit usage: Retrofit parses JSON into `NewsWhole` objects so that data can be passed and used within the app.
* `NewsData` is a subclass contained in `NewsWhole`. It includes the data used across different files in the program and enables data transfer between modules.

##### 1.2.1.6 Function of `signup`

The `signup` folder includes `Login`, `MyDatabaseHelper`, `Register`, and `Welcome`.
This folder contains three activities related to login/registration and one class for database access, implementing user authentication pages and account/password storage.

##### 1.2.1.7 Function of `view`

The `view` folder contains only one file, `FixedPager`.
It extends the native `ViewPager` class, removing unwanted scrolling animations during page transitions, thereby improving the user experience.

##### 1.2.1.8 Activities after login

This part implements the **main body of the news app**, which will be described in detail in Section 2.

#### 1.2.2 res Part

##### 1.2.2.1 Function of `drawable`

Stores image resources and XML style/color files, used for UI design and aesthetics.

##### 1.2.2.2 Function of `layout`

Contains layout files for each page, using components and frameworks to define UI layouts.

##### 1.2.2.3 Function of `values`

Stores style-related files such as font specifications. It helps enforce consistency and reduces redundant code.

##### 1.2.2.4 Function of `xml` (folder)

Contains XML files for specifications and permissions, serving as supplementary configurations for `AndroidManifest`.

### 1.3 Common Patterns Among Java Files

For all `activity`/`fragment`-related files, the logic is consistent:

* `initLayout` → initialize layout
* `initView` → render layout and UI components
* `initData` → fetch and process data

This common pattern improves **code structure, reusability, and readability**.

### 1.4 Summary of Overall Logic

This section can be seen both as a summary of Part 1 and as the beginning of Part 2.

The overall implementation of my news app is as follows:

* **Login + Registration + Welcome** pages
* **Homepage** (with two sections: *Home* and *Mine*)

  * *Mine*: browsing history, favorites, and canceling favorites
  * *Home*: integrated search, categories, news list
  * Clicking a news item opens a **news detail page** with text, images, video playback, and favorites

This is the overall structure and logic of my project.

---

## 2 Implementation Details

### 2.0 Declaration

All the functionalities required in the assignment documentation have been fully implemented and tested locally.
Additionally, I implemented some **extra features**. Below I describe them feature by feature.

### 2.1 Implementation of Required Features

#### 2.1.1 News List

##### 2.1.1.1 Display news list and open detail page

The app’s overall architecture uses `FixedPager` (a subclass of ViewPager) and `CommonTabLayout` (a subclass of TabLayout).

* They form the outer structure of *Home* and *Mine*.
* For category sliding in *Home*, I combined `SlidingTabLayout` and `FixedPager` to link top navigation tabs with news lists.
* News lists are implemented with `RecyclerView`.

For backend data requests, I used **Retrofit**, handling:

* async network requests,
* JSON parsing,
* passing parsed data to adapters for UI rendering.

Finally, I used `Intent` and `Bundle` to carry data into the news detail page.

##### 2.1.1.2 Pull-to-refresh and load more

Based on `RecyclerView`, I used `SmartRefreshLayout` to implement pull-to-refresh and load-more.

* Refresh actions request news from APIs again.
* Visual effects: `BezierRadar` for pull-down, `Classics` for pull-up.

##### 2.1.1.3 Search news by keyword, category, and time

Since the course provided search APIs, I simply retrieved user input from `EditView`, passed it into my news-fetching method, and reused the adapter to render search results.

#### 2.1.2 Categories

##### 2.1.2.1 Add/Delete categories with animation

* Implemented sliding categories with `SlidingTabLayout` + `FixedPager`.
* Adding/deleting categories uses `BottomSheetDialogFragment` (pop-up from bottom).
* Category management UI uses `TabFlowLayout` for fluid layouts.
* Animated effects were inspired by *Toutiao*: two list layouts on both sides of `BottomSheetDialogFragment` allow category migration with flashing animations.

#### 2.1.3 News Detail

##### 2.1.3.1 Show source and time

Data was passed from adapter to activity via `Intent` and `Bundle`, then rendered in the detail page UI.

##### 2.1.3.2 Video playback with proper interaction

* Used Android’s native `VideoView`.
* Converted URL to `Uri` to play video.
* Used `MediaController` for play/pause and 15s skip forward/back.
* Important: correct app permissions are required, including `ACCESS_NETWORK_STATE` and custom security settings.

#### 2.1.4 Local Records

##### 2.1.4.1 Local storage of viewed news, offline access, and gray marking

* Used SQLite for local database storage.
* When viewing a news item, its data is inserted into the DB.
* If offline, news can still be retrieved from DB.
* Viewed news is marked gray by checking existing entries in DB.

##### 2.1.4.2 Store browsing and favorites history

Similar rendering as the news list, but data is retrieved from SQLite rather than API.

### 2.2 Extra Features

#### 2.2.1 Login/Registration pages

Implemented user info management with DB, added a welcome screen for better user experience and attractiveness.

#### 2.2.2 Custom fonts

Applied custom fonts (e.g., *Xingkai* style) using `Typeface`, making the UI more aesthetic.

