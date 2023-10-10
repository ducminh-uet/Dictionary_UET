package screen;

import dictionary.tool.Translate;
import java.io.*;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String wordResult= Translate.translate("en","zh-CN","There is an incredible amount of information on software engineering available on the\n" +
                "Web and some people have questioned if textbooks like this one are still needed.\n" +
                "However, the quality of available information is very patchy, information is sometimes\n" +
                "presented badly and it can be hard to find the information that you need. Consequently,\n" +
                "I believe that textbooks still have an important role to play in learning. They serve as a\n" +
                "roadmap to the subject and allow information on method and techniques to be organized\n" +
                "and presented in a coherent and readable way. They also provide a starting point for\n" +
                "deeper exploration of the research literature and material available on the Web.\n" +
                "I strongly believe that textbooks have a future but only if they are integrated with\n" +
                "and add value to material on the Web. This book has therefore been designed as a\n" +
                "hybrid print/web text in which core information in the printed edition is linked to\n" +
                "supplementary material on the Web. Almost all chapters include specially written\n" +
                "‘web sections’ that add to the information in that chapter. There are also four ‘web\n" +
                "chapters’ on topics that I have not covered in the print version of the book.\n" +
                "The website that is associated with the book is:");
        System.out.println(wordResult);
    }
}
