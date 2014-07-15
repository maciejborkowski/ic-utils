package org.impressivecode.utils.sourcecrawler.parser;

import static org.testng.Assert.*;

import java.util.ArrayList;

import org.impressivecode.utils.sourcecrawler.files.FileHelper;
import org.impressivecode.utils.sourcecrawler.model.ClazzType;
import org.impressivecode.utils.sourcecrawler.model.JavaClazz;
import org.impressivecode.utils.sourcecrawler.model.JavaFile;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.qdox.model.impl.DefaultJavaPackage;

public class SourceParserTest {
	@Mock
	private JavaSource javaSource;
    @Mock
    private FileHelper fileHelper;
	private static final String EXAMPLE_PACKAGE = "pl.example.package";
	private SourceParser sourceParser;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		sourceParser = new SourceParserImpl(fileHelper);
		ArrayList<JavaClass> javaClasses = new ArrayList<JavaClass>();
		when(javaSource.getClasses()).thenReturn(javaClasses);
	}

	@Test
	public void sourceParserShouldSetupFilePackageName() throws Exception {
		// given
		when(javaSource.getPackage()).thenReturn(new DefaultJavaPackage(EXAMPLE_PACKAGE));
		// when
		JavaFile javaFile = sourceParser.parseSource(javaSource);
		// then
		assertThat(javaFile.getPackageName()).isNotNull().isNotEmpty()
				.isEqualTo(EXAMPLE_PACKAGE);
	}

	@Test(dataProvider = "java-class-data")
	public void analyzeClassShouldSetupProperClassType(JavaClass javaClass,
			ClazzType classType) throws Exception {
        JavaSource javaSource1 = mock(JavaSource.class);
        when(javaClass.getSource()).thenReturn(javaSource1);
		// when
		JavaClazz javaClazz = sourceParser.analyzeClass(javaClass);
		// then
		assertThat(javaClazz.getClassType()).isNotNull().isSameAs(classType);
	}

	@Test
	public void sourceParserShouldCheckClassIsThrowable() throws Exception {
		// given
		JavaClass superJavaClass = mock(JavaClass.class);
		String string = new Throwable().getClass().getName();
		when(superJavaClass.isA(string)).thenReturn(true);
		// when
		boolean isThrowable = sourceParser.checkIsThrowable(superJavaClass);
		// then
		assertTrue(isThrowable, "throwable java class ");
	}

	@Test
	public void analyzeClassShouldSetupProperClassName() throws Exception {
		// given
		JavaClass javaClass = mock(JavaClass.class);
		when(javaClass.getName()).thenReturn("sampleName");
        JavaSource javaSource1 = mock(JavaSource.class);
        when(javaClass.getSource()).thenReturn(javaSource1);
		// when
		JavaClazz clazz = sourceParser.analyzeClass(javaClass);
		// then
		assertThat(clazz.getClassName()).isNotNull().isNotEmpty()
				.isEqualTo("sampleName");
	}
	
	@Test
	public void analyzeClassShouldSetupIsInner() throws Exception {
		//given
		JavaClass javaClass = mock(JavaClass.class);
        JavaSource javaSource1 = mock(JavaSource.class);
        when(javaClass.getSource()).thenReturn(javaSource1);
		when(javaClass.isInner()).thenReturn(true);
		//when
		JavaClazz clazz = sourceParser.analyzeClass(javaClass);
		//then
		assertTrue(clazz.isInner());
	}
	@DataProvider(name = "java-class-data")
	public Object[][] classTypeDataProvider() {
		JavaClass abstractClass = mock(JavaClass.class);
		when(abstractClass.isAbstract()).thenReturn(true);
		JavaClass enumClass = mock(JavaClass.class);
		when(enumClass.isEnum()).thenReturn(true);
		JavaClass interfaceClass = mock(JavaClass.class);
		when(interfaceClass.isInterface()).thenReturn(true);
		JavaClass plainClass = mock(JavaClass.class);
		Object[][] classes = new Object[][] {
				{ abstractClass, ClazzType.ABSTRACT },
				{ enumClass, ClazzType.ENUM },
				{ interfaceClass, ClazzType.INTERFACE },
				{ plainClass, ClazzType.CLASS } };
		return classes;
	}
}
