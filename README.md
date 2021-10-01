REST Example with Dropwizard
===============================

This is a REST service based on Dropwizard framework.

## Building the Application
To build the application including the Docker image, execute the following maven command from the
project folder:
```
mvn clean install -Pdocker
```

## Running the Service

### From Command Line
Since it's packaged as a single JAR, you can start the application as:

```
dropwizard-rest-example-1.0.0.jar server path/to/config/file.yml
```

The YAML configuration file is used to configure service integrations, as well as the HTTP server and logging.

### From IntelliJ
In order to run the service from IntelliJ, follow the steps to configure the runner:
1. From the **Run** menu, select **Edit Configuration...**
1. From the left panel of the **Run/Debug Configurations** dialog, click **+* button from the top.
1. Select `Application` from the **Add New Configuration** dropdown.
1. On the **Configuration** tab of the left panel, select `com.basaki.example.ExampleApplication` as the **Main class** by clicking **...**
1. Add `server src/main/resources/example.yml` as the **Program arguments**.
1. Select the project parent folder as the **Working directory**, e.g., `/Users/dropwizard-rest-example`.
1. Select `munin-application` as the **Use classpath of module**.
1. Click **Apply** and then **OK**.

You can now run the application, by clicking the green run button from the toolbar. The application should start up at
port `8080`.

### Docker Run
Run the newly created Docker image, `docker-registry.iovationnp.com/iovation/munin:1.13.0-SNAPSHOT`, by executing the
docker run command from the terminal:

```
docker run --rm -p 8080:8080  --name=munin docker-registry.iovationnp.com/iovation/munin:1.13.0-SNAPSHOT
```

#### Options
- `--rm` option automatically clean up the container and remove the file system when the container exits.
- `--name` option names the Docker container as `munin`. In absence of the --name option, the Docker generates a
  random name for your container.
- `-p 8080:8080` option publishes all exposed ports to the host interfaces. In our example, it is port 8080 is
  both `hostPort` and `containerPort`

This should start up the munin service at port 8080 and an be accessed at `http://localhost:8080`.

#### Docker Commands
**List Container**
Run `docker ps` to list all the containers. To see all running containers, execute the following command:

```
$ docker ps

CONTAINER ID        IMAGE                                                           COMMAND                  CREATED             STATUS              PORTS                    NAMES
538144f6ec0d        docker-registry.iovationnp.com/iovation/munin:1.13.0-SNAPSHOT   "/bin/sh -c /usr/loc…"   11 seconds ago      Up 10 seconds       0.0.0.0:8080->8080/tcp   munin
```

**Remove Container**
To remove the munun Docker container, execute `docker rm` command. This will remove a non-running container.

```
$ docker rm -f munin
munin
```

## Endpoints

| API               |  Endpoint                                                              |  HTTP Method | 
|-------------------|:-----------------------------------------------------------------------|:-------------|
| Scheduled Report  | /v1/scheduledReports/{subscriberId}/{startTime}                        | GET          | 
| Scheduled Report  | /v1/scheduledReports/{subscriberId}/{startTime}/{endTime}              | GET          |
| Scheduled Report  | /v1/scheduledReports/{subscriberId}/{startTime}/{endTime}/{reportName} | GET          |
| Scheduled Report  | /v1/scheduledReports/{subscriberId}/{key: [^?]+}                       | GET          |
| Scheduled Report  | /v1/scheduledReports/{subscriberId}/{key: [^?]+}                       | DELETE       |
| CTD               | /v1/ctd/{subscriberId}/{startTime}                                     | GET          | 
| CTD               | /v1/ctd/{subscriberId}/{startTime}/{endTime}                           | GET          |
| CTD               | /v1/ctd/download/{subscriberId}/{hourStamp}                            | GET          |
| CTD               | /v1/ctd/download/{subscriberId}/{startHour}/{endHour}                  | GET          |
| CTD               | /v1/ctd/download/{subscriberId}/olderThan/{hourStamp}                  | DELETE       |
| Health            | /admin/healthcheck                                                     | GET          |
| Ping              | /admin/ping                                                            | GET          |

### Examples:

#### List Scheduled Report based on start time
```
curl --request GET \
  --url http://localhost:8080/reports/v1/scheduledReports/1000/1600954959000 \
  --header 'authorization: Basic MTAwMC91N2k3LW02LVM3OEFBQUFBQUFBQUFBL3JlcG9ydGluZ0FwaTpSb0ZMNnZld3ZmZVpNbERiZGFkclA2REhpaEtCYXJDSnRyYzY3Zkt5RHRV' 
```

#### Health Check
```
curl --request GET \
  --url http://localhost:8080/admin/healthcheck \
  --header 'authorization: Basic YWRtaW46cWFvbmx5'
```

### Ping
```
curl --request GET \
  --url http://localhost:8080/admin/ping \
  --header 'authorization: Basic YWRtaW46cWFvbmx5' 
```


