package com.example.wawapp.events

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.wawapp.R

val EventType.darkerColor: Color
    get() {
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

fun EventType.getName(context: Context): String = context.getString(this.nameId)

fun stringToEventType(name: String): EventType = EventType.values().first { it.suffix == name }

enum class EventType(val suffix: String, val color: Color, val nameId: Int) {
    TODAY("Today", EventTypeColor.TODAY.color, R.string.today),
    LATEST("Latest", EventTypeColor.LATEST.color, R.string.latest),
    FOR_KIDS("ForKids", EventTypeColor.FOR_KIDS.color, R.string.for_kids),
    MOVIES("Movies", EventTypeColor.MOVIES.color, R.string.movies),
    PARTIES("Parties", EventTypeColor.PARTIES.color, R.string.parties),
    CONCERTS("Concerts", EventTypeColor.CONCERTS.color, R.string.concerts),
    PRESENTATIONS("Presentations", EventTypeColor.PRESENTATIONS.color, R.string.presentations),
    WALKS("Walks", EventTypeColor.WALKS.color, R.string.walks),
    SPORT("Sport", EventTypeColor.SPORT.color, R.string.sport),
    MEETINGS("Meetings", EventTypeColor.MEETINGS.color, R.string.meetings),
    STANDUPS("Standups", EventTypeColor.STANDUPS.color, R.string.standups),
    FAIRS("Fairs", EventTypeColor.FAIRS.color, R.string.fairs),
    THEATRE_PLAYS("TheatrePlays", EventTypeColor.THEATRE_PLAYS.color, R.string.theatre_plays),
    WORKSHOPS("Workshops", EventTypeColor.WORKSHOPS.color, R.string.workshops),
    LECTURES("Lectures", EventTypeColor.LECTURES.color, R.string.lectures),
    EXPOSITIONS("Expositions", EventTypeColor.EXPOSITIONS.color, R.string.expositions),
    OTHER("Other", EventTypeColor.OTHER.color, R.string.other),
    ENGLISH("English", EventTypeColor.ENGLISH.color, R.string.english),
    ONLINE("Online", EventTypeColor.ONLINE.color, R.string.online),
    BEMOWO("Bemowo", EventTypeColor.DISTRICT.color, R.string.bemowo),
    BIALOLEKA("Bialoleka", EventTypeColor.DISTRICT.color, R.string.bialoleka),
    BIELANY("Bielany", EventTypeColor.DISTRICT.color, R.string.bielany),
    MOKOTOW("Mokotow", EventTypeColor.DISTRICT.color, R.string.mokotow),
    OCHOTA("Ochota", EventTypeColor.DISTRICT.color, R.string.ochota),
    PRAGA_POLUDNIE("PragaPoludnie", EventTypeColor.DISTRICT.color, R.string.praga_poludnie),
    PRAGA_POLNOC("PragaPolnoc", EventTypeColor.DISTRICT.color, R.string.praga_polnoc),
    SRODMIESCIE("Srodmiescie", EventTypeColor.DISTRICT.color, R.string.srodmiescie),
    REMBERTOW("Rembertow", EventTypeColor.DISTRICT.color, R.string.rembertow),
    TARGOWEK("Targowek", EventTypeColor.DISTRICT.color, R.string.targowek),
    URSUS("Ursus", EventTypeColor.DISTRICT.color, R.string.ursus),
    URSYNOW("Ursynow", EventTypeColor.DISTRICT.color, R.string.ursynow),
    WAWER("Wawer", EventTypeColor.DISTRICT.color, R.string.wawer),
    WESOLA("Wesola", EventTypeColor.DISTRICT.color, R.string.wesola),
    WILANOW("Wilanow", EventTypeColor.DISTRICT.color, R.string.wilanow),
    WLOCHY("Wlochy", EventTypeColor.DISTRICT.color, R.string.wlochy),
    WOLA("Wola", EventTypeColor.DISTRICT.color, R.string.wola),
    ZOLIBORZ("Zoliborz", EventTypeColor.DISTRICT.color, R.string.zoliborz),
    OUTSIDE_THE_CITY(
        "OutsideTheCity",
        EventTypeColor.OUTSIDE_THE_CITY.color,
        R.string.outside_the_city
    )
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
