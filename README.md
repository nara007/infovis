How to start the Server:
- use Eclipse Java EE IDE for Web Developers
- set JDK 1.7 (Not JRE 1.7!!!) as default runtime environment
- use "maven update" and "maven build" (clean test)
- run the Main.java as Java application

-> Test the example project in your browser by typing:
http://localhost:8080/myapp/myresource

-> For more information about the test project visit:
https://jersey.java.net/documentation/latest/getting-started.html


================

jetty:run

This starts Jetty and serves up the project on http://localhost:8080/.
Jetty continues to run until you stop it. While it runs, it periodically scans for changes to your project files, so if you save changes and recompile your class files, Jetty redeploys your webapp, and you can instantly test the changes you just made.

You can terminate the plugin with in the terminal window where it is running.


