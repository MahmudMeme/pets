### **📚 Pet-Shop REST API**

This project is a Spring Boot-based REST API for managing pet adoptions. It provides endpoints for creating pets and users, listing pets and users, and handling the adoption/purchase process between users and pets.
---  

## **🌟 Features**

✅ **Pet Management**:
- Create individual pets.
- Create multiple pets at once.
- List all available pets

✅ **User Management**:
- Create users
- List all users
- Handle pet adoption transactions.
- Track user budgets.

✅ **Adoption Process**:
- Automatic price calculation based on pet type and age**.
- Budget validation.
- Ownership tracking

---  

## **🛠️ API Documentation**

### **BASE URL 🎨**
- http://localhost:9111/api ⚛️


---  

## **📌Endpoints**
-  /pets/create - Response: Returns a randomly generated pet
- /pets/all - Response: Returns list of all pets as DTOs
- /pets/create-pets/{number?} - Parameter:
number (optional): Number of pets to create (default: 20, max: 20)
  Response: List of created pets

-  /users/create - Response: Returns a randomly generated user
- /users/all - Response: Returns list of all users as DTOs
- /users/create-users/{number?} - Parameter:
  number (optional): Number of users to create (default: 20, max: 20)
  Response: List of created users
- /user/buy - Response: Returns list of all users that bought at least one pet. If there is user or pet in the database it trows an exception with message to tell the user what is missing in the database

- /history - Response: Returns list od all purchases in the store - for each item returns the time and date of purchase number of users that purchased at least one pet and number of users that didn't buy any pets

---  

## **API Testing**
to test all endpoint and business logic use tool like Postman or extension on web browser 

**Unit Tests:**

 **Repository**: it tests all necessary method that are used for the application  
 **Service**: it test all business logic of the app  
 **Controller**  it tests all endpoint of the app.  


---  

## **🛠️ Setup**
to be able to run the application you will need Java 17+ and Maven 3.6+
- clone or download and unzip
- open the pom.xml file and load the maven project
- build and start

---  
