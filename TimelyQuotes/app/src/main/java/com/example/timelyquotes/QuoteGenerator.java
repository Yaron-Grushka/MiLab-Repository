package com.example.timelyquotes;

public class QuoteGenerator {

    // Titles of the notifications to be displayed
    // structure: [artist name], "[song name]", [year]
    private static String[] titles;
    static {
        titles = new String[]{"Guns N' Roses, \"Breakdown\", 1991",
                "David Bowie, \"Space Oddity\", 1969",
                "Billy Joel, \"Piano Man\", 1973",
                "Slipknot, \"Spit It Out\", 1999",
                "Ayreon, \"The Sixth Extinction\", 2008",
                "A Perfect Circle, \"Hourglass\", 2018",
                "The Cranberries, \"Zombie\", 1993",
                "Rage Against the Machine, \"Year of the Boomerang\", 1995",
                "Alice Cooper, \"Love's a Loaded Gun\", 1991",
                "Bob Dylan, \"The Times They Are a-Changin'\", 1964",
                "Iron Maiden, \"Wasted Years\", 1986",
                "Disturbed, \"Down with the Sickness\", 2000",
                "Korn, \"Coming Undone\", 2005",
                "Volbeat, \"Still Counting\", 2008",
                "Metallica, \"Sad But True\", 1991",
                "Eagles, \"Hotel California\", 1976",
                "Trivium, \"The Heart from Your Hate\", 2017",
                "Dio, \"Holy Diver\", 1983",
                "P.O.D, \"Listening for the Silence\", 2018",
                "Pantera, \"Cowboys from Hell\", 1990",
                "Muse, \"Supermassive Black Hole\", 2006",
                "Savatage, \"Edge of Thorns\", 1993"};
    }

    // Text of notification to be displayed:
    private static String[] quotes;
    static {
        quotes = new String[]{"\"Funny how everythin' was roses when we held on to the guns...\"",
                "\"Planet Earth is blue, and there's nothing I can do...\"",
                "\"And they're sharing a drink they call loneliness, but it's better than drinking alone...\"",
                "\"Coz in the interest of all involved, I got the problem solved, and the verdict is GUILTY!\"",
                "\"The meaning of life, is to give life meaning...\"",
                "\"The hourglass smashed, a million little pieces, the countdown carries on - five, four, three, two...\"",
                "\"It's the same old team, since 1916, in your head, in your head, they're still fighting...\"",
                "\"The sisters are in, so check the frontline, seems I spent the 80's in a Haiti state of mind...\"",
                "\"One down, one to go, just another bullet in the chamber... sometimes, love's a loaded gun.\"",
                "\"And you better start swimmin' or you'll sink like a stone, for the times, they are a-changin'.\"",
                "\"So, understand, you're wasting time always searching for those wasted years. Face up, make your stand, and realize you're living in the golden years!\"",
                "\"The world is a scary place, now that you've woken up the demon, in me...\"",
                "\"Sing along, mockingbird, you don't affect me.\"",
                "\"Counting all the a-holes in the room, well I'm definitely not alone, I'm not alone...\"",
                "\"I'm your truth - telling lies, I'm your reasoned alibis, I'm inside - open your eyes... I'm you... sad but true!\"",
                "\"And in the master's chambers, they gathered for the feast... they stab it with their steely knives, but they just can't kill the beast...\"",
                "\" Maybe you were right, maybe I was wrong, but I've been silent for far too long.\"",
                "\"Between the velvet lies, there's a truth that's hard as steel: the vision never dies, life's a neverending wheel...\"",
                "\"I swear, I can hear it so clear, even though it's loud inside my head, so I'm silent, SILENT!\"",
                "\"You see us comin' and you, all together, run for cover!\"",
                "\"Glaciers melting in the dead of night, and all the superstars are sucked into the supermassive...\"",
                "\"I have seen you on the edge of dawn, felt you here before you were born. Balanced your dreams upon the edge of thorns... but I don't think about you anymore!\""};
    }

    static int index = 0;
    static String[] generate(){
        //int index = (int)(Math.random() * quotes.length);
        if (index == quotes.length){
            index = 0;
        }
        return new String[]{titles[index], quotes[index++]};
    }
}
