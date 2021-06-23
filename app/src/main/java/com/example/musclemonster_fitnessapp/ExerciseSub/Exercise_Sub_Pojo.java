package com.example.musclemonster_fitnessapp.ExerciseSub;

public class Exercise_Sub_Pojo {

    //Initializing data
        private String ExerciseName,ExerciseCat, ExerciseDesc,FKey;

        public Exercise_Sub_Pojo(String ExerciseName, String ExerciseCat, String ExerciseDesc) {
            this.ExerciseName=ExerciseName;
            this.ExerciseCat=ExerciseCat;
            this.ExerciseDesc=ExerciseDesc;
        }

        public Exercise_Sub_Pojo(String ExerciseName, String ExerciseCat, String ExerciseDesc, String FKey) {
            this.ExerciseName = ExerciseName;
            this.ExerciseCat=ExerciseCat;
            this.ExerciseDesc=ExerciseDesc;

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



}
