# Java-kanban 

This is a Spring Boot social media application where you can find a friend, common films or films based on rates
  
## Features:	
- Create a user
- Add a friend
- Add a film 
- Add like to film
- View list of films based on rates

## Technologies
* Spring Boot
* Maven
* Lombok
* JUnit

## Database
- H2
- PostgreSQL
 
## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 19+
* Maven or another build automation tool 

To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/vitaliibredun/java-kanban.git`

The application will be available at http://localhost:8080

## API

The tasking manager exposes some of its functionality via an API. It is documented here: 





## Download

- For stable releases, please go to [Github Releases page](https://github.com/LSPosed/LSPosed/releases)
- For canary build, please check [Github Actions](https://github.com/LSPosed/LSPosed/actions)

Note: debug builds are only available in Github Actions.





## Tests

The tests use a separate database. Create that database first:

    sudo -u postgres createdb -O www-data osmtm_tests
    sudo -u postgres psql -d osmtm_tests -c "CREATE EXTENSION postgis;"

Create a `local.test.ini`file in the project root, where you will add the
settings for the database connection.
For example:

    [app:main]
    sqlalchemy.url = postgresql://www-data:www-data@localhost/osmtm_tests

To run the tests, use the following command:

    ./env/bin/nosetests
    
    
    
    
    
    
    
    
    
    = Neo4j: Graphs for Everyone =

https://neo4j.com[Neo4j] is the world's leading Graph Database. It is a high performance graph store with all the features expected of a mature and robust database, like a friendly query language and ACID transactions. The programmer works with a flexible network structure of nodes and relationships rather than static tables -- yet enjoys all the benefits of enterprise-quality database. For many applications, Neo4j offers orders of magnitude performance benefits compared to relational DBs.

Learn more on the https://neo4j.com[Neo4j website].

https://discord.gg/neo4j[image:https://img.shields.io/discord/787399249741479977?label=Chat&logo=discord&style=for-the-badge[Discord]]

https://community.neo4j.com[image:https://img.shields.io/discourse/users?label=Forums&logo=discourse&server=https%3A%2F%2Fcommunity.neo4j.com&style=for-the-badge[Discourse users]]

== Using Neo4j ==

Neo4j is available both as a standalone server, or an embeddable component. You can https://neo4j.com/download/[download] or https://neo4j.com/sandbox/[try online].

== Extending Neo4j ==

We encourage experimentation with Neo4j. You can build extensions to Neo4j, develop library or drivers atop the product, or make contributions directly to the product core. You'll need to sign a Contributor License Agreement in order for us to accept your patches.

== Dependencies ==

Neo4j is built using https://maven.apache.org/[Apache Maven] version 3.8.2 and a recent version of supported VM. Bash and Make are also required. Note that maven needs more memory than the standard configuration, this can be achieved with `export MAVEN_OPTS="-Xmx2048m"`.

macOS users need to have https://brew.sh/[Homebrew] installed.

=== With brew on macOS ===

  brew install maven

Please note that we do not support building Debian packages on macOS.

=== With apt-get on Ubuntu ===

  sudo apt install maven openjdk-17-jdk

== Building Neo4j ==

Before you start running the unit and integration tests in the Neo4j Maven project on a Linux-like system, you should ensure your limit on open files is set to a reasonable value. You can test it with `ulimit -n`. We recommend you have a limit of at least 40K.

* A plain `mvn clean install -T1C` will only build the individual jar files.
* Test execution is, of course, part of the build.
* In case you just want the jars, without running tests, this is for you: `mvn clean install -DskipTests -T1C`.
* You may need to increase the memory available to Maven: `export MAVEN_OPTS="-Xmx2048m"`.
* You may run into problems resolving `org.neo4j.build:build-resources` due to a bug in maven. To resolve this simply invoke `mvn clean install -pl build-resources`.

== Running Neo4j ==

After running a `mvn clean install`, `cd` into `packaging/standalone/target` and extract the version you want, then:

  bin/neo4j-admin start

in the extracted folder to start Neo4j on `localhost:7474`. On Windows you want to run:

  bin\neo4j-admin start

instead.

== Neo4j Desktop ==

Neo4j Desktop is a convenient way for developers to work with local Neo4j databases.

To install Neo4j Desktop, go to https://neo4j.com/download-center/[Neo4j Download Center] and follow the instructions. 

== Licensing ==

Neo4j Community Edition is an open source product licensed under GPLv3.

Neo4j Enterprise Edition includes additional closed-source components _not available in this repository_ and requires a commercial license from Neo4j or one of its affiliates.

== Trademark ==

Neo4j's trademark policy is available at https://neo4j.com/trademark-policy/[our trademark policy page].
