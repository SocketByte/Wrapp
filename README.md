# Wrapp ![version](https://img.shields.io/badge/version-1.0-blue.svg) [![Build Status](https://travis-ci.org/SocketByte/Wrapp.svg?branch=master)](https://travis-ci.org/SocketByte/Wrapp)
Simple class wrapper featuring deep field invoking.

**Wrapp** is based on Java 8 `MethodHandles` for maximum performance. 
It allows you to wrap a whole class (and it's superclasses) 
to one simple `Wrapper<T>` class which is `Serializable`.

That allows you to create `Serializable` versions of classes, serialize them
and then deserialize them  using existing object instance.

#### Usage
```java
// Create reusable wrapper factory
WrapperFactory wrapperFactory = new WrapperFactory();
// Register (optionally) classes for faster field reading
wrapperFactory.register(TestClass.class);
// Create an instance (TestClass is NOT serializable)
TestClass testClass = new TestClass("Something");
// Write it! (Wrapper<T> IS serializable)
Wrapper<TestClass> wrapper = wrapperFactory.write(testClass);
// Then you can read it from existing wrapper
TestClass otherInstance = new TestClass("Nothing");
wrapperFactory.read(wrapper, otherInstance);

System.out.println(otherInstance.getTestString());
// Output: Something
```

#### Installation
Project requires _JRE 8_ and _JDK 8_ installed
```xml
    <repositories>
        <repository>
            <id>socketbyte-repo</id>
            <url>http://repo.socketbyte.pl/repository/nexus-releases</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>pl.socketbyte</groupId>
            <artifactId>Wrapp</artifactId>
            <version>1.0</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```

#### License
Project is based on MIT License.


