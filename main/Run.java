public class Run {
    public static void main(String[] args) {
        // Tạo đối tượng từ điển và đối tượng quản lý từ điển
        Dictionary dictionary = new Dictionary();
        DictionaryCommandLine commandLine = new DictionaryCommandLine(dictionary);

        // Hiển thị menu và thực hiện các chức năng
        commandLine.dictionaryAdvanced();
        //THiếu chưa chạy được
    }
}
