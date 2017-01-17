# geolog
IP geolocation for SSH brute force attacks.

# features
- continuous ssh auth log parsing for failed login attempts
- notify successful attempts via email as potetial breaches
- basic charts with the origin countries and cities

# credits
- https://bl.ocks.org/mbostock/7555321
- http://d3pie.org/
- http://ip-api.com/

# limitations
There is a limitation for the number of IPs than can be geocoded by http://ip-api.com/. 150 requests/minute. The application takes this into consideration and if there are more than 150 attempts per minute, it will queue them and send them in the next minute. This is configurable in `application.properties`, in case you get the pro version of ip-api :)
Currently works on Ubuntu and other systems having ssh auth log in `/var/log/auth.log`. Changing this is trivial.

# emails
In order to get emails for potential breaches you need to configure a username and password in case you plan to use Gmail, or full details including smtp host and port for other services. You must also provite a `notify.to` email - this is where the emails are being sent.

# demo
http://vassal.dynu.com:6061/

# build and run
This is a spring boot application. The packaging outputs an executable jar file that can be also run as a Linux script. In order to build and deploy it:
- build: `mvn clean package`
- deploy: go to target and just run `/geolog`

You can also deploy it as a Linux system service:
- after you build the application do: `ln -s PATH_TO_GEOLOG_FROM_TARGET /etc/init.d/geolog`
- restart systemctl: `systemctl daemon-reload`
- start the service: `sudo service geolog start`
- you can find the log files under: `/var/log/geolog.log`

By default the application starts on port 6060. You can change it in `application.properties`.
