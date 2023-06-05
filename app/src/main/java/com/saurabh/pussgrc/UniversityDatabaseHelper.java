package com.saurabh.pussgrc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class UniversityDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "university.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_UNIVERSITY = "university";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DEAN_NAME = "dean_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_RANK = "rank";
    private static final String COLUMN_IMAGE = "image";

    private static final String TABLE_FACULTY = "faculty";
    private static final String COLUMN_FACULTY_ID = "faculty_id";
    private static final String COLUMN_FACULTY_NAME = "faculty_name";
    private static final String COLUMN_PROFILE = "profile";
    private static final String COLUMN_ENTRY_AVERAGE = "entry_average";

    public UniversityDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUniversityTableQuery = "CREATE TABLE " + TABLE_UNIVERSITY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DEAN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_RANK + " INTEGER,"
                + COLUMN_IMAGE + " TEXT"
                + ")";
        db.execSQL(createUniversityTableQuery);

        String createFacultyTableQuery = "CREATE TABLE " + TABLE_FACULTY + "("
                + COLUMN_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FACULTY_NAME + " TEXT,"
                + COLUMN_PROFILE + " TEXT,"
                + COLUMN_ENTRY_AVERAGE + " REAL,"
                + COLUMN_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ID + ") REFERENCES " + TABLE_UNIVERSITY + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(createFacultyTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACULTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIVERSITY);
        onCreate(db);
    }

    public void addUniversity(University university) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, university.getName());
        values.put(COLUMN_ADDRESS, university.getAddress());
        values.put(COLUMN_DESCRIPTION, university.getDescription());
        values.put(COLUMN_DEAN_NAME, university.getDeanName());
        values.put(COLUMN_EMAIL, university.getEmail());
        values.put(COLUMN_RANK, university.getRank());
        values.put(COLUMN_IMAGE, university.getImage().toString());

        long universityId = db.insert(TABLE_UNIVERSITY, null, values);

        Faculty[] faculties = university.getFaculties();
        if (faculties != null) {
            for (Faculty faculty : faculties) {
                ContentValues facultyValues = new ContentValues();
                facultyValues.put(COLUMN_FACULTY_NAME, faculty.getName());
                facultyValues.put(COLUMN_PROFILE, faculty.getProfile());
                facultyValues.put(COLUMN_ENTRY_AVERAGE, faculty.getEntryAverage());
                facultyValues.put(COLUMN_ID, universityId);

                db.insert(TABLE_FACULTY, null, facultyValues);
            }
        }

        db.close();
    }

    public List<University> getAllUniversities() {
        List<University> universities = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_UNIVERSITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                long universityId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String deanName = cursor.getString(cursor.getColumnIndex(COLUMN_DEAN_NAME));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                int rank = cursor.getInt(cursor.getColumnIndex(COLUMN_RANK));
                Uri imageUri = Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));

                Faculty[] faculties = getFacultiesForUniversity(db, universityId);

                University university = new University(name, address, faculties, description, deanName, email, rank, imageUri);
                universities.add(university);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return universities;
    }

    private Faculty[] getFacultiesForUniversity(SQLiteDatabase db, long universityId) {
        List<Faculty> faculties = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_FACULTY + " WHERE " + COLUMN_ID + " = " + universityId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String facultyName = cursor.getString(cursor.getColumnIndex(COLUMN_FACULTY_NAME));
                String profile = cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE));
                double entryAverage = cursor.getDouble(cursor.getColumnIndex(COLUMN_ENTRY_AVERAGE));

                Faculty faculty = new Faculty(facultyName, profile, entryAverage);
                faculties.add(faculty);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return faculties.toArray(new Faculty[0]);
    }

    public static class University {
        private String name;
        private String address;
        private Faculty[] faculties;
        private String description;
        private String deanName;
        private String email;
        private int rank;
        private Uri image;

        public University(String name, String address, Faculty[] faculties, String description, String deanName, String email, int rank, Uri image) {
            this.name = name;
            this.address = address;
            this.faculties = faculties;
            this.description = description;
            this.deanName = deanName;
            this.email = email;
            this.rank = rank;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public Faculty[] getFaculties() {
            return faculties;
        }

        public String getDescription() {
            return description;
        }

        public String getDeanName() {
            return deanName;
        }

        public String getEmail() {
            return email;
        }

        public int getRank() {
            return rank;
        }

        public Uri getImage() {
            return image;
        }
    }

    public static class Faculty {
        private String name;
        private String profile;
        private double entryAverage;

        public Faculty(String name, String profile, double entryAverage) {
            this.name = name;
            this.profile = profile;
            this.entryAverage = entryAverage;
        }

        public String getName() {
            return name;
        }

        public String getProfile() {
            return profile;
        }

        public double getEntryAverage() {
            return entryAverage;
        }
    }
}

