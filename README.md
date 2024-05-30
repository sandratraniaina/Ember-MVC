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

* The lastest feature execute associated method for user requested URL. 

**Note:** Method should only return a string for now

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
