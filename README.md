# CouponSystem_SpringProject_SessionTechnique

This application is the final project I created during my software studies.<br/>
The application is a Fullstack project, it is written in Java language on the server side and in React on the client side.

### 🤔 What is the purpose of this application?
This is a coupon management system that allows companies to generate coupons as part of advertising and marketing campaigns that they run.<br/>
The system also has registered customers, the customers can purchase coupons, coupons are limited in quantity and validity, a customer is limited to one coupon of each type.<br/>
The system records the coupons purchased by each customer.<br/>
The revenue of the system is from the purchase of coupons by the customers and by the creation of new coupons by the companies.

### 💻 Access to the system is divided into three types of clients:
1. Administrator - manages the system, managing the lists of companies and the customers.<br/>
2. Company – managing a list of coupons associated with the company.<br/>
3. Customer - who buy coupons.<br/>

# The reason I created 3 versions of the server side
Since I always had a great passion and a special connection while working on the server side, during the software studying, I decided to do the server side in 3 different ways, each of them focusing on a different technological technique.

# Now, let's dive into the 3 types of projects I created

## 1️⃣ The 1st Project: Java
Writing the server side in plain old Java.<br/>
For the database I used:<br/>
-	SQL
-	JDBC
-	ConnectionPool that manage the connections queries sent to the database.<br/>

In this project I used simple login() method for authentication & authorization.<br/><br/>

Click here to see this project on Github:<br/>
https://github.com/itsmechelly/CouponSystem_JavaProject 

## 2️⃣ The 2nd Project: Spring Framework and Session Technique for Authentication & Authorization
In the second project I rewrite the core system for more recent technology - I used Java language and Spring Framework.<br/>
For the database I used:<br/>
-	Spring Hibernate JPA.<br/>

For the authentication & authorization I used the Sessions technique.<br/><br/>

👉 The current repo represent this 2nd project.<br/>
https://github.com/itsmechelly/CouponSystem_SpringProject_SessionTechnique 

## 3️⃣ The 3ed Project: Spring Framework and JWT Technique for Authentication & Authorization
In the second project I rewrite the core system for more recent technology - I used Java language and Spring Framework.<br/>
For the database I used:<br/>
-	Spring Hibernate JPA.<br/>

For the authentication & authorization I used the JTW technique.<br/><br/>

Click here to see this project on Github:<br/>
https://github.com/itsmechelly/CouponSystem_SpringProject_JwtTechnique<br/><br/>
👉 NOTE: this project is the final version and deployed to AWS cloud, click to browse the website:<br/>
LINK WILL BE ADDED SOON
<br/><br/>
👉 To login, use those details:<br/>
Admin: ➡️ e-mail: admin@admin.com password: admin<br/>
Company: ➡️ e-mail: zootAllures@company.com password: zootAllures<br/>
Customer: ➡️ e-mail: cust1@cust.com password: 1111<br/>

# And what about the Client side?
This part of the application was written using React libraries and is built as a Single Page Application (SPA).<br/>
The communication between server side and client side was done using Json and RESTful API.<br/><br/>
Click here to see this project on Github:<br/>
https://github.com/itsmechelly/CouponSystem_ReactProject<br/><br/>
👉 NOTE: this project is the final version and deployed to AWS cloud, click to browse the website:<br/>
LINK WILL BE ADDED SOON
<br/><br/>
👉 To login, use those details:<br/>
Admin: ➡️ e-mail: admin@admin.com password: admin<br/>
Company: ➡️ e-mail: zootAllures@company.com password: zootAllures<br/>
Customer: ➡️ e-mail: cust1@cust.com password: 1111<br/>

# Application Architecture – 2nd Project
## Creating Java Beans that represent the Spring Entities in the database:
Java Beans are pure information classes that represent the information managed by the application.<br/>
Below is the diagram of the Java Beans classes:<br/><br/>
![image](https://user-images.githubusercontent.com/60425986/230063877-df50af27-b9fa-4a97-99b1-34fbface8b50.png)

## MVC Architecture:
As we will see below, this project implements an MVC architecture.<br/>
After the user performs a request on the client side, the first layer that is activated on the server side is the controller layer.<br/>

## The first controller – LoginController – manage Authentication & Authorization:
The first controller the client encounters is the login controller.<br/>
Login Controller calls to Login Service.<br/>
The login service contains a method that checks the type of the client, then, according to the client type, generates a new Session that contains a token valid for 30 minutes.<br/>
This created session refers to the specific service type of the client.<br/>
And then the service returns the generated token to the controller and inserts it into a new HttpHeaders object.<br/><br/>
Then, whenever a request comes from the client endpoint, the HttpHeader requests this token. In each such request - the system will check whether the token transferred in the header is indeed compatible with the real token found in Java's memory (this check is performed using the SessionContext class.<br/>
The token is automatically updated every 30 minutes.<br/>
This is how it looks in macro view:<br/><br/>
![image](https://user-images.githubusercontent.com/60425986/230064130-3c2665fb-eda5-4914-8beb-ef246c30e0b0.png)

<br/>
Diagram of login class in details:<br/><br/>


![image](https://user-images.githubusercontent.com/60425986/230067778-9bca18c3-4efb-46ad-8d80-16453f75f2ff.png)




## Now, when the user is logged in – other controllers can be in use:
According to the client type, and after the client's first request has been made - the login, and after the client has a token that is activated behind the scenes - now, with every request the client sends - the relevant controller will be activated (as mentioned, the token and the client type are stored in the HttpHeader).<br/>
The request is passed to the Service Layer into the specific method, which in turn checks whether the token is valid.<br/>
The service layer contains an operates the business logic and then passes the request to another layer I created - Impl Layer (I created this layer to create separation in access to the Repository layer - this creates extra protection).<br/>
And finally - there is a repository Layer that is managed by Spring Hibernate JPA and it is the one that adds, updates, deletes, and manages the information stored in the database.
<br/><br/>
A Facade Design Pattern is used here.<br/>
Such a design allows simple operations to be outsourced, which behind the scenes use a series of extra operations.<br/>
Because there is three classes need to use the service components, each by his client type - I defined an abstract base class – ClientService that reference to the service classes (it will first login() and then direct to the relevant service layer according to the client type).<br/><br/>
![image](https://user-images.githubusercontent.com/60425986/230064437-c4531927-a497-4d82-85b6-5d8cc64f1237.png)

## Building a daily job to delete expired coupons from the system (Spring Scheduling):
A basic infrastructure service named CouponExpirationDailyJob was established to clean the system from expired coupons.<br/>
The job is a process that runs in the background regularly and checks and cleans coupons once every 24 hours.<br/>
The job is executed using a Spring Scheduling that runs parallel to the system activity.<br/>
Below is the job diagram for this job activity:<br/><br/>
![image](https://user-images.githubusercontent.com/60425986/230064571-cf51c3f6-4751-4683-a63c-46d69a914ef3.png)

## Using Custom Exceptions:
In addition to the Java exceptions, I created system-specific exceptions:<br/><br/>
![image](https://user-images.githubusercontent.com/60425986/230064653-b2ecbf8a-a17a-4e83-9763-d8a274838451.png)

# Endpoints
-	To run this Application properly, you should add token to the HttpHader.

## Admin Controller:
http://localhost:8080/admin/addCompany 
<br/>
http://localhost:8080/admin/updateCompany 
<br/>
http://localhost:8080/admin/deleteCompany/{companyId} 
<br/>
http://localhost:8080/admin/getOneCompanyById/{companyId} 
<br/>
http://localhost:8080/admin/getAllCompanies 
<br/>
http://localhost:8080/admin/addCustomer 
<br/>
http://localhost:8080/admin/updateCustomer 
<br/>
http://localhost:8080/admin/deleteCustomer/{customerId} 
<br/>
http://localhost:8080/admin/getOneCustomerById/{customerId} 
<br/>
http://localhost:8080/admin/getAllCustomers 

## Company Controller:
http://localhost:8080/company/addCompanyCoupon 
<br/>
http://localhost:8080/company/updateCompanyCoupon 
<br/>
http://localhost:8080/company/deleteCompanyCoupon 
<br/>
http://localhost:8080/company/getAllCompaniesCoupons 
<br/>
http://localhost:8080/company/getAllCouponsByCategory/{couponCategory} 
<br/>
http://localhost:8080/company/getAllCouponsUnderMaxPrice 
<br/>
http://localhost:8080/company/getCompanyDetails 

## Customer Controller:
http://localhost:8080/customer/purchaseCoupon 
<br/>
http://localhost:8080/customer/getAllCustomerCoupons 
<br/>
http://localhost:8080/customer/getAllCouponsByCategory/{couponCategory} 
<br/>
http://localhost:8080/customer/getAllCouponsUnderMaxPrice 
<br/>
http://localhost:8080/customer/getCustomerDetails 

# ⚒️ Tech Stack
Language & Framework: Java Language, Spring Framework
<br/>
Database: Spring Hibernate, Spring JPA (MySQL Driver), SQL, MySQL
<br/>
Authentication & Authorization: Sessions Technique
<br/>
Scheduling Mechanisms: Spring Scheduling
<br/>
Architecture & Design Patterns: Spring MVC Layers, Singleton Pattern, Facade Pattern, Factory Pattern
<br/>
Communication between Client side and Server side: Restful API
<br/>
Client-Side: React, JavaScript, Typescript, Bootstrap 5, HTML, CSS
<br/>


<br/>
Thanks for reading,
<br/>
Chelly 👩🏻‍💻
