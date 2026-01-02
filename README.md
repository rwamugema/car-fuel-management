# car-fuel-management
This project consists of a **Spring Boot backend** and a **CLI client** to interact with it.  

## Requirements

- Java 17+  
- Maven 3+  

---

## 1️⃣ Clone the repository

```bash
git clone https://github.com/rwamugema/car-fuel-management.git
cd car-fuel-management
````

---

## 2️⃣ Build the project

From the root folder, run:

```bash
mvn clean install
```

This will build both the backend and CLI modules and install dependencies.

---

## 3️⃣ Run the Backend

You can run the backend without going inside the `backend` folder:

```bash
mvn -f backend/pom.xml spring-boot:run
```

The backend will start on `http://localhost:8080` by default.

---

## 4️⃣ Run the CLI

Open a new terminal (backend can keep running) and navigate to the CLI folder:

```bash
cd cli
```

Run commands using the built CLI JAR:

```bash
# Using the packaged JAR
java -jar target/cli.jar add-fuel --carId 1 --liters 20 --price 1000 --odometer 5000

```

> ⚠️ Make sure the backend is running before using the CLI.

---

## 5️⃣ Example Commands

* **Create a car**

```bash
java -jar target/cli-1.0-SNAPSHOT.jar create-car --brand Toyota --model BMW --year 2019
```
* **Add a new fuel entry**

```bash
java -jar target/cli-1.0-SNAPSHOT.jar add-fuel --carId 2 --liters 40 --price 2000 --odometer 2000
```
* **View fuel stats of a car**

```bash
java -jar target/cli-1.0-SNAPSHOT.jar fuel-stats --carId 1 
```
---


