package toa_project;

import java.util.ArrayList;

public class Languages {

    public String checkLanguage(String symbols, ArrayList powers) {
        boolean lang1 = false;
        boolean lang2 = false;
        boolean lang3 = false;
        int p1 = (int) powers.get(0);
        int p2 = (int) powers.get(1);
        int p3 = (int) powers.get(2);
        if (symbols.length() == 3) {
            if(p1 == p2 && p1 == p3)
                lang1 = true;
            else if (p1 == p3 && p2 == (2 * p1))
                lang2 = true;
            else if (p1 % 2 == 0 && p2 == (p1/2) && p1 == p3)
                lang3 = true;
        }
        if (lang2)
            return "lang2";
        else if (lang3)
            return "lang3";
        
        return "none";
    }

    public boolean[] rule(String symbols, String current, int index, ArrayList powers) { //a^n b^n c^n
        boolean[] arr = {false, false, false, false};
        if (index == 0 && !powers.get(0).equals(0)) {
            arr[0] = true;
        } else {
            if (index != 2 && !powers.get(1).equals(0)) {
                arr[0] = false;
                arr[2] = true;
                arr[1] = true;
            } else if (!powers.get(2).equals(0)) {
                arr[2] = false;
                arr[1] = false;
                arr[3] = true;
            }
        }
        return arr;
    }
}
