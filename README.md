ImpressiveCode ic-utils Project
===================

# License
Copyright (C) 2013 ImpressiveCode (http://impressivecode.org)

Depress is licensed under the GPL license. (http://www.gnu.org/licenses/gpl.html)
<h3>Preface</h3>
Plugin need Java 7 or higher to work. Source crawler can be used as a maven plugin or as a standalone application.

<h3>How to use as maven plugin</h3>

In plugin directory invoke following command: 

```
mvn clean install
```

When plugin is installed in local repository, we can use it in any project. Add it to pom.xml file.

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

Now we can execute scan goal in this project by following command:

```
mvn org.impressivecode.utils.sourcecrawler:ic-utils-sourcecrawler:1.0.0:scan
```

Plugin generates output sourcecrawler-[name-of-directory].xml for every module in parent directory of project.

You can execute another goal to merge those file into one sourcecrawler.xml using following command:

```
mvn org.impressivecode.utils.sourcecrawler:ic-utils-sourcecrawler:1.0.0:merge
```

<h3>How to use plugin as standalone scanner</h3>

In directory which contains plugins jar invoke followed command:
```
java -jar uber-ic-utils-sourcecrawler-1.0.0.jar -i ./dir/to/scan -o ./dir/output.xml
```
Mandatory flag is -i (--input). This flag shows where start scanning project's files. Optional flag is -o (--output) which shows where to save output in xml.

<h3>Sample output file</h3>

Sample output xml file:

```xml
<root>
  <file>
    <path>C:\workspace\sourcecrawler\ic-utils\ic-utils-sourcecrawler\src\main\java\org\impressivecode\utils\sourcecrawler\CommandLineValues.java</path>
    <package>org.impressivecode.utils.sourcecrawler</package>
    <classes>
      <class>
        <name>CommandLineValues</name>
        <access>Package-private</access>
        <type>Class</type>
        <exception>false</exception>
        <inner>false</inner>
        <test>false</test>
        <final>false</final>
      </class>
    </classes>
  </file>
  <file>
    <path>C:\workspace\sourcecrawler\ic-utils\ic-utils-sourcecrawler\src\main\java\org\impressivecode\utils\sourcecrawler\document\DocumentWriter.java</path>
    <package>org.impressivecode.utils.sourcecrawler.document</package>
    <classes>
      <class>
        <name>DocumentWriter</name>
        <access>Private</access>
        <type>Interface</type>
        <exception>false</exception>
        <inner>false</inner>
        <test>false</test>
        <final>false</final>
      </class>
    </classes>
  </file>
</root>
```
