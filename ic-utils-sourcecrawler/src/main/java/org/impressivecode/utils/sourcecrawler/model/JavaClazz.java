/*
ImpressiveCode Depress Framework Source Crawler
Copyright (C) 2013 ImpressiveCode contributors

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.impressivecode.utils.sourcecrawler.model;

/**
 * 
 * @author Paweł Nosal
 * 
 */

public class JavaClazz {
	private String className;
	private ClazzType classType;
	private boolean isException;
	private boolean isTest;
	private boolean isInner;
	
	public String getClassName() {
		return className;
	}

	public ClazzType getClassType() {
		return classType;
	}

	public boolean isException() {
		return isException;
	}

	public boolean isTest() {
		return isTest;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClassType(ClazzType classType) {
		this.classType = classType;
	}

	public void setException(boolean isException) {
		this.isException = isException;
	}

	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

	public boolean isInner() {
		return isInner;
	}

	public void setInner(boolean isInner) {
		this.isInner = isInner;
	}
}
