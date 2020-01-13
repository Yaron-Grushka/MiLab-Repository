package com.example.timelyquotes;

import java.util.ArrayList;

public class QuoteGenerator {

    // Titles of the notifications to be displayed
    // structure: [artist name], "[song name]", [year]
    private static ArrayList<String> titles = new ArrayList<>();
    static {
        titles.add("Guns N' Roses, \"Breakdown\", 1991");
        titles.add("David Bowie, \"Space Oddity\", 1969");
        titles.add("Billy Joel, \"Piano Man\", 1973");
        titles.add("Slipknot, \"Spit It Out\", 1999");
        titles.add("Ayreon, \"The Sixth Extinction\", 2008");
        titles.add("A Perfect Circle, \"Hourglass\", 2018");
        titles.add("The Cranberries, \"Zombie\", 1993");
        titles.add("Rage Against the Machine, \"Year of the Boomerang\", 1995");
        titles.add("Alice Cooper, \"Love's a Loaded Gun\", 1991");
        titles.add("Bob Dylan, \"The Times They Are a-Changin'\", 1964");
        titles.add("Iron Maiden, \"Wasted Years\", 1986");
        titles.add("Disturbed, \"Down with the Sickness\", 2000");
        titles.add("Korn, \"Coming Undone\", 2005");
        titles.add("Volbeat, \"Still Counting\", 2008");
        titles.add("Metallica, \"Sad But True\", 1991");
        titles.add("Eagles, \"Hotel California\", 1976");
        titles.add("Trivium, \"The Heart from Your Hate\", 2017");
        titles.add("Dio, \"Holy Diver\", 1983");
        titles.add("P.O.D, \"Listening for the Silence\", 2018");
        titles.add("Pantera, \"Cowboys from Hell\", 1990");
        titles.add("Muse, \"Supermassive Black Hole\", 2006");
        titles.add("Savatage, \"Edge of Thorns\", 1993");
    }

    // Text of notification to be displayed:
    private static ArrayList<String> quotes = new ArrayList<>();
    static {
        quotes.add("\"Funny how everythin' was roses when we held on to the guns...\"");
        quotes.add("\"Planet Earth is blue, and there's nothing I can do...\"");
        quotes.add("\"And they're sharing a drink they call loneliness, but it's better than drinking alone...\"");
        quotes.add("\"Coz in the interest of all involved, I got the problem solved, and the verdict is GUILTY!\"");
        quotes.add("\"The meaning of life, is to give life meaning...\"");
        quotes.add("\"The hourglass smashed, a million little pieces, the countdown carries on - five, four, three, two...\"");
        quotes.add("\"It's the same old team, since 1916, in your head, in your head, they're still fighting...\"");
        quotes.add("\"The sisters are in, so check the frontline, seems I spent the 80's in a Haiti state of mind...\"");
        quotes.add("\"One down, one to go, just another bullet in the chamber... sometimes, love's a loaded gun.\"");
        quotes.add("\"And you better start swimmin' or you'll sink like a stone, for the times, they are a-changin'.\"");
        quotes.add("\"So, understand, you're wasting time always searching for those wasted years. Face up, make your stand, and realize you're living in the golden years!\"");
        quotes.add("\"The world is a scary place, now that you've woken up the demon, in me...\"");
        quotes.add("\"Sing along, mockingbird, you don't affect me.\"");
        quotes.add("\"Counting all the a-holes in the room, well I'm definitely not alone, I'm not alone...\"");
        quotes.add("\"I'm your truth - telling lies, I'm your reasoned alibis, I'm inside - open your eyes... I'm you... sad but true!\"");
        quotes.add("\"And in the master's chambers, they gathered for the feast... they stab it with their steely knives, but they just can't kill the beast...\"");
        quotes.add("\" Maybe you were right, maybe I was wrong, but I've been silent for far too long.\"");
        quotes.add("\"Between the velvet lies, there's a truth that's hard as steel: the vision never dies, life's a neverending wheel...\"");
        quotes.add("\"I swear, I can hear it so clear, even though it's loud inside my head, so I'm silent, SILENT!\"");
        quotes.add("\"You see us comin' and you, all together, run for cover!\"");
        quotes.add("\"Glaciers melting in the dead of night, and all the superstars are sucked into the supermassive...\"");
        quotes.add("\"I have seen you on the edge of dawn, felt you here before you were born. Balanced your dreams upon the edge of thorns... but I don't think about you anymore!\"");
    }

    static String[] generate(){
        int index = (int)(Math.random() * quotes.size());
        return new String[]{titles.get(index), quotes.get(index)};
    }
}
