# Financial Rates Comparison #

This is a command line program that takes a user's data input and generates analysis of financial rates offered by various companies / banks.

Financial Rates Comparison is written in Java and tested on Mac OS X 10.2 and above.

# Table of Contents

* [Prerequisites: Install Java](#installation)
* [Prerequisites: Install Maven](#build-management)
* [Prerequisites: Install Git](#source-control)
* [Recreating the Maven project from scratch - optional](#recreating-from-scratch)
* [Building and running the program](#running-program)
* [Design Patterns](#design-patterns)
* [License](#license)


<a name="installation"></a>
## Install Java ##

Please skip this section if you are sure you have the Java Development Kit installed.

To check existence of the JDK (open your command prompt or terminal console) :

```
    javac -version
```

If Java is not pre-installed on your machine, please download and install the JDK from http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html.

On Windows 10 and Windows 8, after installation, proceed to make the java utitilies globally accessible:

1. In Search, search for 'Environment Variables'
2. Select 'Edit the system environment variables (Control Panel)'
3. Click Environment Variables. In the section System Variables, find the PATH environment variable and select it. Click Edit. If the PATH environment variable does not exist, click New.
4. In the Edit System Variable (or New System Variable) window, specify the value of the PATH environment variable. e.g. C:\Program Files\Java\jdk-10.0.1\bin
5. Close and reopen your command prompt for the new settings to take place

<a name="build-management"></a>
## Install Maven ##

Maven is the default build management tool for this project. Please skip this section if you already have Maven installed.

Make sure you can execute the Maven program: 

```
    mvn -v
```

Install Maven by downloading the latest package from https://maven.apache.org/download.cgi

Proceed to extract the package into the directory location of your choice:

```
    unzip apache-maven-3.5.3-bin.zip
```

or

```
    tar xzvf apache-maven-3.5.3-bin.tar.gz
```

On MAC OS X: Add the bin directory of the created directory apache-maven-3.5.3 to the PATH environment variable

```
    export PATH=/directory/path/to/maven/bin:$PATH
```

for example

```
    export PATH=/Users/username/apache-maven-3.5.3/bin:$PATH
```

On Windows 10 and Windows 8:

1. In Search, search for 'Environment Variables'
2. Select 'Edit the system environment variables (Control Panel)'
3. Click Environment Variables. In the section System Variables, find the PATH environment variable and select it. Click Edit. If the PATH environment variable does not exist, click New.
4. In the Edit System Variable (or New System Variable) window, specify the value of the PATH environment variable. e.g. C:\apache-maven-3.5.3\bin
5. Close and reopen your command prompt for the new settings to take place

On Windows 7:

1. Click Start, then Control Panel, then System.
2. Click Advanced, then Environment Variables.
3. Add the location of the bin folder of the JDK installation for the PATH variable in System Variables. The following is a typical value for the PATH variable: C:\apache-maven-3.5.3\bin
4. Close and reopen your command prompt for the new settings to take place


Confirm the installation by running the command 'mvn -v'. The result should look similar to

```
    Apache Maven 3.5.3 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T08:58:13+01:00)
    Maven home: /opt/apache-maven-3.5.3
    Java version: 1.8.0_45, vendor: Oracle Corporation
    Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/jre
    Default locale: en_US, platform encoding: UTF-8
    OS name: "mac os x", version: "10.8.5", arch: "x86_64", family: "mac"
```

<a name="source-control"></a>
## Install Git ##

Please skip this section if you are sure you have Git installed.

To check existence of git (open your command prompt or terminal console) :

```
    git --version
```

If Git is not installed, please download and install it from https://git-scm.com/downloads.

<a name="recreating-from-scratch"></a>
## Recreating the Maven project from scratch (optional) ##

The following are detailed instructions to recreate the Maven project step-by-step. You can skip this section if you are only interested in building and running the project.

To create the Maven project, we will make use of Maven's archetype mechanism. In Maven, an archetype is a template of a project which is combined with some user input to produce a working Maven project that has been tailored to the user's requirements.

To create the Maven project, execute the following from the command line. The process should only take a few seconds:

```
    mvn -B archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=com.sharon.govtech.assignment -DartifactId=mas-api-app
```

Once you have executed this command, you will notice that a directory named mas-api-app has been created for the new project, and this directory contains a file named pom.xml.

pom.xml contains the Project Object Model (POM) for this project. It contains every important piece of information about the project.

Edit the pom.xml so the file looks like this. We have made some changes to the default pom.xml. Examples of the changes we have made include - adding a json library for easy processing of json and instructing Maven to include the dependencies in the target folder when we compile our class:

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.sharon.govtech.assignment</groupId>
  <artifactId>mas-api-app</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>mas-api-app</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>com.googlecode.json-simple</groupId>
      <artifactId>json-simple</artifactId>
      <version>1.1</version>
    </dependency>
  </dependencies>
  <properties>
    <maven.compiler.source>1.6</maven.compiler.source>
    <maven.compiler.target>1.6</maven.compiler.target>
  </properties>
  <build>
    <plugins>
      <plugin>
        <artifactId>maven-dependency-plugin</artifactId>
        <executions>
          <execution>
            <phase>install</phase>
            <goals>
              <goal>copy-dependencies</goal>
            </goals>
            <configuration>
              <outputDirectory>${project.build.directory}/lib</outputDirectory>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
</project>
```

<a name="running-program"></a>
## Building and running the program ##

Make sure you can execute the Maven program: 

```
    mvn -v
```

The result should look similar to

```
    Apache Maven 3.5.3 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T08:58:13+01:00)
    Maven home: /opt/apache-maven-3.5.3
    Java version: 1.8.0_45, vendor: Oracle Corporation
    Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/jre
    Default locale: en_US, platform encoding: UTF-8
    OS name: "mac os x", version: "10.8.5", arch: "x86_64", family: "mac"
```

If you have installed Maven but cannot execute the mvn command, you have to add the bin directory of directory apache-maven-3.5.3 to the PATH environment variable:

```
    export PATH=/directory/path/to/maven/bin:$PATH
```

To build the program, we will need to download the project assets from a Github repository.

Make sure you have Git installed by executing the following command in your command prompt / terminal. If Git is not already installed on your system, please download and install it from https://git-scm.com/downloads/.

```
    git --version
```

Open your command line and change to a directory of your preference.

On Mac OS X, you can use your home directory: /Users/user-name

To download a copy of the project assets : 

```
    git clone https://github.com/however12345/pushed-pork
    cd pushed-pork
```

Maven is based around the central concept of a build lifecycle, consisting of various phases. 

The default Maven lifecycle consist of the following phases:
* validate
* compile
* test
* package
* verify
* install

The clean Maven lifecycle consist of the following phases.
* pre-clean	
* clean
* post-clean

Maven commands are executed sequentially and in order. For instance, if you use the command 'install', Maven will execute the phases that come before 'install' - 'validate', 'compile', 'test', 'package', 'verify' before finally executing the 'install' phase.

For our project, we will execute the following command. What we are instructing Maven to do is to execute the phase 'pre-clean' and 'clean', followed by the phases 'validate', 'compile', 'test', 'package', 'verify', 'install'.

```
    mvn clean install
```

On Mac OS: Test the newly compiled and packaged JAR with the following command:

```
    java -cp target/mas-api-app-1.0-SNAPSHOT.jar:target/lib/json-simple-1.1.jar com.sharon.govtech.assignment.App
```

On Windows: Test the newly compiled and packaged JAR with the following command:

```
    java -cp target/mas-api-app-1.0-SNAPSHOT.jar;target/lib/json-simple-1.1.jar com.sharon.govtech.assignment.App
```

<a name="design-patterns"></a>
## Explanation on any design patterns used and the reason

### The program makes use of the following design patterns.

### Creational Factory Design Pattern:
FinanceRate is the superclass that contains the common properties (e.g. fixed_deposit_3m, fixed_deposit_6m) and methods (e.g. setFixedDeposit3M, setFixedDeposit6M) for the BankRate and FinancialCompanyRate sub-classes. This makes code less coupled and easy to extend. For example, we can easily add additional properties and methods for BankRate and FinancialCompanyRate classes, or change BankRate class implementation by overriding common methods.

### Structural Facade Pattern:
MASApiCall acts as the helper interface class that is used to make API calls, so that the client application is able to use the interface to get the API connection and retrieve the required data. This helps to avoid having a lot of logic on the client side and allows the client application to easily interact with the system.

### Behavioral Command Design Pattern:
In the client application, the user is able to select from a set of options to view the required information after entering the dates to retrieve the entire data set for that period. The App program then passes the user option request to the appropriate method, which processes and outputs the data according to the specified action. The advantage of this pattern is that it is easily extendible, so we can add new action methods without changing the client code.

![Alt text](images/chart.png?raw=true "Class Diagram")

<a name="license"></a>
## License ##

This project is licensed under the MIT License.

