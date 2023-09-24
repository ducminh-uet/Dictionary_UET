package dictionary;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> tumoi = new ArrayList<>();

    public ArrayList<Word> getTumoi() {
        return tumoi;
    }
    public Dictionary(){

    }
    public int binary_timkiem(int start, int n, String target){
        if (n< start) return start;
        int length = tumoi.size();
        int mid = (start+ n) /2;
        if(mid == length) return mid;
        Word word = tumoi.get(mid);
        int sosanh = word.getWord_target().compareTo(target);
        if(sosanh == 0) return -1;
        else if(sosanh > 0) return binary_timkiem(start, mid -1, target);
        return binary_timkiem(mid+1, n, target);
    }
    public void push(Word word) {
        int length = tumoi.size();
        int index = binary_timkiem(0, length - 1, word.getWord_target());
        if (index <= length && index >= 0) tumoi.add(index, word);
    }

    public void setTumoi(ArrayList<Word> tumoi) {
        this.tumoi = tumoi;
    }
}