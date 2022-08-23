# NetworkingApp
0. In your Internet browser open https://randomuser.me/api and inspect connection to the site: see certificate details, inspect request and response headers, see json response body (its structure and used basic json types). Guess what should be a class hierarchy to produce such a json on serialization. Add to url query parameters "results" and "page" to request 2nd page with 3 results per page.

Create an application with a single activity with a button.

1. When the button is pressed, make a request to https://randomuser.me/api with OkHttp client. Using HttpUrl.Builder add to the request the query parameters from above.

2. Deserialize response and:
a) diplay first names of all the users obtained in the response;
b) display some details for the first user in the list, diplay the user photo.
No need to implement full hierarchy to deserialize the json completely, use just some of its attributes.

Optionally you can use Retrofit for the networking.

3. If an error occurs, show a dialog with some explanation: the response status name or exception message, tell user to turn on Internet connection if there is no connection. See how your app behaves if the Airplane mode is on or if you use wrong name of the query parameter.

4. Write and add to the OkHttp client an interceptor which logs to Logcat: url, status, any 2 headers.

5. Use Android Studio Network profiler to inspect details of the app networking activity.
