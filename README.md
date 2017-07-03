# Demo REST CRUD Android Application

Demo android application using mock web service JSONPlaceholder

JSONPlaceholder is a free online REST service that you can use whenever you need some fake data.

This project implements the following functionalities:-

1. Create a UI to display a list of users obtained from the web service (GET http://jsonplaceholder.typicode.com/users). You should show username and full address for each user. The app should query the service for a new list of users each time it is launched.

2. Add functionality so that the user can select a user from the list and view all posts by that user (GET http://jsonplaceholder.typicode.com/posts?userId={userId}). You should show the title and body for each post.

3. Add functionality so that the user can create a new post by the user they have selected (POST http://jsonplaceholder.typicode.com/posts).

4. Add functionality so that the user can edit or delete a post (PUT or PATCH, DELETE http://jsonplaceholder.typicode.com/posts/{postId}).

5. Add functionality to allow the user to sort the list of posts in ascending and descending alphabetical order by title.
