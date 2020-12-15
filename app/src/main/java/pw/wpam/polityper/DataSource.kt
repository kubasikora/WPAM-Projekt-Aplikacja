package pw.wpam.polityper

import pw.wpam.polityper.models.LeagueHeader


class DataSource{

    companion object{

        fun createDataSet(): ArrayList<LeagueHeader>{
            val list = ArrayList<LeagueHeader>()
            list.add(
                    LeagueHeader(
                            "Puchar Myszki Miki",
                            "Premier League",
                            "https://www.nicepng.com/png/full/340-3408251_premier-league-premier-league-logo-pes-2017.png",
                            "Sally"
                    )
            )
            list.add(
                    LeagueHeader(
                            "Liga pracownicza",
                            "Premier League",
                            "https://www.nicepng.com/png/full/340-3408251_premier-league-premier-league-logo-pes-2017.png",
                            "mitch"
                    )
            )

            list.add(
                    LeagueHeader(
                            "Polityper",
                            "Ekstraklasa",
                            "https://fs.siteor.com/gsmonline/articles/photo1s/65633/large/ekstraklasa_new.jpg?1371808291",
                            "John"
                    )
            )
            list.add(
                    LeagueHeader(
                            "Liga Piłkawki",
                            "Serie A",
                            "https://www.kindpng.com/picc/m/25-254792_serie-a-logo-png-transparent-png.png",
                            "Steven"
                    )
            )
            list.add(
                    LeagueHeader(
                            "La Liga Loca",
                            "La Liga",
                            "https://www.kindpng.com/picc/m/0-3231_laliga-vertical-logo-vector-transparent-la-liga-logo.png",
                            "Richelle"
                    )
            )
            list.add(
                    LeagueHeader(
                            "Siatkawka",
                            "Plus liga",
                            "https://img.plps.siatkarskaliga.pl/plusliga.png",
                            "Jessica"
                    )
            )
            list.add(
                    LeagueHeader(
                            "Tenisowe świry",
                            "US Open",
                            "https://newyorktennismagazine.com/sites/default/files/us%20open%20logo_0.png",
                            "Guy"
                    )
            )
            list.add(
                    LeagueHeader(
                            "Curva Sud",
                            "Serie A",
                            "https://www.kindpng.com/picc/m/25-254792_serie-a-logo-png-transparent-png.png",
                            "Ruby"
                    )
            )
            list.add(
                    LeagueHeader(
                            "Liga Kopania się po czole",
                            "Ekstraklasa",
                            "https://fs.siteor.com/gsmonline/articles/photo1s/65633/large/ekstraklasa_new.jpg?1371808291",
                            "mitch"
                    )
            )
            return list
        }
    }
}