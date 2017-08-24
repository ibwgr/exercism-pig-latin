import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PigLatinTranslator {
    private final List<String> plusAy = Collections.unmodifiableList(Arrays.asList("yt", "xr", "a", "e", "i", "o", "u"));
    private final List<String> movePlusAy = Collections.unmodifiableList(Arrays.asList("thr", "sch", "ch", "qu", "th", "yt", "xr"));
    private final List<String> moveTemplatePlusAy = Collections.unmodifiableList(Arrays.asList("qu"));
    private final String ay = "ay";

    public String translate(String englishPhrase) {
        String[] words = englishPhrase.split("\\s+");
        return Stream.of(words)
                .map((w) -> pigify(w))
                .collect(Collectors.joining(" "));
    }

    private String pigify(String word) {
        Boolean plusAy = this.plusAy.stream().anyMatch(v -> word.startsWith(v));
        Optional<String> move = this.movePlusAy.stream().filter(v -> word.startsWith(v)).findFirst();
        Optional<String> moveTemplate = moveTemplatePlusAy.stream().filter(v -> word.matches("^[^aeiou]\\w+") && word.indexOf(v) == 1).findFirst();

        if(plusAy){
            return word + ay;
        }else if(move.isPresent()) {
            String movable = move.get();
            return word.substring(movable.length()) + movable + ay;
        }else if(moveTemplate.isPresent()){
            String movable = moveTemplate.get();
            return word.substring(1+movable.length()) + word.charAt(0) + movable + ay;
        }else{
            return word.substring(1) + word.charAt(0) + ay;
        }
    }
}
