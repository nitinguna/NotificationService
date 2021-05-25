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



