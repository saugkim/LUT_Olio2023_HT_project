package ht.lutemon;

import androidx.annotation.NonNull;

public enum Team {
    WHITE {
        @NonNull
        @Override
        public String toString() {
            return "White";
        }
    },
    GREEN {
        @NonNull
        @Override
        public String toString() {
            return "Green";
        }
    },
    PINK {
        @NonNull
        @Override
        public String toString() {
            return "Pink";
        }
    },
    ORANGE {
        @NonNull
        @Override
        public String toString() {
            return "Orange";
        }
    },
    BLACK {
        @NonNull
        @Override
        public String toString() {
            return "Black";
        }
    }
}