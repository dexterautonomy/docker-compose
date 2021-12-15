There are 3 ways to connect and use more than one containers
1. Use docker networking
2. Use container linking
3. Use docker compose

I will talk on (1) and (3) as (2) is now legacy and replaced using the docker compose

1. Docker Networking

To use docker networking, 
	a. Create your application e.g spring boot employee app that uses mysql db
		Therefore we will have two services/containers, the spring boot employee app and the mysql db.
		Create the employee spring boot app. using local mysql db settings. 
		Initially you would use your local mysql just so you can at least run and test that the app is 
		working fine. In doing so, you used datasource properties in your properties file like so:
		
			spring.datasource.url=jdbc:mysql://localhost:8004/employeedb?serverTimezone=UTC&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true
			spring.datasource.username=root
			spring.datasource.password=dexter
			
	b. Create a docker file for the spring boot app. 
		N/B: Do note that this configs are for local mysql and not for mysql image that you will 
			use in a container
	c. Your complete docker file for the employee app will look like so:
	
		FROM openjdk:8-jdk-alpine
		ARG JAR_FILE=target/employee-app.jar
		WORKDIR /opt/app
		COPY ${JAR_FILE} employee.jar
		ENTRYPOINT ["java","-jar","employee.jar"]
		
	d. Create a docker network using:
		-> docker network create -d bridge [network_name]
		where [network_name] is the name of the network to create therefore: 
		-> docker network create -d bridge mynetwork
	e. Pull a mysql image using:
		-> docker pull mysql
		This will pull the latest MySQL image from docker repository with name mysql:latest
	f. Run a container off the image and bind it to the network while specifying the environment 
		variables like so: [Do note that the environment variables are used to define initial 
		configs for the mysql databse instance like the connection URL, username and password]
		
		-> docker run --name mysql_container -d --network mynetwork -e MYSQL_ROOT_PASSWORD=root 
			MYSQL_USER=root MYSQL_PASSWORD=root -e MYSQL_DATABASE=employeedb mysql:latest
			
		or
		-> docker run --name mysql_container -d --network mynetwork -e MYSQL_ROOT_PASSWORD=root mysql:latest
		
		Then enter the mysql container in interactive mode and create the database with which to use, which is 
		employeedb
			
		This will spin up a mysql container on the mynetwork network
			
		N/B: MYSQL_USER=root and MYSQL_PASSWORD=root are not necessary when you use MYSQL_ROOT_PASSWORD=root
		since it simply means username=root and password=root
		
	g. Now go back to (b) above and build the docker file in order to create an image off of it like so:
		-> docker build -t [whatever-the-name-you-want] .
		e.g docker build -t emp-service:1.0 .
		
		This will build the image of the employee app with name emp-service:1.0
		
	h. Now spin up a container off of the employeedb image and bind it to the same mynetwork as the mysql 
		container like so:
		
		-> docker run --name emp-service-container --network mynetwork -d -p 8044:9192 emp-service:1.0
		
	i. You will notice an error. The app could not connect to the database. This is because the database in 
		the app uses localhost in its url like so:
		
		spring.datasource.url=jdbc:mysql://localhost:8004/employeedb?serverTimezone=UTC&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true
		
		Since we are not using our local mysql database in the container but want to point to a mysql container, 
		we need to specify this as environment varible in our docker file with localhost being replaced with 
		the name of our running container based mysql in the same 'mynetwork' network. The name of the MySQL database is 
		'mysql_container'. Therefore we will update our docker file by adding the following environment variables:
		
			ENV spring.datasource.url="jdbc:mysql://mysql_container:3306/employeedb?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"
			ENV spring.datasource.username="root"
			ENV spring.datasource.password="root"
			
			Complete docker file will now loke like so: 
			
			FROM openjdk:8-jdk-alpine
			ENV spring.datasource.url="jdbc:mysql://mysql_container:3306/employeedb?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"
			ENV spring.datasource.username="root"
			ENV spring.datasource.password="root"
			ARG JAR_FILE=target/employee-app.jar
			WORKDIR /opt/app
			COPY ${JAR_FILE} employee.jar
			ENTRYPOINT ["java","-jar","employee.jar"]
			
	j. Now, build the image and run and everything will work fine
		-> docker build -t emp-service:1.0 .
		-> docker run --name emp-service-container --network mynetwork -p 8044:9192 emp-service:1.0
		
	k. In a situation where you did not specify the environment variables in the docker file, you can do so 
		on the fly when running the container like you did in (f) above by using the -e flag but with 
		uppercase letters and using underscore to replace the dots like so:
		
		-> docker run --name emp-service-container --network mynetwork -p 8044:9192 
			-e SPRING_DATASOURCE_URL="jdbc:mysql://mysql_container:3306/employeedb?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true" 
			-e SPRING_DATASOURCE_USERNAME="root" -e SPRING_DATASOURCE_PASSWORD="root" emp-service:1.0
	
2. Docker Compose
	This is a more simpler process as docker compose simply automates most of the tasks for you. 
	You just need to create your services and state which depends on which and docker compose will 
	create the network and do the binding accordingly.
	
	a. Create a docker file in the employee app like so:
	
		FROM openjdk:8-jdk-alpine
		ARG JAR_FILE=target/employee-app.jar
		WORKDIR /opt/app
		COPY ${JAR_FILE} employee.jar
		ENTRYPOINT ["java","-jar","employee.jar"]
		
	b. Create a docker-compose.yml file in same location or some other location like so:
	
		version: "3"
		services:
		  employee:
			build: .
			container_name: employee-micro-service
			ports: 
			  - 8009:9192
			environment:
			  spring.datasource.url: "jdbc:mysql://docker-mysql:3306/employeedb?serverTimezone=UTC&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"
			  spring.datasource.username: "root"
			  spring.datasource.password: "root"
			depends_on:
			  - docker-mysql
			  
		  docker-mysql:
			image: mysql:latest
			container_name: mysql-container
			environment:
			  - MYSQL_ROOT_PASSWORD=root
			  - MYSQL_DATABASE=employeedb
			  
	c. Run the docker-compose.yml file like so:
		-> docker-compose up
	
	d. That's all you have to do.