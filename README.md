# o3smeasures 

[![Build Status](https://travis-ci.org/mariazevedo88/o3smeasures-tool.svg?branch=master)](https://travis-ci.org/mariazevedo88/o3smeasures-tool) ![GitHub tag (latest SemVer)](https://img.shields.io/github/tag/mariazevedo88/o3smeasures-tool.svg) ![GitHub repo size](https://img.shields.io/github/repo-size/mariazevedo88/o3smeasures-tool.svg) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/mariazevedo88/o3smeasures-tool.svg) ![GitHub language count](https://img.shields.io/github/languages/count/mariazevedo88/o3smeasures-tool.svg) ![GitHub top language](https://img.shields.io/github/languages/top/mariazevedo88/o3smeasures-tool.svg) ![GitHub](https://img.shields.io/github/license/mariazevedo88/o3smeasures-tool.svg) ![GitHub All Releases](https://img.shields.io/github/downloads/mariazevedo88/o3smeasures-tool/total.svg) ![GitHub last commit](https://img.shields.io/github/last-commit/mariazevedo88/o3smeasures-tool.svg) 

Plug-in for Eclipse to measure software internal quality. It was developed as part of my dissertation entitled
"Um Estudo Quantitativo para Caracterização da Qualidade Interna de Sistemas de Software Orientados a Objetos Open-Source"
("A Quantitative Study to Characterize the Internal Quality of Open-Source Object-Oriented Software Systems").

## About o3smeasures
This is a plug-in for Eclipse to measure software internal quality in Java projects.
In the plug-in development, we used the Eclipse IDE (Oxygen), the Java Development Tools (JDT), the Plug-in Development Environment (PDE), and Abstract Syntax Tree (AST). In JDT, there are tools for manipulating Java code. In PDE, there are tools to develop and test plug-ins in the Eclipse IDE.

## Dependencies

- Java 8 or higher
- Eclipse IDE Oxygen or higher
- junit 4 (4.11.0)
- org.eclipse.ui (3.109.0 or higher)
- org.eclipse.core.resources (3.12.0 or higher)
- org.eclipse.jdt.core (3.8.3 or higher)
- org.eclipse.core.runtime (3.13.0 or higher)

## How to use as an Eclipse Application

o3smeasures can be used as an Eclipse Application, importing the project into your workspace.
After import the o3smeasures project, with the right mouse button you need select the option 
<b>Run as</b> -> <b>Eclipse Application</b>. In the runtime eclipse application, select a Java project to be measured and with the right mouse button, go to <b>O3SMeasures</b> -> <b>Measure</b>.

## How to install

### On dropins directory 

o3smeasures plug-in is installed in the Eclipse directory. You need to paste the .jar on the <b>dropins</b> directory (<b>eclipse</b> -> <b>dropins</b>).
After installed the o3smeasures project into your workspace, select a Java project to be measured and with the right mouse button, go to <b>O3SMeasures</b> -> <b>Measure</b>.

### Marketplace

Run Eclipse, go to <b>Help menu</b> -> <b>Install New Software...</b> On the opening dialog add a new Remote site named o3smeasures with the following url `https://raw.githubusercontent.com/mariazevedo88/o3smeasures-plugin/master/site.xml` and follow the instructions.

## Displaying the views

The o3smeasures views can be enabled by the eclipse menu <b>Window</b> -> <b>Show View</b> -> <b>Other ...</b> -> <b>o3smeasures</b>.
 
## Exporting files

To export the measurement results in the O3SMeasures Diagnostic view, with the right mouse button in the view, select the option <b>Export to CSV file</b> to export in the .csv format or <b>Export to XML file</b> to export in the .xml format.

## Contributing

[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/images/0)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/links/0)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/images/1)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/links/1)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/images/2)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/links/2)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/images/3)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/links/3)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/images/4)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/links/4)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/images/5)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/links/5)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/images/6)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/links/6)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/images/7)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/o3smeasures-tool/links/7)
