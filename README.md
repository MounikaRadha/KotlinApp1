# kotlin app 
## layman description
- save emergency contacts
- save incidents like heart attack,faint,kidnap
- in needy time click the relavant image and the app sends sms to the saved contacts
## explanation of project
- the main activity(after launching of app) has a frame layout and 2 buttons
- in frame layout the necessary fragments will be loaded based on the click of buttons
- the frame by default will load fragment which has a button to take users to about page
- if user clicks start page button a new fragment will be loaded which takes user to save contact numbers and incidents
- there for each saving part the 1st half takes the input and saves in data stroe
- the second half from data store fetches the data and shows them in a list view
- in data store we need to store in key value pair so the level number say 1,2,3 will be saved as keys
- to fetch the contact numbers we iterate over range 1-3 and get contact numbers to display and as well to send msg in later part
- in the same way incidents as well will be saved for 1-3
- the saving part is in activities as i dont want to show the button which does not give clear view so did not used a fragement for saving part screen
- coming to the part of click the button it retrives from datastore top 3 incidents
- it fetches 4 images from resources images are used as it will easy for users to click rather than searchign for text
- used an adater for using images and mappign them to the incidents list and used a grid view for this screen
- on user click the current version will loop over contacts and sms to all along with latitude and longitude but  need to figure out why they are not of india but of america
## additional
- https://www.amazon.com/dp/B0DNX597Z8/ref=apps_sf_sta
- ![image1](https://github.com/user-attachments/assets/0e77a330-094d-4a9c-9c7a-b87f83dd630c)
- ![image2](https://github.com/user-attachments/assets/9553f890-a4e0-4f12-84cf-eb7a09a6db4c)
- ![image3](https://github.com/user-attachments/assets/6241490e-6f67-45fb-8bec-b1489ac75bf3)
- ![image4](https://github.com/user-attachments/assets/1e121432-5308-4d41-a5cf-500ae113d730)
- ![image5](https://github.com/user-attachments/assets/776fddcb-e140-4b89-bd85-74f544d6457f)
## video of recording


https://github.com/user-attachments/assets/3951ae5b-5c96-488f-8fe0-2b0b53f503ff






