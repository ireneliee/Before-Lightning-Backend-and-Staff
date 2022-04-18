# beforeLightningBackend
BeforeLightningBackend is a part of Before Lightning Enterprise, an enterprise system created to facilitate customizable laptops and simple accessories online sales.  The enterprise system consists of two web-app, one for BeforeLightning Staff to handle administration matters such as handling purchase orders, regulating forum posts, etc. 
The backend and staff portal are in this repository, while the customer portal source code would be in the other repository: BeforeLightningAngular. 
Tech stack used in this repository is Jakarta EE and JSF. 
To know more about our project, please take a look at the thorough documentation the team has prepared. This documentation would have been included inside this repository. 

Instructions to Deploy:
1. start MySQL80 in your services
2. create database and name it beforelightningbackend on netbeans
3. connect to it
4. ensure that you have primefaces 8 library, jasperreport, JSF community theme bluesky, Mimepull and Jersey Media (provided in source folder under java libraries)
5. unzip uploadedFiles in your docroot (provided in source folder as well)
6. clean build and deploy and ensure that the tables are generated and data is populated in the tables
7. go to visual studio
8. npm install
9. ng serve
10. now go to your web browser and enter the appropriate links to access each app
localhost8080 for our jsf application
localhost4200 for our angular application
both apps should be working fine from this point.

A full demo video of this enterprise system can be seen here: https://www.youtube.com/watch?v=k5CZJt5hBnY
