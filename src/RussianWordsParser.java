import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

//Этот класс умеет обрабатывать русские слова в файлах
public class RussianWordsParser implements Parser{
    private final String fileName;
    private final Map<String, Integer> wordsFrequency;

    public RussianWordsParser(String fileName) {
        //Можно добавить проверку существования файла fileName
        this.fileName = fileName;
        this.wordsFrequency = new TreeMap<>();

    }
    @Override
    public void parse() {
        if(!wordsFrequency.isEmpty()) {
            //Если мы до этого уже считали частоту слов, то ничего не делаем
            return;
        }
        try(FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String s = null;
            while((s = bufferedReader.readLine()) != null) {
                String[] russianWords = s.split("[^А-Яа-я]+");
                for(String word : russianWords) {
                    if(!word.equals("")) {   //Если слово не является пустой строкой
                        //Слово может либо быть в нашем wordsFrequency, либо не быть.
                        //toLowerCase нужен для преобразования к нижнему регистру
                        //Используем его для того, чтобы например слова "яблоко" и "Яблоко" считались одинаковыми
                        String lowerCaseWord = word.toLowerCase();

                        if(wordsFrequency.get(lowerCaseWord) == null) {
                            //Слово встретилось в первый раз. На данный момент его частота равна единице
                            wordsFrequency.put(lowerCaseWord, 1);
                        }
                        else {
                            //Достаём старую частоту слова. Увеличиваем ее на единицу.
                            int oldFrequency = wordsFrequency.get(lowerCaseWord);
                            wordsFrequency.replace(lowerCaseWord, oldFrequency + 1);
                        }
                    }
                }
            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Map<String, Integer> frequency() {
        return wordsFrequency;
    }
    public String mostFrequentWord() {
        int biggestFrequency = 0;
        String mostFrequentWordFound = null;
        for (Map.Entry<String, Integer> entry : wordsFrequency.entrySet()) {
            if (entry.getValue() > biggestFrequency) {
                biggestFrequency = entry.getValue();
                mostFrequentWordFound = entry.getKey();
            }
        }
        String wordWithBiggestFrequency = String.format("Слово: %s , встречается: %d раз. Это самое часто используемое слово\n\n", mostFrequentWordFound, biggestFrequency);
        return wordWithBiggestFrequency;
    }
    //возвращает среднюю частоту каждого слова.
    public double averageFrequency(){
        //Для реализации метода нам нужно просуммировать все значения Map’a и поделить эту сумму на размер мапа.
        int sumOfAllFrequency = 0;
        for (Map.Entry<String, Integer> entry : wordsFrequency.entrySet()) {
            sumOfAllFrequency = sumOfAllFrequency + entry.getValue();
        }
        return sumOfAllFrequency / wordsFrequency.size();
    }
}
