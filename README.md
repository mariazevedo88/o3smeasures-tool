o3smeasures
===========

Plug-in for Eclipse to measure software internal quality. It was developed as part of my dissertation entitled
"Um Estudo Quantitativo para Caracterização da Qualidade Interna de Sistemas de Software Orientados a Objetos Open-Source"
("A Quantitative Study to Characterize the Internal Quality of Open-Source Object-Oriented Software Systems").


#About o3smeasures
This is a plug-in for Eclipse to measure software internal quality in Java projects.
In the plug-in development, we used the Eclipse IDE 4.4 (Luna), the Java Development Tools (JDT), the Plug-in Development Environment (PDE), and Abstract Syntax Tree (AST). In JDT, there are tools for manipulating Java code. In PDE, there are tools to develop and test plug-ins in the Eclipse IDE.

#Dependencies

- Java 8
- Eclipse IDE 4.4 (Luna) or higher
- junit 4 (4.11.0)
- org.eclipse.ui
- org.eclipse.core.resources
- org.eclipse.jdt.core (3.8.3)
- org.eclipse.core.runtime (3.8.0)

#How to use as an Eclipse Application

o3smeasures can be used as an Eclipse Application, importing the project into your workspace.
After import the o3smeasures project, with the right mouse button you need select the option 
<b>Run as</b> -> <b>Eclipse Application</b>. In the runtime eclipse application, select a Java project to be measured and with the right mouse button, go to <b>O3SMeasures</b> -> <b>Measure</b>.

#How to install 

o3smeasures plug-in is installed in the Eclipse directory. You need to paste the .jar on the <b>dropins</b> directory (<b>eclipse</b> -> <b>dropins</b>).
After installed the o3smeasures project into your workspace, select a Java project to be measured and with the right mouse button, go to <b>O3SMeasures</b> -> <b>Measure</b>.

#Displaying the views

The o3smeasures views can be enabled by the eclipse menu <b>Window</b> -> <b>Show View</b> -> <b>Other ...</b> -> <b>o3smeasures</b>.
 

#Exporting files

To export the measurement results in the O3SMeasures Diagnostic view, with the right mouse button in the view, select the option <b>Export to CSV file</b> to export in the .csv format or <b>Export to XML file</b> to export in the .xml format.
