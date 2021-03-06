/*****************************************************************
BioZen

Copyright (C) 2011 The National Center for Telehealth and 
Technology

Eclipse Public License 1.0 (EPL-1.0)

This library is free software; you can redistribute it and/or
modify it under the terms of the Eclipse Public License as
published by the Free Software Foundation, version 1.0 of the 
License.

The Eclipse Public License is a reciprocal license, under 
Section 3. REQUIREMENTS iv) states that source code for the 
Program is available from such Contributor, and informs licensees 
how to obtain it in a reasonable manner on or through a medium 
customarily used for software exchange.

Post your updates and modifications to our GitHub or email to 
t2@tee2.org.

This library is distributed WITHOUT ANY WARRANTY; without 
the implied warranty of MERCHANTABILITY or FITNESS FOR A 
PARTICULAR PURPOSE.  See the Eclipse Public License 1.0 (EPL-1.0)
for more details.
 
You should have received a copy of the Eclipse Public License
along with this library; if not, 
visit http://www.opensource.org/licenses/EPL-1.0

*****************************************************************/
package com.t2.compassionDB;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


//@DatabaseTable(tableName = "accounts")
@DatabaseTable
public class BioUser{

	public static final String NAME_FIELD_NAME = "name";
	
	// id is generated by the database and set on the object automagically
	@DatabaseField(generatedId = true)
	int id;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	public String name;

	@DatabaseField
	int minTheta;

	@DatabaseField
	int maxTheta;

	@DatabaseField
	int sessionCount;

	@DatabaseField
	Date createDate;

	@DatabaseField
	boolean even;
	
	@ForeignCollectionField
	private ForeignCollection<BioSession> sessions;	
	

	BioUser() {
		// needed by ormlite
	}

	public BioUser(String name, long currentDate) {
		this.createDate = new Date(currentDate);
		this.name = name;
	}

	public ForeignCollection<BioSession> getSessions() {
		return sessions;
	}	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(", ").append("string=").append(name);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.S");
		sb.append(", ").append("even=").append(even);
		

		return sb.toString();
	}
}
