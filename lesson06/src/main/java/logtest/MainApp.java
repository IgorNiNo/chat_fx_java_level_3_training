package logtest;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp {
//    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) {
//        logger.log(Level.INFO, "Test Info");
//        logger.log(Level.SEVERE, "Test Severe");

    }


    public Integer[] transformArray(int[] arr) {
        int i;
        int NUM_EQUAL = 4;
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == NUM_EQUAL) {
                break;
            }
        }
        if (i == -1) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.out.println("В массиве нет элементов, содержащих четвёрки");
            }
        } else if ((i + 1) < arr.length) {
            for (int j = i + 1; j < arr.length; j++) {
                arrayList.add(arr[j]);
            }
        }
        Integer[] arrNew = arrayList.toArray(new Integer[arrayList.size()]);
        return arrNew;
    }

    public boolean checkArray(int[] arr) {
        int CHECK_NUM1 = 1;
        int CHECK_NUM2 = 4;
        boolean isNum1 = false;
        boolean isNum2 = false;
        boolean isNumX = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == CHECK_NUM1) {
                isNum1 = true;
            } else if (arr[i] == CHECK_NUM2) {
                isNum2 = true;
            } else {
                isNumX = true;
                break;
            }
        }
        return isNum1 && isNum2 && !isNumX;
    }


}