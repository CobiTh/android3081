# android3081
android project for 3081 (real)


## The main idea: 

- letterboxd but for trips
- allows user to locally create trips that can then be seen by other people who are following the account

## what we should do for this project
- no maps integration, users should be able to sign into their account (use firebase sql, free at small scale)
- be able to create trips and give trips different events
- eventFactory that creates different events (i.e beach, park, theme park, etc)
- be able to see your past trips and trips of people you follow in another screen 

## stretch goals 
- if users look up an event that another user has been to, first show friends review of said event and then anonymous other reviews from people you don't follow
- "suggestions" tab that allows people to put suggestions on events for others who may go there (what they should do there, rather then a review)
- security features like allowing users to ensure that trips are locally saved on their device until completion of trip, user should be able to put things into their trips that only they can see like photos they don't want to share with others but want to link to the trip

## it would be crazy if we could do this
- use google maps api/locations api to ensure that events are matched with a location rather then just a name, would make it easier to link events to other people who have done them since others may spell events differently 

## what I'm kind of thinking

user creates a new trip -> homepage of app on sign in switches from a "start a trip" tab to a persistent "add event" tab that also lets them end trip 

buttons on this should be: 
add a new pin -> lets user go back later and fill out event 
add a new event -> lets user review event (out of 5 stars and with optional text) and add pictures
(maybe) suggestions -> first shows suggestions for guests location, then can be adjusted to show suggestions regarding a more specficic event 
end trip -> finishes trip, if user has opted out of live updating trip sends whole trip to server (if they have opted into live updating send events, otherwise events are saved on device storage and deleted now after sending)


if you have other ideas go crazy just let me know :D