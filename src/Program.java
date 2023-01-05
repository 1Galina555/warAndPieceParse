import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Program {
    public static void main(String[] args) {

        RussianWordsParser fileParser = new RussianWordsParser("War and piece.txt");
        fileParser.parse();
        Map<String, Integer> frequency = fileParser.frequency();

        double averageWordFrequency = fileParser.averageFrequency();

        try(FileWriter fileWriter = new FileWriter("statistics.txt")) {
            //Записываем в файл самое часто используемое слово
            fileWriter.write(fileParser.mostFrequentWord());
            //Записываем в файл среднюю частоту слова.
            fileWriter.write("Средняя частота каждого слова = "+averageWordFrequency+"\n\n");
            //записываем файл все слова с из частотами.
            for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
                String s = String.format("Слово: %s встречается -  %d раз\n",entry.getKey(),entry.getValue());
                fileWriter.write(s);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        System.out.println(fileParser.mostFrequentWord());
        System.out.println("Средняя частота каждого слова = "+averageWordFrequency);
    }
}
