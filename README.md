# Otter

**Otter** is an application that demonstrates fundamental Android development principles. 
This project is designed to be accessible to beginners to Android with Java experience, and to showcase Android best practices
with minimal dependencies.

## Features
* Model-View-ViewModel architecture
* Android Architecture Components:
    * ViewModel
    * Room
    * LiveData
* Android components:
    * Activities
    * Fragments
    * Services
    * Broadcast Receivers
* CoordinatorLayout
* Repository pattern

## Architecture
Otter uses a Model-View-ViewModel architecture (MVVM). `MainActivity` is the main entry point to the application; its only concern is serving fragments 
and handling fragment callbacks. `AlarmFragment` displays a LiveData list of alarms provided by `AlarmViewModel`. The viewmodel fetches the user's alarms 
from `AlarmRepository`, which provides an interface to the database via Room.

When the FAB in `AlarmFragment` is pressed, fragments are queued to display various dialogs to create a new alarm object. When a fragment is destroyed, 
a callback is sent to `MainActivity` to manage the alarm object and pass data between fragments. When the object is created, the viewmodel is updated with 
the new data, which also creates a new system alarm in `BroadcastRepository` using the alarm's data.

`AlarmBroadcastRepository` listens for updates to system alarms, and calls `NotificationService` to display a notification when one occurs. `ActionReceiver`
allows the notification to perform actions to snooze and dismiss alarms. `OnBootReceiver` and `AlarmReinitWorker` work together to restore system alarms when 
the device is rebooted.

`AlarmActivity` acts as a secondary entry point to the application, and is activated by clicking an alarm notification. It displays data from the provided 
alarm, and can perform actions to snooze and dismiss as well. Exiting the activity returns the user to `MainActivity`.

## To Do
* Move responsibility of fragment callbacks to `AlarmFragment`

## Contributing
* Bug reports and contributions are always welcome. Please use this guide when making a PR:
    * Add an issue in the tracker linked to the PR
    * New features should match the existing style, including use of Java and minimal external dependencies
