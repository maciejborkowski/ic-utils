ImpressiveCode ic-utils Project
===================

# License
Copyright (C) 2013 ImpressiveCode (http://impressivecode.org)

Depress is licensed under the GPL license. (http://www.gnu.org/licenses/gpl.html)

<h2>How to use</h2>

In plugin directory invoke following command: 

```
mvn clean install
```

When plugin is installed in local repository, we can use it in own project by. Just only add it to pom file.

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

Now we can use scan goal in project by follow command:

```
mvn org.impressivecode.utils.sourcecrawler:ic-utils-sourcecrawler:1.0.0:scann
```

Plugin generate output file source crawler.xml in root directory of project.

<h2>Output file structure</h2>

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
<h2>How to use plugin as standalone scanner</h2>

In directory which contains plugins jar invoke followed command:
```
java -jar ic-utils-sourcecrawler-1.0.0-snapshot.jar -i ./dir/to/scan -o ./dir/output.xml
```
Mandatory flag is -i (--input). This flag shows where start scanning project's files. Optional flag is -o (--output) which shows where to save output in xml.
