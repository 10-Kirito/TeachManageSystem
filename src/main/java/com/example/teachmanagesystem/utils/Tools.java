package com.example.teachmanagesystem.utils;

public class Tools {
    public static boolean checkExistFinally(String mulTime1, String mulTime2){
        String [] subtime1 = mulTime1.split(",");
        String [] subtime2 = mulTime2.split(",");

        for (String test : subtime1) {
            for (String test1 : subtime2) {
                Object [] token_3 = parse(test);
                Object [] token_4 = parse(test1);

                if (checkExist(token_3, token_4))
                    return true;
            }
        }
        return false;
    }
    public static Object[] parse(String time) {
        Object[] objects = new Object[3];
        String [] subtime1 = time.split("-");
        objects[0] = subtime1[0].substring(0,1);
        objects[1] = Integer.parseInt(subtime1[0].substring(1));
        objects[2] = Integer.parseInt(subtime1[1]);
        return objects;
    }
    public static boolean checkExist(Object[] a, Object [] b){
        int begin1 = (int) a[1];
        int end1 = (int) a[2];

        int begin2 = (int) b[1];
        int end2 = (int) b[2];

        if (!a[0].equals(b[0])) {
            return false;
        }
        else {
            if (begin1 <begin2){
                if (end1 < begin2)
                    return false;
                else
                    return true;
            }
            else if (begin1 == begin2) {
                return true;
            }
            else{
                if (begin1 > end2)
                    return false;
                else
                    return true;
            }
        }
    }
}
