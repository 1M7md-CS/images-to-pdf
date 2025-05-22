# Images to PDF Converter

[![PDF Converter](https://img.shields.io/badge/PDF-Converter-blue.svg)](https://en.wikipedia.org/wiki/PDF)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-24-red.svg)](https://www.oracle.com/java/)
[![GitHub issues](https://img.shields.io/github/issues/1M7md-CS/images-to-pdf)](https://github.com/1M7md-CS/images-to-pdf/issues)

A command-line tool to convert images to PDF file with optional sorting by image name.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)
- [Author](#author)



## Features

- Convert multiple image formats (JPG, JPEG, PNG, GIF, BMP, TIFF, JFIF, HEIC) to a single PDF
- Option to sort images by numeric image name before conversion
- Center images on A4-sized pages


## Prerequisites

- Java 24 or higher installed
- Apache Maven installed


## Installation

```bash
# Clone the repository
git clone https://github.com/1M7md-CS/images-to-pdf.git

# Navigate to project directory
cd images-to-pdf

# Compile source files using Maven
mvn clean compile

# Run the application using Maven
mvn exec:java
```


## Usage

```
Choose an option:
1. Convert images to pdf with sorting
2. Convert images to pdf without sorting
3. Help
4. Exit

Enter folder path: C:\Users\user\Desktop\imageFolder
Enter the name of PDF file: myPDF
Output: Done. (PDF saved in folder: 'C:\Users\user\Desktop\imageFolder\myPDF.pdf')
```

- **With Sorting**: Images are sorted by numeric image name (e.g., 1.jpg, 2.png).
- **Without Sorting**: Images are processed in default filesystem order.


## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author
Mohammad Hammad - [GitHub](https://github.com/1M7md-CS)
