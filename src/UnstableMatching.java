/**
 * Created by Art on 7/27/2016.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class UnstableMatching {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Project 1: Stable Match Check");
        String yesOrNo = "y";
        String fileName;
        Scanner input = new Scanner(System.in);
        while (yesOrNo.equals("y")) {
            try {
                System.out.println();
                System.out.println("Please enter input file name");
                fileName = input.nextLine();
                File file = new File(fileName);
                Scanner fileInput = new Scanner(file);
                if (fileInput.hasNext()) {
                    CheckIfUnstable(fileInput);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist");
            }
            System.out.println();
            System.out.println("Check another file? y or n");
            yesOrNo = input.nextLine();
        }
        System.exit(0);
    }

    public static void CheckIfUnstable(Scanner fileInput) {
        int size = fileInput.nextInt();
        //Varibles for for loop, declared here so it doens't have to keep redeclaring as int.
        int i, j;
        Map<Integer, ArrayList<Integer>> malePreferences = new HashMap<>();
        Map<Integer, ArrayList<Integer>> femalePreferences = new HashMap<>();
        Map<Integer, Integer> matching = new HashMap<>();
        Map<String, String> instability = new HashMap<>();

        for (i = 1; i <= size; i++) {
            malePreferences.put(i, new ArrayList<>());
            femalePreferences.put(i, new ArrayList<>());
        }

        //Initialize malePrefernces
        for (i = 1; i <= size; i++) {
            //Iterate through lines
            for (j = 1; j <= size; j++) {
                malePreferences.get(i).add(fileInput.nextInt());
            }
        }

        //Initialize femalePrefernces
        for (i = 1; i <= size; i++) {
            //Iterate through lines
            for (j = 1; j <= size; j++) {
                femalePreferences.get(i).add(fileInput.nextInt());
            }
        }

        //Initialize matching
        for (i = 1; i <= size; i++) {
            matching.put(i, fileInput.nextInt());
        }

        //Save for Viewing mappings
//        for (Integer key : malePreferences.keySet()) {
//            System.out.println(key + " " + malePreferences.get(key));
//        }
//
//        for (Integer key : femalePreferences.keySet()) {
//            System.out.println(key + " " + femalePreferences.get(key));
//        }
//
//        for (Integer key : matching.keySet()) {
//            System.out.println(key + " " + matching.get(key));
//        }

        //NOTE!!!! ArrayList will start at index 0.
        for (i = 1; i <= size; i++) {
            //Check matching to guys preference
            if (matching.get(i) == malePreferences.get(i).get(0)) {
                //Male has his best preference go to next matching
            } else {
                //Check all higher prefered females and see if they would rather be paired with him (instability)

                //Get the 1 higher than current matching
                int indexOfMatching = malePreferences.get(i).indexOf(matching.get(i)) - 1;
                int femaleAtIndex;
                int curFemalePartner;
                //Iterate through males higher prefered
                while (indexOfMatching >= 0 && instability.isEmpty()) {
                    femaleAtIndex = malePreferences.get(i).get(indexOfMatching);
                    //Grab the current Females Partner
                    curFemalePartner = (int) getKeyFromValue(matching, femaleAtIndex);
                    //Check if guy is higher on the females preference list
                    if(femalePreferences.get(femaleAtIndex).indexOf(i) < femalePreferences.get(femaleAtIndex).indexOf(curFemalePartner)){
                        instability.put("M" + i, "W" + femaleAtIndex);
                    }
                    indexOfMatching--;
                }
            }
        }
        if(instability.isEmpty()){
            System.out.println("Stable.");
        }
        else{
            for (Map.Entry<String, String> entry : instability.entrySet()) {
                System.out.println("Not stable. (" + entry.getKey() + "," + entry.getValue() + ") is one instability.");
            }
        }

    }

    //Got from http://www.java2s.com/Code/Java/Collections-Data-Structure/GetakeyfromvaluewithanHashMap.htm
    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

}
