# Zebra Notification controller

[![N|Solid](https://upload.wikimedia.org/wikipedia/en/f/f2/Zebra_Technologies_logo.svg)](https://nodesource.com/products/nsolid)

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Notification controller for controlling Android Notification from Enterprise Managed config interface
Notification controller majorly constitutes of an
- Android Foreground service
- Managed config interface 

### Android Foreground service
This service intercepts the notifications after binding with System NotificationListener service. This requires 
**android.permission.BIND_NOTIFICATION_LISTENER_SERVICE** to be granted.Right now Zebra MX doesnt provide a silent way of granting this permssion so that we had provided an navigation interface in landing activity for granting this permission.

Once Launched this service will re-launch itself on every reboot and start monitoring Notifications (unless disabled from Managed Config)

|  ![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/Screenshot_20210525-135854.png)|![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/Screenshot_20210525-135917.png)  | ![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/Screenshot_20210525-140013.png) |
|--|--|--|
| ![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/Screenshot_20210525-140124.png) | ![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/Screenshot_20210525-140207.png) | ![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/Screenshot_20210525-140223.png) |

### Managed Config interface
Notification service exposes an fully configurable admin Managed configuration, using them an EMM admin can remotely control every aspect of Notification management in Android.

***Managed Configs***

 - Enable Disable Service
 - Mode (Whitelist / Blacklist)
 - Whitelist Rules
 - Blacklist Rules

***Rules***

Blocking or Allowing notifications are based on Rules, There can be multiple rules for a given Mode (WL/BL). Rule defines the criteria under which each Notification is evaluated to be qualified.
| Field |Type  |Meaning  |
|--|--|--|
|Package Name  | String | Package name of Notification issuer |
|Channel-id  | String | Channel on which Notification is issued |
|Title | String | Title of Notification |
|Text | String | Text of Notification |
|Sub Text | String | Sub-text of Notification |

***Blacklist Mode***
if this mode is selected and rules are defined under Blacklist rules then Notifications are blocked if it matches with any of the defined blacklisted rules.
*eg. Blacklist all the notification from App Install / update*

 **- *rule 1: block Install app notifications***
 
    Channel-Id : DEVICE_ADMIN_ALERTS
 
    Text : Installed by your admin
 
**- *rule 2: block update app notifications***

    Channel-Id : DEVICE_ADMIN_ALERT
    
    Text : Updated by your admin

***Whitelist Mode***
if this mode is selected and rules are defined under WhiteList rules then only those Notifications are whitelisted which passed through all of the defined Whitelist rules.
*eg. Whitelist all the notification which passes below rules*

 **- *rule 1: Allow Notifications from com.google.android.packageinstaller*** 
 
    Package Name : com.google.android.packageinstaller
 
 **- *rule 2: Allow Notifications from Device Admin***
 
    Channel-Id : DEVICE_ADMIN_ALERT
    

| ![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/PlayStore.PNG) |![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/ManagedConfig.PNG)  |
|--|--|
|  ![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/BlackList.PNG)| ![enter image description here](https://storage.googleapis.com/keyattestationserver.appspot.com/Whitelist.PNG) |



