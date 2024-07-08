# Ember-MVC

## Introduction

Ember-MVC is a Java-based web framework built on top of the Servlet API. It provides a lightweight alternative to Spring MVC, focusing on core functionalities.

## Getting Started

**Directory Structure:**

* **src/**: Contains the source code for the project.
* **build.bat**: Script for building the project into JAR file.

**Prerequisites**

* Java Development Kit (JDK)
* Jakarta Servlet API

### Building the Project

The repository contains a batch script file to build the project. Run the following command in the terminal:

```bash
./build.bat
```

The script will create a JAR file named **ember_mvc.jar**. Add the file to your project's libraries and it will be ready to use for your web application. 

**Note:** Do not forget to add FrontController inside your **web.xml** file with your controller package as a `init_param` value.

## Feature

* You can now send data for a request using the `@RequestParameter` annotation. It is to be added to the parameter of the called method.
* Session usage is now possible by adding the `Session` as method parameter or by adding the `Session` object as the controller class attribute.

**Note:** `Allowed return type` : String and ModelView

## Example 

* Here how to config the framework in the `web.xml` file

```xml
<servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>mg.emberframework.controller.FrontController</servlet-class>
    <load-on-startup>1</load-on-startup>
    <init-param>
        <param-name>package_name</param-name>
        <param-value>controller</param-value>
    </init-param>
</servlet>

<servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

* This example handles requests related to employee data management.
```java
// It leverages the `mg.emberframework` framework for annotations and data management.
@Controller
public class MyController {

    // Injected session object for managing user data across requests (if applicable).
    private final Session session;

    // Constructor to inject dependencies (consider using dependency injection framework).
    public MyController(Session session) {
        this.session = session;
    }

    /**
     * Handles GET requests to the "/working" endpoint.
     *
     * @param name (optional) The name of the employee to retrieve (if applicable).
     * @param password (optional) The password for authentication (if applicable).
     * @param mySession (injected) The session object for managing user data.
     * @return A ModelView object containing the response data and view URL.
     */
    @Get("/working")
    public ModelView index(
            @RequestParameter(name = "name", required = false) String name,
            @RequestParameter(name = "password", required = false) String password,
            Session mySession
    ) {
        ModelView result = new ModelView();
        result.setUrl("./WEB-INF/pages/index.jsp");

        result.addObject("random", "This is a random object");

        // Store user credentials in session if necessary (implement proper security measures).
        if (name != null && password != null) {
            mySession.add("name", name);
            mySession.add("password", password);
        }

        return result;
    }
}

```

## Contributing

Contributions are welcome. Please feel free to fork the project and submit your pull requests by :

* Implementing additional features based on the Spring framework.
* Improving the existing code.
* Adding documentation and comments.

## License

This project is licensed under the MIT License. This license grants you permission to freely use, modify, and distribute this software under certain conditions. Please refer to the [LICENSE](./LICENSE) file for more details.

## Contact

You can reach me at: [sandratrarafa@gmail.com](mailto:sandratrarafa@gmai.com)

## Acknowledgments

Mr Naina
