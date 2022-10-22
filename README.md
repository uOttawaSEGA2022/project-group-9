# project-group-9 Mealer App

Important Notes For Marking:
We were facing an SDK error each time someone pulls the project from the GitHub repo, we couldn't find a final solution to it yet. However, there's a local fix:
In the file gradle.properties, add the following line of code:
For Windows: sdk.dir=C:\\users\\Username\\AppData\\Local\\Android\\sdk
For Mac: sdk.dir = /users/Username/Library/Android/sdk
And replace Username as the local user name on the local machine and the error should be fixed

We followed this guide to get the solution: https://www.geeksforgeeks.org/how-to-fix-sdk-location-not-found-in-android-studio/
