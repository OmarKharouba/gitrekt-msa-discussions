# Main Server

The main server is responsible for getting requests from the user,
adding the request in the queue for the respective service.

### Instructions

1. Install Docker

    ###### Ubuntu
    
    https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-16-04
    
    ###### Mac
    
    https://runnable.com/docker/install-docker-on-macos

2. Clone the repo 

3. Run `docker-compose up -d` to start the server

4. Access the server @ `localhost`

5. To stop the server for recompile run `docker-compose stop mainserver`

6. To run `mainserver` again `docker-compose up mainserver`