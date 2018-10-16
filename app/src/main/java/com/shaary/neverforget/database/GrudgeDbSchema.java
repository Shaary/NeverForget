package com.shaary.neverforget.database;

public class GrudgeDbSchema {
    public static final class GrudgeTable {
        public static final String NAME = "grudges";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String REMIND = "remind";
            public static final String REVENGE = "revenge";
            public static final String REVENGED = "revenged";
            public static final String FORGIVE = "forgive";
            public static final String VICTIM = "victim";
            public static final String DESCRIPTION = "description";
            public static final String GENDER = "gender";
            public static final String TIME = "time";
        }
    }
}
