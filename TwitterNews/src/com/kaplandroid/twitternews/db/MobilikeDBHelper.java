package com.kaplandroid.twitternews.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kaplandroid.twitternews.model.TweetForDB;

/**
 * 
 * 
 * SQLITE Helper class. Controls database processes
 * 
 * 
 * @author KAPLANDROID
 * 
 */
public class MobilikeDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "mobilikeDB";

	// table name
	private static final String TABLE_TWEET = "tweet";
	private static final String TABLE_SOURCE = "source";
	private static final String TABLE_FEEDBACK = "feedback";
	private static final String TABLE_TWEET_HAS_SOURCE = "tweetHasSource";
	private static final String TABLE_TWEET_HAS_FEEDBACK = "tweetHasFeedback";

	public MobilikeDBHelper(Context context) {
		super(context, DATABASE_NAME, null, 3);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		//

		String sqlTweet = "CREATE TABLE " + TABLE_TWEET + "(dbID INTEGER PRIMARY KEY,tweetID TEXT,weblink TEXT)";
		Log.d("DBHelper", "SQL : " + sqlTweet);
		db.execSQL(sqlTweet);

		//

		String sqlFeedback = "CREATE TABLE " + TABLE_FEEDBACK + "(feedbackID INTEGER,feedbackName TEXT)";
		Log.d("DBHelper", "SQL : " + sqlFeedback);
		db.execSQL(sqlFeedback);

		//

		String sqlSource = "CREATE TABLE " + TABLE_SOURCE + "(sourceID INTEGER PRIMARY KEY,sourceName TEXT)";
		Log.d("DBHelper", "SQL : " + sqlSource);
		db.execSQL(sqlSource);

		//

		String sqlTweetHasFeedback = "CREATE TABLE " + TABLE_TWEET_HAS_FEEDBACK + "(dbID INTEGER,feedbackID INTEGER)";
		Log.d("DBHelper", "SQL : " + sqlTweetHasFeedback);
		db.execSQL(sqlTweetHasFeedback);

		//

		String sqlTweetHasSource = "CREATE TABLE " + TABLE_TWEET_HAS_SOURCE + "(dbID INTEGER,sourceID INTEGER)";
		Log.d("DBHelper", "SQL : " + sqlTweetHasSource);
		db.execSQL(sqlTweetHasSource);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEET);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOURCE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEET_HAS_FEEDBACK);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEET_HAS_SOURCE);
		onCreate(db);
	}

	public void insertTweet(TweetForDB tweet) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("weblink", tweet.getWeblink());
		values.put("tweetID", tweet.getTweetID());

		db.insert(TABLE_TWEET, null, values);
		db.close();
	}

	public void insertFeedback(int id, String feedbackName) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("feedbackID", id);
		values.put("feedbackName", feedbackName);

		db.insert(TABLE_FEEDBACK, null, values);
		db.close();
	}

	public void insertSource(String sourceName) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("sourceName", sourceName);

		db.insert(TABLE_SOURCE, null, values);
		db.close();
	}

	public void insertSourceToTweet(int tweetDBID, int sourceID) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("dbID", tweetDBID);
		values.put("sourceID", sourceID);

		db.insert(TABLE_TWEET_HAS_SOURCE, null, values);
		db.close();
	}

	public void insertFeedbackToTweet(int tweetDBID, int feedbackID) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("dbID", tweetDBID);
		values.put("feedbackID", feedbackID);

		db.insert(TABLE_TWEET_HAS_FEEDBACK, null, values);
		db.close();
	}

	public List<TweetForDB> getAllTweets() {
		List<TweetForDB> tweets = new ArrayList<TweetForDB>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(TABLE_TWEET, new String[] { "dbID", "tweetID", "weblink" }, null, null, null, null,
				null);

		while (cursor.moveToNext()) {
			TweetForDB tweet = new TweetForDB();
			tweet.setDbID(cursor.getInt(0));
			tweet.setTweetID(cursor.getString(1));
			tweet.setWeblink(cursor.getString(2));
			tweets.add(tweet);
		}

		return tweets;
	}

	public int getSourceIDBySourceName(String sourceName) {
		SQLiteDatabase db = this.getWritableDatabase();

		String sqlQuery = "SELECT  sourceID FROM " + TABLE_SOURCE + " where sourceName=='" + sourceName + "'";
		Cursor cursor = db.rawQuery(sqlQuery, null);

		while (cursor.moveToNext()) {
			return cursor.getInt(0);
		}

		return -1;
	}

	public int getTweetDBIDByTweetID(String tweetID) {
		SQLiteDatabase db = this.getWritableDatabase();

		String sqlQuery = "SELECT  dbID FROM " + TABLE_TWEET + " where tweetID=='" + tweetID + "'";
		Cursor cursor = db.rawQuery(sqlQuery, null);

		while (cursor.moveToNext()) {
			return cursor.getInt(0);
		}

		return -1;
	}

	public String getWeblinkBydbID(int dbID) {
		SQLiteDatabase db = this.getWritableDatabase();

		String sqlQuery = "SELECT  weblink FROM " + TABLE_TWEET + " where dbID=='" + dbID + "'";
		Cursor cursor = db.rawQuery(sqlQuery, null);

		while (cursor.moveToNext()) {
			return cursor.getString(0);
		}

		return "";
	}

	public int getTotalTweetCount() {
		SQLiteDatabase db = this.getWritableDatabase();

		String sqlQuery = "SELECT  count(dbID) FROM " + TABLE_TWEET;
		Cursor cursor = db.rawQuery(sqlQuery, null);

		while (cursor.moveToNext()) {
			return cursor.getInt(0);
		}

		return -1;
	}

	public void clearTweets() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TWEET, null, null);
	}

}
