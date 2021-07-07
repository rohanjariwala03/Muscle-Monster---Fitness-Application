package com.example.musclemonster_fitnessapp.POJOClasses;

public class Exercise_Sub_Pojo {

    //Initializing data
        private String ExerciseName,ExerciseCat, ExerciseDesc,FKey,ImageUri;

        public Exercise_Sub_Pojo(String ExerciseName, String ExerciseCat, String ExerciseDesc,String ImageUri, String FKey) {
            this.ExerciseName=ExerciseName;
            this.ExerciseCat=ExerciseCat;
            this.ExerciseDesc=ExerciseDesc;
            this.ImageUri=ImageUri;

            this.FKey = FKey;
        }

        public String getFKey() {
            return FKey;
        }

        public void setFKey(String FKey) {
            this.FKey = FKey;
        }

        //Constructor class
        public Exercise_Sub_Pojo() {
        }

        //Getter and Setter for all variable

        public void setExerciseName(String exerciseName) {
            this.ExerciseName = exerciseName;
        }

        public String getExerciseCat() {
            return ExerciseCat;
        }

        public void setExerciseCat(String exerciseCat) {
            ExerciseCat = exerciseCat;
        }

        public String getExerciseDesc() {
            return ExerciseDesc;
        }

        public void setExerciseDesc(String exerciseDesc) {
            ExerciseDesc = exerciseDesc;
        }

        public String getExerciseName() {
            return ExerciseName;
        }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getImageUri() {
        return ImageUri;
    }

}
