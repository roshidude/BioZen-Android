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
package com.t2.compassionMeditation;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.t2.biofeedback.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * A static class that provides a global abstraction of an app's shared 
 * preferences.
 * @author robbiev
 *
 */
public class SharedPref {
	private static SharedPreferences sSharedPref;
	
	private static void init(Context c) {
		if(sSharedPref == null) {
			sSharedPref = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
		}
	}
	
	public SharedPreferences getSharedPreferences(Context c) {
		init(c);
		return sSharedPref;
	}

	/**
	 * Returns the parameter name for a specified BT address
	 * @param btAddress
	 * @return
	 * 
	 * Since the table in Shared pref is stored with parameter names
	 * (the only thing known apriori)
	 * we must interate through the names to see of we have a match for the address
	 * 
	 */
	public static String getParamForDevice(Context c, String btAddress) {
		String result = null;
		
		String[] paramNamesStringArray = c.getResources().getStringArray(R.array.parameter_names);		
		for (String name : paramNamesStringArray) {
			String add = SharedPref.getString(c, name, null);
			if (add != null && add.equalsIgnoreCase(btAddress))
				result = name;
		}
		
		if (result != null && result.equalsIgnoreCase(paramNamesStringArray[0])) {
			result = null;
		}
		return result;
	}

	public static String getDeviceForParam(Context c, String param) {
		return SharedPref.getString(c, param, null);

	}
	
	
	
	
	/**
	 * Associates a BT address with a parameter name
	 * Also removes this addresss from all other parameter names
	 *  
	 * @param paramName
	 * @param btAddress
	 */
	public static void setParamForDevice(Context c, String paramName, String btAddress) {
		 
		// Now also need to make sure to remove this address from any other parameter names
		String[] paramNamesStringArray = c.getResources().getStringArray(R.array.parameter_names);		
		for (String name : paramNamesStringArray) {
			
			if (name.equalsIgnoreCase(paramName)) {
				// Assign this address to a parameter name
				SharedPref.putString(c, paramName, btAddress);
			}
			else {
				String add = SharedPref.getString(c, name, null);
				if (add != null && add.equalsIgnoreCase(btAddress))
					SharedPref.putString(c, name, null);
				
			}
				
		}
	}
	
	
	
	public static String[] getValues(SharedPreferences sharedPref, String key, String separator, String[] defaultValue) {
		String dataStr = sharedPref.getString(key, "!<[NULLFOUND]>[");
		if(dataStr.equals("!<[NULLFOUND]>[")) {
			return defaultValue;
		}
		return dataStr.split(separator);
	}
	
	public static void setValues(SharedPreferences sharedPref, String key, String separator, String[] values) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < values.length; ++i) {
			sb.append(values[i]);
			sb.append(separator);
		}
		sharedPref.edit().putString(key, sb.toString()).commit();
	}
	
	public static void setIntValues(SharedPreferences sharedPref, String key, String separator, int[] values) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < values.length; ++i) {
			sb.append(Integer.toString(values[i]));
			sb.append(separator);
		}
		sharedPref.edit().putString(key, sb.toString()).commit();
	}
	
	public static int[] getIntValues(SharedPreferences sharedPref, String key, String separator) {
		String dataStr = sharedPref.getString(key, "!<[NULLFOUND]>[");
		if(dataStr.equals("!<[NULLFOUND]>[")) {
			return null;
		}
		String strResults[] = dataStr.split(separator);
		int numItems = strResults.length;
		int results[] = new int[numItems]; 
		for (int i = 0; i < numItems; i++ ) {
			results[i] = Integer.parseInt(strResults[i]);
		}
		return results;
	}
	
	
	
	/**
	 * @see SharedPreferences.getBoolean
	 * @param c
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context c, String key, boolean defValue) {
		init(c);
		return sSharedPref.getBoolean(key, defValue);
	}
	
	/**
	 * @see SharedPreferences.getFloat
	 * @param c
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static float getFloat(Context c, String key, float defValue) {
		init(c);
		return sSharedPref.getFloat(key, defValue);
	}
	
	/**
	 * @see SharedPreferences.getInt
	 * @param c
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(Context c, String key, int defValue) {
		init(c);
		return sSharedPref.getInt(key, defValue);
	}
	
	/**
	 * @see SharedPreferences.getLong
	 * @param c
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLong(Context c, String key, long defValue) {
		init(c);
		return sSharedPref.getLong(key, defValue);
	}
	
	/**
	 * @see SharedPreferences.getString
	 * @param c
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context c, String key, String defValue) {
		init(c);
		return sSharedPref.getString(key, defValue);
	}
	
	/**
	 * @see SharedPreferences.putBoolean
	 * @param c
	 * @param key
	 * @param value
	 */
	public static void putBoolean(Context c, String key, boolean value) {
		init(c);
		sSharedPref.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * @see SharedPreferences.putFloat
	 * @param c
	 * @param key
	 * @param value
	 */
	public static void putFloat(Context c, String key, float value) {
		init(c);
		sSharedPref.edit().putFloat(key, value).commit();
	}
	
	/**
	 * @see SharedPreferences.putInt
	 * @param c
	 * @param key
	 * @param value
	 */
	public static void putInt(Context c, String key, int value) {
		init(c);
		sSharedPref.edit().putInt(key, value).commit();
	}
	
	/**
	 * @see SharedPreferences.putLong
	 * @param c
	 * @param key
	 * @param value
	 */
	public static void putLong(Context c, String key, long value) {
		init(c);
		sSharedPref.edit().putLong(key, value).commit();
	}
	
	/**
	 * @see SharedPreferences.putString
	 * @param c
	 * @param key
	 * @param value
	 */
	public static void putString(Context c, String key, String value) {
		init(c);
		sSharedPref.edit().putString(key, value).commit();
	}
	
	
	public static class Analytics {
		public static boolean isEnabled(Context c) {
			return getBoolean(c, "analytics_enabled", false);
		}
		
		public static void setIsEnabled(Context c, boolean b) {
			putBoolean(c, "analytics_enabled", b);
		}
	}
	
	public static class RemoteStackTrace {
		public static boolean isEnabled(Context c) {
			return getBoolean(c, "remote_stack_trace_enabled", true);
		}
		
		public static void setIsEnabled(Context c, boolean b) {
			putBoolean(c, "remote_stack_trace_enabled", b);
		}
	}
	
	public static long getBioSessionStartTime(SharedPreferences sharedPref) {
		return sharedPref.getLong("bio_session_start_time", 0);
	}
	
	public static void setBioSessionStartTime(SharedPreferences sharedPref, long time) {
		sharedPref.edit().putLong("bio_session_start_time", time).commit();
		return;
	}
	
	public static long getBioSessionId(SharedPreferences sharedPref) {
		return sharedPref.getLong("bio_session_start_time", 0);
	}
	
	public static void setBioSessionId(SharedPreferences sharedPref, long time) {
		sharedPref.edit().putLong("bio_session_start_time", time).commit();
		return;
	}
	
	public static long getBioSessionEndTime(SharedPreferences sharedPref) {
		return sharedPref.getLong("bio_session_end_time", 0);
	}
	
	public static void setBioSessionEndTime(SharedPreferences sharedPref, long time) {
		sharedPref.edit().putLong("bio_session_end_time", time).commit();
		return;
	}	
	public static class Security {
		public static boolean isEnabled(Context c) {
			return getBoolean(c, "security_enabled", false);
		}
		
		
		public static boolean doesPasswordMatch(Context c, String inputPassword) {
			return getString(c, "security_password", "").equals(md5(inputPassword));
		}
		
		public static boolean isPasswordSet(Context c) {
			return getString(c, "security_password", "").length() > 0;
		}
		
		
		public static String getQuestion1(Context c) {
			return getQuestion(c, 1, sSharedPref);
		}
		
		public static String getQuestion2(Context c) {
			return getQuestion(c, 2, sSharedPref);
		}
		
		public static boolean doesAnswer1Match(Context c, String answer) {
			return doesAnswerMatch(c, 1, answer);
		}
		
		public static boolean isAnswer1Set(Context c) {
			return isAnswerSet(c, 1);
		}
		
		public static boolean doesAnswer2Match(Context c, String answer) {
			return doesAnswerMatch(c, 2, answer);
		}
		
		public static boolean isAnswer2Set(Context c) {
			return isAnswerSet(c, 2);
		}
		
		private static String getQuestion(Context c, int index, SharedPreferences sharedPref) {
			return getString(c, "security_question"+index, "");
		}
		
		private static boolean doesAnswerMatch(Context c, int index, String answer) {
			return getString(c, "security_answer"+index, "").equals(md5(cleanString(answer)));
		}
		
		private static boolean isAnswerSet(Context c, int index) {
			return getString(c, "security_answer"+index, "").length() > 0; 
		}
		
		public static void setEnabled(Context c, boolean b) {
			putBoolean(c, "security_enabled", b);
		}
		
		public static void setPassword(Context c, String password) {
			if(password != null && password.length() > 0) {
				putString(c, "security_password", md5(password.trim()));
			}
		}
		
		public static void setChallenge1(Context c, String question, String answer) {
			setChallenge(c, 1, question, answer);
		}
		
		public static void setChallenge2(Context c, String question, String answer) {
			setChallenge(c, 2, question, answer);
		}
		
		private static void setChallenge(Context c, int index, String question, String answer) {
			putString(c, "security_question"+ index, question);
			if(answer != null && answer.length() > 0) {
				putString(c, "security_answer"+ index, md5(cleanString(answer)));
			}
		}
		
		private static String cleanString(String str) {
			return str.trim().toLowerCase().replaceAll("[^a-z0-9]", "");
		}

	}
	
	private static String md5(String s) {
		MessageDigest m = null;
		try {
			m = java.security.MessageDigest.getInstance("MD5");
			
		} catch (NoSuchAlgorithmException e) {
			// ignore
		}
		
		if (m != null) m.update(s.getBytes(), 0, s.length());
		String hash = new BigInteger(1, m.digest()).toString();
		return hash;
	}
	
	
	
}
