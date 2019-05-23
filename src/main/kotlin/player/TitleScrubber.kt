package player

import org.slf4j.LoggerFactory

class TitleScrubber {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    fun scrub(dirtyTitle: String): String {
        val scrubbedTitle = stripIHeartRadioJunk(stripMarkers(dirtyTitle))
        log.info("Scrubbed title {} into {}", dirtyTitle, scrubbedTitle)
        return scrubbedTitle
    }

    private fun stripIHeartRadioJunk(dirtyTitle: String): String {
        val regex = """(.*)text="(.*?)\"""".toRegex()
        val matchResult = regex.find(dirtyTitle)
        if (matchResult == null) {
            return dirtyTitle
        } else {
            val (artist, song) = matchResult.destructured
            return artist + song
        }
    }

    private fun stripMarkers(dirtyTitle: String): String {
        var titleWithoutMarkers = dirtyTitle
        listOf("@", "\\+", "\\*").forEach {
            titleWithoutMarkers = stripBeginningAndTrailingMarker(titleWithoutMarkers, it)
        }
        return titleWithoutMarkers
    }

    private fun stripBeginningAndTrailingMarker(dirtyTitle: String, marker: String): String {
        // These markers end up having a space after or before them, an example is: "+ Boston @"
        val regex = "(^$marker\\s|\\s$marker\$)".toRegex()
        return regex.replace(dirtyTitle, "")
    }
}
