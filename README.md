# quarkus-reactive-postgres project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `quarkus-reactive-postgres-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkus-reactive-postgres-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

Create a native executable using: `./mvnw package -Pnative`.

### Issues with packaging on Windows

The Microsoft Native Tools for Visual Studio must first be initialized before packaging. You can do this by starting
the `x64 Native Tools Command Prompt` that was installed with the Visual Studio Build Tools. At 
`x64 Native Tools Command Prompt` you can navigate to your project folder and run `mvnw package -Pnative`.

Another solution is to write a script to do this for you:

```
cmd /c 'call "C:\Program Files (x86)\Microsoft Visual Studio\2017\BuildTools\VC\Auxiliary\Build\vcvars64.bat" && mvn package -Pnative'
```

You can then execute your native executable with: `./target/quarkus-reactive-postgres-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Step by Steps

Package the application by running:
```
mvnw package 
```

Create a native image (exe-file under Windows):
```
mvnw package -Pnative
```

Verify that the native artifact was properly constructed:
```
mvnw verify -Pnative 
```

Call in browser: http://localhost:8080/hello/polite/Mr.President


## Docker

Create a container image using our native executable. For that, we must have a container runtime (i.e. Docker) running in our machine
```
mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=docker -Dquarkus.native.native-image-xmx=6g
```

Create a docker image:
```
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-project .
```

Run the container:
```
docker run -i --rm -p 8080:8080 quarkus/quarkus-project
```

## Pitfalls (Windows)

* install and use x64 Native Tools Command Prompt (as extension for Visual Studio 2019)
* use the newest release (see also https://github.com/oracle/graal/issues/2522)
* svm.jar (included in Graal VM package) is recognized as threat by Windows defender (see also https://github.com/oracle/graal/issues/1752)
* share the project directory in Docker (see also https://github.com/quarkusio/quarkus/issues/9816)
* increase docker memory up to 8GB (see also https://github.com/oracle/graal/issues/920)