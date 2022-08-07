package com.example.wawapp.events

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

fun EventType.getDarkerColor(): Color {
    val androidGraphicsColor = this.color.toArgb()
    val hsv = FloatArray(3)

    android.graphics.Color.RGBToHSV(
        androidGraphicsColor.red,
        androidGraphicsColor.green,
        androidGraphicsColor.blue,
        hsv
    )
    hsv[2] /= 2f

    val androidGraphicsDarkerColor = android.graphics.Color.HSVToColor(hsv)

    return Color(androidGraphicsDarkerColor)
}

fun stringToEventType(name: String): EventType = EventType.values().first { it.suffix == name }

enum class EventType(val suffix: String, val color: Color) {
    TODAY("Today", EventTypeColor.TODAY.color),
    LATEST("Latest", EventTypeColor.LATEST.color),
    FOR_KIDS("ForKids", EventTypeColor.FOR_KIDS.color),
    MOVIES("Movies", EventTypeColor.MOVIES.color),
    PARTIES("Parties", EventTypeColor.PARTIES.color),
    CONCERTS("Concerts", EventTypeColor.CONCERTS.color),
    PRESENTATIONS("Presentations", EventTypeColor.PRESENTATIONS.color),
    WALKS("Walks", EventTypeColor.WALKS.color),
    SPORT("Sport", EventTypeColor.SPORT.color),
    MEETINGS("Meetings", EventTypeColor.MEETINGS.color),
    STANDUPS("Standups", EventTypeColor.STANDUPS.color),
    FAIRS("Fairs", EventTypeColor.FAIRS.color),
    THEATRE_PLAYS("TheatrePlays", EventTypeColor.THEATRE_PLAYS.color),
    WORKSHOPS("Workshops", EventTypeColor.WORKSHOPS.color),
    LECTURES("Lectures", EventTypeColor.LECTURES.color),
    EXPOSITIONS("Expositions", EventTypeColor.EXPOSITIONS.color),
    OTHER("Other", EventTypeColor.OTHER.color),
    ENGLISH("English", EventTypeColor.ENGLISH.color),
    ONLINE("Online", EventTypeColor.ONLINE.color),
    BEMOWO("Bemowo", EventTypeColor.DISTRICT.color),
    BIALOLEKA("Bialoleka", EventTypeColor.DISTRICT.color),
    BIELANY("Bielany", EventTypeColor.DISTRICT.color),
    MOKOTOW("Mokotow", EventTypeColor.DISTRICT.color),
    OCHOTA("Ochota", EventTypeColor.DISTRICT.color),
    PRAGA_POLUDNIE("PragaPoludnie", EventTypeColor.DISTRICT.color),
    PRAGA_POLNOC("PragaPolnoc", EventTypeColor.DISTRICT.color),
    SRODMIESCIE("Srodmiescie", EventTypeColor.DISTRICT.color),
    REMBERTOW("Rembertow", EventTypeColor.DISTRICT.color),
    TARGOWEK("Targowek", EventTypeColor.DISTRICT.color),
    URSUS("Ursus", EventTypeColor.DISTRICT.color),
    URSYNOW("Ursynow", EventTypeColor.DISTRICT.color),
    WAWER("Wawer", EventTypeColor.DISTRICT.color),
    WESOLA("Wesola", EventTypeColor.DISTRICT.color),
    WILANOW("Wilanow", EventTypeColor.DISTRICT.color),
    WLOCHY("Wlochy", EventTypeColor.DISTRICT.color),
    WOLA("Wola", EventTypeColor.DISTRICT.color),
    ZOLIBORZ("Zoliborz", EventTypeColor.DISTRICT.color),
    OUTSIDE_THE_CITY("OutsideTheCity", EventTypeColor.OUTSIDE_THE_CITY.color)
}

private enum class EventTypeColor(val color: Color) {
    TODAY(Color(0xFF70FF3C)),
    LATEST(Color(0xFFFF7777)),
    FOR_KIDS(Color(0xFFF59467)),
    MOVIES(Color(0xFF7CB0FF)),
    PARTIES(Color(0xFFD085FF)),
    CONCERTS(Color(0xFFCAFF80)),
    PRESENTATIONS(Color(0xFFFF6666)),
    WALKS(Color(0xFFE3FF80)),
    SPORT(Color(0xFFFFF385)),
    MEETINGS(Color(0xFFC1E49E)),
    STANDUPS(Color(0xFFF48CFF)),
    FAIRS(Color(0xFF8DFF85)),
    THEATRE_PLAYS(Color(0xFFDB778F)),
    WORKSHOPS(Color(0xFFFF4E4E)),
    LECTURES(Color(0xFFFCE08B)),
    EXPOSITIONS(Color(0xFFBB5CFF)),
    OTHER(Color(0xBF9595B1)),
    ENGLISH(Color(0xFF487AFA)),
    ONLINE(Color(0xFF73EEEE)),
    DISTRICT(Color(0xFFB7C2CC)),
    OUTSIDE_THE_CITY(Color(0xFFFFA6A6))
}
