package screen;

import dictionary.tool.Translate;
import java.io.*;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String wordResult= Translate.translate("en","vi","As I was writing the final chapters in this book in the summer of 2009, I realized\n" +
                "that software engineering was 40 years old. The name ‘software engineering’ was\n" +
                "proposed in 1969 at a NATO conference to discuss software development problems—\n" +
                "large software systems were late, did not deliver the functionality needed by their\n" +
                "users, cost more than expected, and were unreliable. I did not attend that conference\n" +
                "but, a year later, I wrote my first program and started my professional life in software.\n" +
                "\n" +
                "Progress in software engineering has been remarkable over my professional life-\n" +
                "time. Our societies could not function without large, professional software systems.\n" +
                "\n" +
                "For building business systems, there is an alphabet soup of technologies—J2EE,\n" +
                ".NET, SaaS, SAP, BPEL4WS, SOAP, CBSE, etc.—that support the development and\n" +
                "deployment of large enterprise applications. National utilities and infrastructure—\n" +
                "energy, communications, and transport—all rely on complex and mostly reliable\n" +
                "computer systems. Software has allowed us to explore space and to create the World\n" +
                "Wide Web, the most significant information system in the history of mankind.\n" +
                "Humanity is now faced with a new set of challenges—climate change and extreme\n" +
                "weather, declining natural resources, an increasing world population");
        System.out.println(wordResult);
    }
}
