# API Testing Framework with Serenity BDD

This project is an API testing framework built with Serenity BDD and Cucumber, designed for testing RESTful APIs.

## Prerequisites

- Java 21 or higher ([Installation Guide](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html))
- Maven 3.8.1 or higher ([Installation Guide](https://maven.apache.org/install.html))

## Getting Started

### Running Tests

To run all tests, open your terminal at the root of the project and execute:

```bash
mvn clean install -Drestapi.baseurl=https://waarkoop-server.herokuapp.com/
```

### Project Structure

```
├── src
│   ├── main
│   │   └── java
│   │       └── org.softindustry.com
│   └── test
│       ├── java
│       │   └── org.softindustry.com
│       │       ├── steps       # Step definitions
│       │       └── tests       # Test runners
│       └── resources
│           └── features        # Cucumber feature files
```

## Creating New Tests

### Step 1: Add Feature File

Create a new `.feature` file in `src/test/resources/features/` directory with your Cucumber scenarios.

### Step 2: Implement Step Definitions

Add corresponding step definitions in the `org.softindustry.com.steps` package.

### Step 3: Create Test Runner

```java
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/search/search_product.feature",
        glue = "org.softindustry.com.steps")
public class SearchProductTest {

    @BeforeClass
    public static void setUp() {
        EnvConfigs.setConfigs();
    }
}
```

### Step 4: Add Test to Test Suite

```java
@RunWith(Suite.class)
@Suite.SuiteClasses({SearchProductTest.class})
public class SearchSuite {
}
```

### Step 5: Include Suite in Maven Profile

Update the `pom.xml` file to include your test suite in an appropriate profile:

```xml
<profile>
    <id>regression</id>
    <build>
        <plugins>
            <plugin>
                <!-- Configuration -->
                <includes>
                    <include>*SearchSuite.java</include>
                </includes>
            </plugin>
        </plugins>
    </build>
</profile>
```

For more detailed information about Serenity BDD, please refer to the [official documentation](https://serenity-bdd.github.io/).

## Framework Enhancements

The framework has been upgraded with the following improvements:

1. **Cleaned up project structure**:
    - Removed unnecessary files and folders (.github, .idea, .m2, gradle, etc.)
    - Removed build-specific files not required for Maven-based project

2. **POM.xml cleanup and improvements**:
    - Updated groupId, artifactId, and name
    - Removed unused properties and configurations
    - Removed browser-specific configurations not needed for API testing

3. **Technology upgrades**:
    - Updated Serenity to the latest version
    - Upgraded from Cucumber 6 to Cucumber 7
    - Upgraded from JUnit 4 to JUnit 5 (running in vintage mode)
    - Standardized on Java 17 (LTS version)

4. **Library enhancements**:
    - Implemented RestAssured for API requests
    - Replaced outdated Hamcrest library versions
    - Added Serenity Screenplay pattern support with appropriate libraries

## Known Issues

The automated tests have identified the following issues:

1. Search with an empty keyword returns HTTP 401 instead of the expected 404. The response body does not conform to the JSON error schema.
2. Using HTTP methods other than GET for search requests produces response bodies that do not conform to the JSON error schema.
