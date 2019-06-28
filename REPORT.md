# Maplife2 final report

## description

Maplife is an app with which you can safe important places in your life, places you have been on holiday or places you just want to remember for later. Every user has his/her own account and through making friendships on the app the map with places of other users can be viewed. The philosophy of this app is to safe the best places in your life for yourself, if you want to revisit some day or to share them with your friends.

![A Friends Map](FriendsMap.jpg)
![A friends location](FriendsMapView.jpg)

## technical design

![final design](endDesign.png)

## functionality by classes. 

The project exists of 10 activities, 8 helpers and 3 classes.

### activities

#### Loginactivity
this activity verifies that the username typed in exists and the typedin password matches with the password of the user saved in the database. For this it calls the loginDataGetRequest. Upon succesfull login it starts up the mainActivity, the loggedIn ID is passed on to the new activity. If a new user starts the app, he can press the register button to get to the SigninActivity.

#### SigninActivity
this activity has editText fields in which the new user can type his register information. This information is send to the UserPostRequest, which uploads it in the database. Hereafter, IdGetRequestByEmail is called, to retreive the created ID of the new user. Upon succesfulll registration the mainActivity is started and the loggedIn ID is passed on.

#### StartActivity
This activity starts up when the app is initialized. It sends an intent to the mainactivity with a Boolean: loginChecker, to let this activity know that no user has logged In yet. The StartActivity has no layout.

#### MainActivity
This activity is the central activity of the app. It checks if a user is loggedin, and shows the locationsMap of the loggedinUser. The information necessary for the creation of this map is retreived with the help of the UserGetRequest. The activity has a drawerLayout, which has onclickItems to the FriendsViewActivity, LocationsViewActivity,  AddFriendsActivity, AddLocationActivity, AccountActivity and LoginActivity. 

#### FriendsViewActivity
This activity uses the FriendsAdapter helper class to show a grid view of all the friends of the person logged in. By clicking the floating action button the user is redirected towards the AddFriendsActivity, by clicking on one of the friends in the grid, the user is redirected towards the FriendMapsActivity.

#### FriendMapsActivity
This activity shows the map of the friend clicked in the FriendsViewActivity. The map contains markers on every of this friends locations. When clicked on one of the markers a dialog pops up with more information about the location. 

#### LocationsViewActivity
This activity show the users own map. The map contains markers on every of the users locations. When clicked on one of the markers a dialog pops up with more information about the location. 

#### AddFriendsActivity
This Activity uses the FriendsAdapter helper class to show a grid view of all the users of the app. The Activity can be accessed from both the MainActivity and the FriendsViewActivity. When clicked on one of the names, a dialog pops up to verify that the user means to add this friend to his/her friendslist. When confirmed, FriendsPutRequest is called and the user is redirected to the FriendsViewActivity. 

#### AddLocationsActivity
This Activity shows the users map with all the locations on it. By longclicking on a point on the map the user can add a location. After the longclick a dialog pops up, asking the user for the name and a description of the place. When confirmed, LocationPutRequest is called and the user is redirected to MainActivity.

#### AccountActivity
This activity gives a more detailed overview of the users account information. 

### helpers

#### FriendsAdapter
This helper class creates a convertView for Friends to be shown in a grid. It is called in the AddFriendsActivity and the FriendsViewActivity.

#### FriendsPutRequest
This helper class makes a put request to put a Friend in the Rester database. It is called in the AddFriendsActivity.

#### IdGetRequestbyEmail
This helper class makes a get request to get an ID from the database with a specific email adress as the key. It is called in the SigninActivity.

#### LocationPutRequest
This helper class makes a put request to put a Location in the Rester Database. It is called in the addLocationActivity.

#### LoginDataGetRequest
This helper class makes a get request to get a name, a id and a password from the database for verification in the login process. It is called in the LoginActivity.

#### PossibleFriendsGetRequest
This helper class makes a get request to get all users from the database and safe them as friend objects: with a name and an ID. It is called in the AddFriendsActivity.

#### UserGetRequest
This helper class makes a get request to get all the up-to-date information of the current user. It is called in MainActivity and the FriendMapsViewActivity.

#### UserPostRequest
This helper class makes a post request to post the information of a newly subscribed user to the rester database. it is called in the SigninActivity.

### classes

#### Friend
The Friend class has two atributes: an ID and a name.

#### Location
The Location class has four atributes: latitude, longitude, name and description. 

#### User
The User class has six atributes: an ID, emailadress, name, password, a list of locations and a list of friends.

## Chalenges and Changes

In this project some key aspects of the initial idea have been executed differently in the final product. First of all, due to difficulties with the Firebase database, the database of the final project is not firebase, as mentioned in the proposal document, but rester. The second big change is the use of the google maps API instead of the MapBox API to load the maps in the app. Furthermore, some intended functionalities have been canceled due to lack of time, such as: the possibility to sort the users locations into categories and the possibility to view the locations of multiple users in one map.

### MapBox

Although in the project proposal states that the Google Maps API was going to be used for rendering the map in this project, in the first days of research the idea arose to use MapBox API for this. The reason for this was, at the time, because MapBox allows more views per year for free. In hindsight, this amount of views was never going to be reached, so the google Maps API would have been the better API to start with. I namely, had some trouble getting the MapBox map working, and the whole first week I was only busy with this. When I did not have a functional map in the second week, I decided to switch to Google Maps API. this one turned out to be way easier to work with and I got it working in the first day. 

### Firebase

After the problems with MapBox I decided not to continue trying to safe data in Firebase. Since, like MapBox, this did not work smoothly with me, and I already lost quite some time with MapBox, I decided to use a database that I knew: Rester. Altough this database has some drawbacks in comparison to firebase (such as always having to host it yourself), I decided that I could use the remaining time more effectively than trying to get the database working.

