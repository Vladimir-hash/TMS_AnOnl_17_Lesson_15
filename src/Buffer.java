public class Buffer {
    private static final int CAPACITY = 10;
    private  final int[] myArray = new int[CAPACITY];
    private  int currentIndex = 0;
    public  int[] getMyArray() {
        return myArray;
    }
    public  void addIndex() {
        currentIndex++;
    }
    public  void removeIndex() {
        currentIndex--;
    }
    public  int getCurrentIndex() {
        return currentIndex;
    }
}
