ic-utils
========

ImpressiveCode utils

<h3>Project purpose</h3>
Plugin for analyze source files in Maven project. Purpose of this plugin is generate xml file describing structure of classes in project.

<h3>How to use</h3>
In project directory invoke follow command: <br/>
```
mvn clean install
```
When plugin is installed in local repository we can use it in own project by addit it in pom file:

```xml
<build>
		<plugins>
			<plugin>
				<groupId>org.impressivecode.utils.sourcecrawler</groupId>
				<artifactId>ic-utils-sourcecrawler</artifactId>
				<version>1.0.0</version>
			</plugin>
    </plugins>
</build>
```

Now we can use scann goal in project by follow command:
```
mvn org.impressivecode.utils.sourcecrawler:ic-utils-sourcecrawler:1.0.0:scann
```
Plugin generate output in <b>sourcecrawler.xml</b> in root directory of project.

<h3>Output file structure</h3>
Sample output xml file:
```xml
<root>
  <file>
    <path>.\ic-utils\ic-utils-sourcecrawler\src\main\java\org\impressivecode\utils\sourcecrawler\document\DocumentWriter.java</path>
    <package>org.impressivecode.utils.sourcecrawler.document</package>
    <classes>
      <class>
        <type>Interface</type>
        <name>DocumentWriter</name>
        <exception>false</exception>
        <inner>false</inner>
        <test>false</test>
      </class>
    </classes>
  </file>
  <file>
    <path>.\ic-utils\ic-utils-sourcecrawler\src\main\java\org\impressivecode\utils\sourcecrawler\document\XMLDocumentWriterImpl.java</path>
    <package>org.impressivecode.utils.sourcecrawler.document</package>
    <classes>
      <class>
        <type>Class</type>
        <name>XMLDocumentWriterImpl</name>
        <exception>false</exception>
        <inner>false</inner>
        <test>false</test>
      </class>
    </classes>
  </file>
</root>
```
