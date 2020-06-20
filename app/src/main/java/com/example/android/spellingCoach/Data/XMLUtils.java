package com.example.android.spellingCoach.Data;

import android.util.Xml;

import com.example.android.spellingCoach.SpellWord;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byilm on 5/13/2017.
 */

public class XMLUtils {
    public static final String XMLexample =
            "<entry_list version=\"1.0\">" +
                    "<entry id=\"prestidigitation\">" +
                    "<ew>prestidigitation</ew>" +
                    "<subj>ET</subj>" +
                    "<hw>pres*ti*dig*i*ta*tion</hw>" +
                    "<sound>" +
                    "<wav>presti01.wav</wav>" +
                    "<wpr>+pres-tu-+di-ju-!tA-shun</wpr>" +
                    "</sound>" +
                    "<pr>ˌpres-tə-ˌdi-jə-ˈtā-shən</pr>" +
                    "<fl>noun</fl>" +
                    "<et>" +
                    "French, from <it>prestidigitateur</it> prestidigitator, from <it>preste</it>" +
                    "nimble, quick (from Italian <it>presto</it>) + Latin <it>digitus</it>" +
                    "finger <ma>digit</ma>" +
                    "</et>" +
                    "<def>" +
                    "<date>1859</date>" +
                    "<dt>" +
                    ":" +
                    "<sx>sleight of hand</sx>" +
                    "<sx>legerdemain</sx>" +
                    "</dt>" +
                    "</def>" +
                    "<uro>" +
                    "<ure>pres*ti*dig*i*ta*tor</ure>" +
                    "<sound>" +
                    "<wav>presti02.wav</wav>" +
                    "<wpr>+pres-tu-!di-ju-+tA-tur</wpr>" +
                    "</sound>" +
                    "<pr>-ˈdi-jə-ˌtā-tər</pr>" +
                    "<fl>noun</fl>" +
                    "</uro>" +
                    "</entry>" +
                    "</entry_list>";

    private static final String ns = null;

    public static SpellWord simpleParseTest(String XMLstring) throws XmlPullParserException, IOException {
        String parsedString = "";
        SpellWord wordResult = new SpellWord("junk", "junk", "", "", "junk");
        String word = "", definition = "", audio = "";
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(XMLstring));

        int eventType = parser.getEventType();
        boolean haveWord = false, haveAudio = false, haveDefinition = false;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String name = parser.getName();
                parsedString = parsedString + " ST: " + name;
                //if(parser.getEventType()!=XmlPullParser.TEXT){continue;}
                switch (name) {
                    case "ew":
                        parser.next();
                        if (!haveWord) {
                            word = parser.getText();
                            parsedString = parsedString + "Word is : " + word + "\n";
                            haveWord = true;
                        }
                        break;
                    case "wav":
                        parser.next();
                        if (!haveAudio) {
                            audio = parser.getText();
                            parsedString = parsedString + "Audio file is : " + audio + "\n";
                            haveAudio = true;
                        }
                        break;
                    case "dt":
                        parser.next();
                        if (!haveDefinition) {
                            definition = getDefinition(parser);
                            parsedString = parsedString + "dt contains : " + definition + "\n";
                            haveDefinition = true;
                        }
                        break;
                }
            }
            if (haveWord && haveAudio && haveDefinition) {
                break;
            }
            eventType = parser.next();

        }
        wordResult = new SpellWord(word, definition, parsedString, "", audio);
        return wordResult;
    }

    private static String getDefinition(XmlPullParser parser) throws XmlPullParserException, IOException {
        boolean isEndOfDefinition = false, endOfsx = false;
        String definition = "";
        int eventType = parser.getEventType();
        while (!isEndOfDefinition) {

            if (eventType == XmlPullParser.TEXT) {
                //definition=definition+ " in TEXT, Text: "+parser.getText().length()+"*";
                String newText = parser.getText();
                if (newText.length() > 1) {
                    definition = definition + parser.getText();
                    if (endOfsx) {
                        endOfsx = false;
                    }
                }

            } else if (eventType == XmlPullParser.START_TAG) {
                //definition=definition+ " in Start TAG, Name: "+parser.getName()+" ";
                if (parser.getName().matches("sx")) {
                    if (endOfsx) {
                        definition = definition + ", ";
                        //definition=definition+" endOfsx in SX";
                        endOfsx = false;
                    }
                }
                if (parser.getName().matches("vi")) {
                    skipTAG(parser);
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                //definition=definition+ " in End TAG, Name: "+parser.getName()+" ";
                if (parser.getName().matches("dt")) {
                    isEndOfDefinition = true;
                } else if (parser.getName().matches("sx")) {
                    //definition=definition+" endOfsx true ";
                    endOfsx = true;
                }
            }
            eventType = parser.next();
        }
        if (definition.charAt(0) == ':') {
            definition = definition.substring(1);
        }
        return definition;
    }

    private static void skipTAG(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        Reader inp = new StringReader(XMLexample);
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            //parser.setInput(in, null);
            parser.setInput(inp);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
            inp.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, "entry_list");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
                skipTag(parser);
            }
        }
        return entries;
    }

    public static class Entry {
        public final String word;
        public final String audio;
        public final String definition;

        private Entry(String word, String definition, String audio) {
            this.word = word;
            this.definition = definition;
            this.audio = audio;
        }
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");
        String word = null;
        String definition = null;
        String audio = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("ew")) {
                word = readWord(parser);
//            } else if (name.equals("summary")) {
//                definition = readDefinition(parser);
//            } else if (name.equals("link")) {
//                audio = readAudio(parser);
            } else {
                skipTag(parser);
            }
        }
        return new Entry(word, definition, audio);
    }

    // Processes title tags in the feed.
    private String readWord(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "ew");
        String word = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "ew");
        return word;
    }

    // Processes link tags in the feed.
    //TODO: Correctly implement this
    private String readAudio(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("link")) {
            if (relType.equals("alternate")) {
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    // Processes summary tags in the feed.
    //TODO: Correctly implement this
    private String readDefinition(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "summary");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return summary;
    }

    // For the tags title and summary, extracts their text values.
    //TODO: Correctly implement this
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}

